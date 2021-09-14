package be.brussel.schoolgrappling.exception.schoolexception;

public class SchoolWithThisIdDoesNotExistInDBException extends SchoolException {
    public SchoolWithThisIdDoesNotExistInDBException() {
        super();
    }

    public SchoolWithThisIdDoesNotExistInDBException(String message) {
        super(message);
    }

    public SchoolWithThisIdDoesNotExistInDBException(String message, Throwable cause) {
        super(message, cause);
    }

    public SchoolWithThisIdDoesNotExistInDBException(Throwable cause) {
        super(cause);
    }

    protected SchoolWithThisIdDoesNotExistInDBException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
