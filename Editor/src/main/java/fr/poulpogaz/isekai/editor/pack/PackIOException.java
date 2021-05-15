package fr.poulpogaz.isekai.editor.pack;

import java.io.IOException;

public class PackIOException extends IOException {

    public PackIOException() {
    }

    public PackIOException(String message) {
        super(message);
    }

    public PackIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public PackIOException(Throwable cause) {
        super(cause);
    }
}