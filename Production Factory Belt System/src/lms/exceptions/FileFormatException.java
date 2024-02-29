package lms.exceptions;

import java.io.File;

/**
 * An exception that is thrown if a file being read or processed is not
 * in the expected format.
 */
public class FileFormatException extends Exception {

    /**
     * Constructs FileFormatException with no message
     */
    public FileFormatException() {}

    /**
     * Constructs FileFormatException with specified message
     * @param message a String detailing the message for exception
     */
    public FileFormatException(String message) {
        super(message);
    }

    /**
     * Constructs FileFormatException with specified message and line number
     *
     * @param message a String detailing the message for exception
     * @param lineNum the line number where it occurred
     */
    public FileFormatException(String message, int lineNum) {
        super(message + " (line: " + lineNum + ")");
    }

    /**
     * Constructs FileFormatException with specified message, line number and cause
     *
     * @param message a String detailing the message for exception
     * @param lineNum the line number where it occurred
     * @param cause a Throwable containing the cause
     */
    public FileFormatException(String message, int lineNum, Throwable cause) {
        super(message + " (line: " + lineNum + ")", cause);
    }

    /**
     * Constructs FileFormatException with specified message and cause
     *
     * @param message a String detailing the message for exception
     * @param cause a Throwable containing the cause
     */
    public FileFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs FileFormatException with specified Throwable cause
     *
     * @param cause a Throwable containing the cause
     */
    public FileFormatException(Throwable cause) {
        super(cause);
    }
}
