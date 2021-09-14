package be.brussel.schoolgrappling.service;

import be.brussel.schoolgrappling.exception.tournamentexception.TournamentWithThisNameAlreadyExistsInDBException;
import be.brussel.schoolgrappling.model.TournamentEntity;
import be.brussel.schoolgrappling.repository.TournamentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;

    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public List<TournamentEntity> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    public TournamentEntity saveTournament(TournamentEntity tournament) throws TournamentWithThisNameAlreadyExistsInDBException {
        if (Objects.isNull(tournament)) {
            throw new NullPointerException();
        }
        if (tournamentRepository.existsTournamentEntityByTournamentName(tournament.getTournamentName()) &&
                !tournamentRepository.findTournamentEntityByTournamentName(tournament.getTournamentName()).equals(tournament)) {
            throw new TournamentWithThisNameAlreadyExistsInDBException("Tournament: '" + tournament.getTournamentName() + "' already exists!");
        }
        return tournamentRepository.save(tournament);
    }

    public TournamentEntity findTournamentById(Long id) {
        if (Objects.isNull(id)) {
            throw new NullPointerException("There is no id in the request");
        }
        return tournamentRepository.getById(id);
    }

    public void deleteById(Long id) {
        if (Objects.isNull(id)) {
            throw new NullPointerException("there is no id in the request");
        }
        tournamentRepository.deleteById(id);
    }

    public List<TournamentEntity> filterTournamentsByName(String tournamentName) {
        if (tournamentName != null) {
            return tournamentRepository.filterTournamentsByName(tournamentName);
        }
        return getAllTournaments();
    }

    public List<TournamentEntity> filterTournamentsByLocation(String location) {
        if (location != null) {
            return tournamentRepository.filterTournamentsByLocation(location);
        }
        return getAllTournaments();
    }

    public List<TournamentEntity> filterTournaments(String keyword) {
        if (keyword != null) {
            return tournamentRepository.filterTournaments(keyword);
        }
        return getAllTournaments();
    }
}
