package be.brussel.schoolgrappling.exception.tournamentexception;

public class TournamentException extends Exception {
    public TournamentException() {
    }

    public TournamentException(String message) {
        super(message);
    }

    public TournamentException(String message, Throwable cause) {
        super(message, cause);
    }

    public TournamentException(Throwable cause) {
        super(cause);
    }

    public TournamentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
