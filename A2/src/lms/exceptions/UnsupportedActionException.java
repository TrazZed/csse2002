package lms.exceptions;

/**
 * An Exception that is used to indicate that an unsupported action or operation was attempted.
 */
public class UnsupportedActionException extends RuntimeException {

    /**
     * Constructs UnsupportedActionException with no message
     */
    public UnsupportedActionException() {}

    /**
     * Constructs UnsupportedActionException with specified message
     *
     * @param message a String detailing the message for exception
     */
    public UnsupportedActionException(String message) {
        super(message);
    }

    /**
     * Constructs UnsupportedActionException with specified message and cause
     *
     * @param message a String detailing the message for exception
     * @param cause the cause of the exception
     */
    public UnsupportedActionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs UnsupportedActionException with specified cause
     *
     * @param cause the cause of the exception
     */
    public UnsupportedActionException(Throwable cause) {
        super(cause);
    }
}
