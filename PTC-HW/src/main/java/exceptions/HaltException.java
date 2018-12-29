package exceptions;

import java.io.IOException;

/**
 * Description:
 *
 * @author xxz
 * Created on 12/28/2018
 */
public class HaltException extends IOException {
    /**
     * Constructs an {@code IOException} with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method)
     */
    public HaltException(String message) {
        super(message);
    }


}
