package org.egovframe.cloud.reactive.exception;

/**
 * org.egovframe.cloud.reactive.exception.NotFoundException
 * <p>
 * 요청한 리소스가 존재하지 않을 경우 발생하는 Exception 클래스
 *
 */
public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = -7783730001532197502L;

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    protected NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
