package be.brussel.schoolgrappling.controller;

import be.brussel.schoolgrappling.exception.tournamentexception.TournamentWithThisNameAlreadyExistsInDBException;
import be.brussel.schoolgrappling.model.TournamentEntity;
import be.brussel.schoolgrappling.service.SchoolService;
import be.brussel.schoolgrappling.service.TournamentService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor

@RequestMapping("/tournaments")
@Controller
public class TournamentController {
    private static final String VIEWS_TOURNAMENT_CREATE_OR_UPDATE_FORM = "tournaments/createOrUpdateForm";

    private TournamentService tournamentService;
    private SchoolService schoolService;

    @GetMapping({"","/"})
    public String getTournaments(@Param ("keyword") String keyword, Model model) {
        model.addAttribute("tournaments", tournamentService.filterTournaments(keyword));
        model.addAttribute("keyword", keyword);
        return "tournaments/showList";
    }

    @GetMapping({"/{id}/show", "/{id}"})
    public String getTournamentById(@PathVariable("id") String id, Model model) {
        model.addAttribute("tournament", tournamentService.findTournamentById(Long.parseLong(id)));
        return "tournaments/show";
    }

    // --------------- CREATE NEW TOURNAMENT FORM ------------------ //
    @GetMapping("/new")
    public String initCreationTournamentForm(Model model) {
        model.addAttribute("tournament", TournamentEntity.builder().build());
        model.addAttribute("schools", schoolService.getSchools());
        return VIEWS_TOURNAMENT_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid @ModelAttribute("tournament") TournamentEntity tournament, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return VIEWS_TOURNAMENT_CREATE_OR_UPDATE_FORM;
        }

        TournamentEntity savedTournament;
        try {
            savedTournament = tournamentService.saveTournament(tournament);
        }
        catch (TournamentWithThisNameAlreadyExistsInDBException exception) {
            model.addAttribute("errorMessageNameAlreadyInDB", exception.getMessage());
            return VIEWS_TOURNAMENT_CREATE_OR_UPDATE_FORM;
        }
        return String.format("redirect:/tournaments/%d/show", savedTournament.getId());
    }
    // ------------------------------------------------------------- //

    @GetMapping({"/{id}/update"})
    public String updateTournament(@PathVariable String id, Model model) {
        model.addAttribute("tournament", tournamentService.findTournamentById(Long.parseLong(id)));
        model.addAttribute("schools", schoolService.getSchools());
        return VIEWS_TOURNAMENT_CREATE_OR_UPDATE_FORM;
    }

    @GetMapping({"/{id}/delete"})
    public String deleteTournament(@PathVariable String id) {
        tournamentService.deleteById(Long.parseLong(id));
        return "redirect:/tournaments/";
    }

    @GetMapping({"/{id}/schools"})
    public String listSelectedSchools(@PathVariable String id, @Param("keyword") String keyword, Model model) {
        model.addAttribute("tournament", tournamentService.findTournamentById(Long.parseLong(id)));
        model.addAttribute("schools", tournamentService.findTournamentById(Long.parseLong(id)).getSchools());
        model.addAttribute("keyword", keyword);
        return "tournaments/selectedSchools/showList";
    }
}