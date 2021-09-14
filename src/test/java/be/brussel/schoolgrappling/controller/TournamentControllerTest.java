package be.brussel.schoolgrappling.controller;

import be.brussel.schoolgrappling.model.TournamentEntity;
import be.brussel.schoolgrappling.service.SchoolService;
import be.brussel.schoolgrappling.service.TournamentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@DataJpaTest
class TournamentControllerTest {
    @Mock
    TournamentService tournamentService;

    @Mock
    SchoolService schoolService;

    TournamentController tournamentController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tournamentController = new TournamentController(tournamentService, schoolService);
        mockMvc = MockMvcBuilders.standaloneSetup(tournamentController).build();
    }

    @Test
    void getTournaments() throws Exception {
        List<TournamentEntity> tournamentEntities = new ArrayList<>();
        tournamentEntities.add(TournamentEntity.builder().tournamentName("Schoolgrappling 1").build());
        tournamentEntities.add(TournamentEntity.builder().tournamentName("Schoolgrappling 2").build());
        tournamentEntities.add(TournamentEntity.builder().tournamentName("Schoolgrappling 3").build());

        when(tournamentService.getAllTournaments()).thenReturn(tournamentEntities);

        mockMvc.perform(get("/tournaments"))
                .andExpect(status().isOk())
                .andExpect(view().name("tournaments/showList"))
                .andExpect(model().attributeExists("tournaments"));
    }

    @Test
    void getTournamentById() throws Exception{
        TournamentEntity tournament = TournamentEntity.builder().id(1L).build();
        when(tournamentService.findTournamentById(anyLong())).thenReturn(tournament);

        mockMvc.perform(get("/tournaments/1/show/"))
                .andExpect(status().isOk())
                .andExpect(view().name("tournaments/show"))
                .andExpect(model().attributeExists("tournament"));
    }

    @Test
    void postNewTournamentFormWithValidations() throws Exception {
        // given
        TournamentEntity tournament = TournamentEntity.builder().build();
        tournament.setId(3L);

        // when
        when(tournamentService.saveTournament(any())).thenReturn(tournament);

        // then
        mockMvc.perform(post("/tournaments/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", ""))

                .andExpect(status().isOk())
                .andExpect(view().name("tournaments/createOrUpdateForm"))
                .andExpect(model().attributeExists("tournament"));
    }

    @Test
    void postNewTournamentFormWithValidationsFail() throws Exception {
        // given
        TournamentEntity tournament = TournamentEntity.builder().build();
        tournament.setId(4L);

        // when
        when(tournamentService.saveTournament(any())).thenReturn(tournament);

        // then
        mockMvc.perform(post("/tournaments/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "").param("maxPool", "3"))

                .andExpect(status().isOk())
                .andExpect(view().name("tournaments/createOrUpdateForm"))
                .andExpect(model().attributeExists("tournament"));
    }

    @Test
    void getUpdateView() throws Exception {
        // given
        TournamentEntity tournament = TournamentEntity.builder().build();
        tournament.setId(4L);

        // when
        when(tournamentService.findTournamentById(anyLong())).thenReturn(tournament);

        // then
        mockMvc.perform(get("/tournaments/4/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("tournaments/createOrUpdateForm"))
                .andExpect((model().attributeExists("tournament")));
    }

    @Test
    void deleteTournament() throws Exception {
        mockMvc.perform(get("/tournaments/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/tournaments/"));

        verify(tournamentService, times(1)).deleteById(anyLong());
    }
}