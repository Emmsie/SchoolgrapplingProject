package be.brussel.schoolgrappling.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TournamentEntityTest {

    TournamentEntity tournament;

    @BeforeEach
    public void setUp() {
        tournament = new TournamentEntity();
    }

    @Test
    void getId() {
        Long idValue = 1L;
        tournament.setId(idValue);

        assertEquals(idValue, tournament.getId());
    }

    @Test
    void getTournamentName() {
        String tournamentName = "Schoolgrappling 10 Ukkel";
        tournament.setTournamentName(tournamentName);

        assertEquals(tournamentName, tournament.getTournamentName());
    }

    @Test
    void getSchools() {
        Set<SchoolEntity> selectedSchools = new HashSet<>();
        selectedSchools.add(SchoolEntity.builder().schoolName("De Regenboog").build());
        tournament.setSchools(selectedSchools);

        assertEquals(selectedSchools, tournament.getSchools());
    }
}