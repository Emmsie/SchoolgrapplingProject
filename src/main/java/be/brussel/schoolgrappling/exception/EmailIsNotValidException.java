package be.brussel.schoolgrappling.exception;

public class EmailIsNotValidException extends Exception {
    public EmailIsNotValidException() {
    }

    public EmailIsNotValidException(String message) {
        super(message);
    }

    public EmailIsNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailIsNotValidException(Throwable cause) {
        super(cause);
    }

    public EmailIsNotValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
