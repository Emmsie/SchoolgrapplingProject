package be.brussel.schoolgrappling.exception.contactexception;

public class ContactWithThisUserNameAlreadyExistsInDBException extends ContactException{

    public ContactWithThisUserNameAlreadyExistsInDBException() {
    }

    public ContactWithThisUserNameAlreadyExistsInDBException(String message) {
        super(message);
    }

    public ContactWithThisUserNameAlreadyExistsInDBException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContactWithThisUserNameAlreadyExistsInDBException(Throwable cause) {
        super(cause);
    }

    public ContactWithThisUserNameAlreadyExistsInDBException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
