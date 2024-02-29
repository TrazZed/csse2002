package srg.exceptions;

/**
 * An exception that is thrown if there is not enough capacity to store a resource
 */
public class InsufficientCapcaityException extends Exception {

    /**
     * Throws the InsufficientCapcaityException with no message
     */
    public InsufficientCapcaityException() {}

    /**
     * Throws the InsufficientCapcaityException with a message
     *
     * @param s the message to be displayed
     */
    public InsufficientCapcaityException(String s) {
        super(s);
    }
}
