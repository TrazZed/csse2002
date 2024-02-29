package lms.exceptions;

/**
 * An exception that is thrown if the system is in a bad state
 */
public class BadStateException extends RuntimeException {

    /**
     * Construct BadStateException with no message
     */
    public BadStateException() {}

    /**
     * Construct BadStateException with specified message
     *
     * @param message a String containing the error message for exception.
     */
    public BadStateException(String message) {
        super(message);
    }

    /**
     * Construct BadStateException with specified message and cause
     *
     * @param message a String containing the error message for exception.
     * @param cause the cause of the exception
     */
    public BadStateException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Construct new BadStateException with specified cause
     *
     * @param cause the cause of the exception
     */
    public BadStateException(Throwable cause) {
        super(cause);
    }
}
