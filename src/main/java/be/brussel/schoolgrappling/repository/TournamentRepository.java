package be.brussel.schoolgrappling.repository;

import be.brussel.schoolgrappling.model.TournamentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentRepository extends JpaRepository<TournamentEntity, Long> {
    TournamentEntity findTournamentEntityByTournamentName(String tournamentName);

    boolean existsTournamentEntityByTournamentName(String tournamentName);

    @Query("SELECT tournaments FROM tournaments tournaments " +
            "WHERE LOWER(tournaments.tournamentName) LIKE LOWER(CONCAT('%', ?1, '%')) " +
            "OR LOWER(tournaments.venue) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<TournamentEntity> filterTournaments(String keyword);

    /* --------------------------------------------------------------------------- */
    @Query("SELECT tournaments FROM tournaments tournaments WHERE LOWER(tournaments.tournamentName) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<TournamentEntity> filterTournamentsByName(String keyword);

    @Query("SELECT tournaments FROM tournaments tournaments WHERE LOWER(tournaments.venue) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<TournamentEntity> filterTournamentsByLocation(String keyword);
    // methoden om afzonderlijke filters te maken maar weet niet of dit echt nodig is.
}
