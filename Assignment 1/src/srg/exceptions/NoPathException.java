package srg.exceptions;

/**
 * An exception that is thrown when there is no possible flight or jump path to the specified SpacePort
 */
public class NoPathException extends Exception {

    /**
     * Throws the NoPathException with no message
     */
    public NoPathException() {}

    /**
     * Throws the NoPathException with a message
     *
     * @param s the message to be displayed
     */
    public NoPathException(String s) {
        super(s);
    }
}
