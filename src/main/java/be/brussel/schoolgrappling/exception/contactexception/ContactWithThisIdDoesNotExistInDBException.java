package be.brussel.schoolgrappling.exception.contactexception;

public class ContactWithThisIdDoesNotExistInDBException extends ContactException{

    public ContactWithThisIdDoesNotExistInDBException() {
    }

    public ContactWithThisIdDoesNotExistInDBException(String message) {
        super(message);
    }

    public ContactWithThisIdDoesNotExistInDBException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContactWithThisIdDoesNotExistInDBException(Throwable cause) {
        super(cause);
    }

    public ContactWithThisIdDoesNotExistInDBException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
