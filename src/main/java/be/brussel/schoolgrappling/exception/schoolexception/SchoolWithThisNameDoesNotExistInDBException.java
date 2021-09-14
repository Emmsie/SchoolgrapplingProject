package be.brussel.schoolgrappling.exception.schoolexception;

public class SchoolWithThisNameDoesNotExistInDBException extends SchoolException {
    public SchoolWithThisNameDoesNotExistInDBException() {
        super();
    }

    public SchoolWithThisNameDoesNotExistInDBException(String message) {
        super(message);
    }

    public SchoolWithThisNameDoesNotExistInDBException(String message, Throwable cause) {
        super(message, cause);
    }

    public SchoolWithThisNameDoesNotExistInDBException(Throwable cause) {
        super(cause);
    }

    protected SchoolWithThisNameDoesNotExistInDBException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
