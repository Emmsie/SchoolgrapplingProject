package be.brussel.schoolgrappling.exception.schoolexception;

public class SchoolWithThisNameAlreadyExistInDBException extends SchoolException {
    public SchoolWithThisNameAlreadyExistInDBException() {
        super();
    }

    public SchoolWithThisNameAlreadyExistInDBException(String message) {
        super(message);
    }

    public SchoolWithThisNameAlreadyExistInDBException(String message, Throwable cause) {
        super(message, cause);
    }

    public SchoolWithThisNameAlreadyExistInDBException(Throwable cause) {
        super(cause);
    }

    protected SchoolWithThisNameAlreadyExistInDBException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
