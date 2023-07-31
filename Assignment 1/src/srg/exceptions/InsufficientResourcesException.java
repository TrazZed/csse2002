package srg.exceptions;

/**
 * An exception that is thrown if a selected Resource is unavailable, or a Room was broken when trying to perform an action
 */
public class InsufficientResourcesException extends Exception {

    /**
     * Throws the InsufficientResourcesException with no message
     */
    public InsufficientResourcesException() {}

    /**
     * Throws the InsufficientResourcesException with a message
     *
     * @param message the message to be displayed
     */
    public InsufficientResourcesException(String message) {
        super(message);
    }
}
