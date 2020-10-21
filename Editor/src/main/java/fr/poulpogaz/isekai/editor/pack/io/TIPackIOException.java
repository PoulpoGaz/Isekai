package fr.poulpogaz.isekai.editor.pack.io;

import java.io.IOException;

public class TIPackIOException extends IOException {

    public TIPackIOException() {
    }

    public TIPackIOException(String message) {
        super(message);
    }

    public TIPackIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public TIPackIOException(Throwable cause) {
        super(cause);
    }
}