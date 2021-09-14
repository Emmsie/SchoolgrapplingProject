package be.brussel.schoolgrappling.exception.tournamentexception;

public class TournamentWithThisNameAlreadyExistsInDBException extends TournamentException {
    public TournamentWithThisNameAlreadyExistsInDBException() {
    }

    public TournamentWithThisNameAlreadyExistsInDBException(String message) {
        super(message);
    }

    public TournamentWithThisNameAlreadyExistsInDBException(String message, Throwable cause) {
        super(message, cause);
    }

    public TournamentWithThisNameAlreadyExistsInDBException(Throwable cause) {
        super(cause);
    }
}
