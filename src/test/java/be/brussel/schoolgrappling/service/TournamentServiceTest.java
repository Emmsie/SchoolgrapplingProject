package be.brussel.schoolgrappling.service;

import be.brussel.schoolgrappling.exception.tournamentexception.TournamentWithThisNameAlreadyExistsInDBException;
import be.brussel.schoolgrappling.model.TournamentEntity;
import be.brussel.schoolgrappling.repository.TournamentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
class TournamentServiceTest {

    @Mock
    TournamentRepository tournamentRepository;

    TournamentService tournamentService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        tournamentService = new TournamentService(tournamentRepository);
    }

    @Test
    void getAllTournaments() {
        // given
        TournamentEntity tournament = new TournamentEntity();
        List<TournamentEntity> tournamentsData = new ArrayList<>();
        List<TournamentEntity> tournaments = tournamentService.getAllTournaments();
        tournamentsData.add(tournament);

        // when
        when(tournamentRepository.findAll()).thenReturn(tournamentsData);

        // then
        assertEquals(tournaments.size(), 0);
        verify(tournamentRepository, times(1)).findAll();
    }

    @Test
    void findTournamentById() {
        // given
        TournamentEntity tournament = TournamentEntity.builder().id(1L).build();

        // when
        when(tournamentRepository.getById(anyLong())).thenReturn(tournament);
        TournamentEntity returnedTournament = tournamentService.findTournamentById(1L);

        // then
        assertNotNull(returnedTournament, "Null entity returned");
        verify(tournamentRepository, times(1)).getById(anyLong());
        verify(tournamentRepository, never()).findAll();
    }

    @Test
    void saveTournament() throws TournamentWithThisNameAlreadyExistsInDBException {
        //given
        TournamentEntity tournament = new TournamentEntity();
        tournament.setId(1L);

        // when
        when(tournamentRepository.save(any())).thenReturn(tournament);

        TournamentEntity savedTournament = tournamentService.saveTournament(tournament);

        // then
        assertEquals(Long.valueOf(1L), savedTournament.getId());
        verify(tournamentRepository, times(1)).save(any(TournamentEntity.class));
    }

    @Test
    void deleteById() {
        // given
        Long id = 2L;
        tournamentService.deleteById(id);

        // then, (no when for void return type)
        verify(tournamentRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void filterTournamentsByName() {
        // given
        List<TournamentEntity> tournamentEntities = new ArrayList<>();
        TournamentEntity tournament1 = TournamentEntity.builder().tournamentName("tournament 1").build();
        TournamentEntity tournament2 = TournamentEntity.builder().tournamentName("schoolgrappling 1").build();

        tournamentEntities.add(tournament1);
        tournamentEntities.add(tournament2);
        tournamentRepository.saveAll(tournamentEntities);

        // when
        when(tournamentRepository.filterTournamentsByName("schoolgrappling")).thenReturn(Collections.singletonList(tournament1));
        List<TournamentEntity> filteredTournaments = tournamentService.filterTournamentsByName("schoolgrappling");

        // then
        assertEquals(1, filteredTournaments.size());
        verify(tournamentRepository, times(1)).filterTournamentsByName("schoolgrappling");
    }

    @Test
    void filterTournamentsByLocation() {
        // given
        List<TournamentEntity> tournamentEntities = new ArrayList<>();
        TournamentEntity tournament1 = TournamentEntity.builder().venue("Bellekouter Affligem").build();
        TournamentEntity tournament2 = TournamentEntity.builder().venue("T 'Walleke Denderleeuw").build();

        tournamentEntities.add(tournament1);
        tournamentEntities.add(tournament2);
        tournamentRepository.saveAll(tournamentEntities);

        // when
        when(tournamentRepository.filterTournamentsByLocation("Bellekouter")).thenReturn(Collections.singletonList(tournament1));
        List<TournamentEntity> filteredTournaments = tournamentService.filterTournamentsByLocation("Bellekouter");

        // then
        assertEquals(1, filteredTournaments.size());
        verify(tournamentRepository, times(1)).filterTournamentsByLocation("Bellekouter");
    }
}