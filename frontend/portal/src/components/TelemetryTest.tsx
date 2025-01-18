import React from 'react';
import { logTelemetryEvent } from '../telemetry';
import { Button } from '@material-ui/core';

const TelemetryTest: React.FC = () => {
    const handleTestClick = () => {
        logTelemetryEvent('test.button.clicked', {
            timestamp: new Date().toISOString(),
            component: 'TelemetryTest'
        });
    };

    return (
        <Button 
            variant="contained" 
            color="primary" 
            onClick={handleTestClick}
            style={{ margin: '20px' }}
        >
            Test Telemetry
        </Button>
    );
};

export default TelemetryTest;
