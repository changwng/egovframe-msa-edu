import { WebTracerProvider } from '@opentelemetry/sdk-trace-web';
import { BatchSpanProcessor } from '@opentelemetry/sdk-trace-base';
import { OTLPTraceExporter } from '@opentelemetry/exporter-trace-otlp-http';
import { ZoneContextManager } from '@opentelemetry/context-zone';
import { DocumentLoadInstrumentation } from '@opentelemetry/instrumentation-document-load';
import { UserInteractionInstrumentation } from '@opentelemetry/instrumentation-user-interaction';
import { registerInstrumentations } from '@opentelemetry/instrumentation';
import { Resource } from '@opentelemetry/resources';
import { SemanticResourceAttributes } from '@opentelemetry/semantic-conventions';
import { ParentBasedSampler, TraceIdRatioBasedSampler } from '@opentelemetry/sdk-trace-base';

const provider = new WebTracerProvider({
    resource: new Resource({
        [SemanticResourceAttributes.SERVICE_NAME]: 'portal-frontend',
        [SemanticResourceAttributes.DEPLOYMENT_ENVIRONMENT]: 'development'
    }),
    sampler: new ParentBasedSampler({
        root: new TraceIdRatioBasedSampler(0.5) // 50% sampling rate
    })
});

// Configure OTLP exporter
const collectorUrl = 'http://localhost:4318/v1/traces';
const exporter = new OTLPTraceExporter({
    url: collectorUrl,
});

provider.addSpanProcessor(new BatchSpanProcessor(exporter));

// Register instrumentations
registerInstrumentations({
    instrumentations: [
        new DocumentLoadInstrumentation(),
        new UserInteractionInstrumentation({
            eventNames: ['click', 'submit']
        }),
    ],
    tracerProvider: provider,
});

// Initialize the provider
provider.register({
    contextManager: new ZoneContextManager(),
});

export const tracer = provider.getTracer('portal-frontend');

// Test logging function
export const logTelemetryEvent = (eventName: string, attributes: Record<string, any> = {}) => {
    const span = tracer.startSpan(eventName);
    Object.entries(attributes).forEach(([key, value]) => {
        span.setAttribute(key, value);
    });
    span.end();
};

// Add test logging on window load
window.addEventListener('load', () => {
    logTelemetryEvent('app.loaded', {
        timestamp: new Date().toISOString(),
        userAgent: navigator.userAgent
    });
});
