package be.brussel.schoolgrappling.exception.schoolexception;

public class SchoolException extends Exception{

    public SchoolException() {
        super();
    }

    public SchoolException(String message) {
        super(message);
    }

    public SchoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public SchoolException(Throwable cause) {
        super(cause);
    }

    protected SchoolException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
