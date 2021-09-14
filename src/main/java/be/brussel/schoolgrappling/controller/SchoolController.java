package be.brussel.schoolgrappling.controller;

import be.brussel.schoolgrappling.exception.EmailIsNotValidException;
import be.brussel.schoolgrappling.exception.schoolexception.SchoolWithThisIdDoesNotExistInDBException;
import be.brussel.schoolgrappling.exception.schoolexception.SchoolWithThisNameAlreadyExistInDBException;
import be.brussel.schoolgrappling.model.SchoolEntity;
import be.brussel.schoolgrappling.service.ContactService;
import be.brussel.schoolgrappling.service.SchoolService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@Controller
@RequestMapping("/schools")
public class SchoolController {

    private SchoolService schoolService;
    private ContactService contactService;

    private static final String SCHOOL_CREATE_OR_UPDATE_FORM = "schools/createOrUpdateSchoolsForm";

    // create school form
    @GetMapping("/new")
    public String initCreatSchoolForm(Model model){
        model.addAttribute("school", SchoolEntity.builder().build());
        model.addAttribute("contacts", contactService.getContacts());
        return SCHOOL_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid @ModelAttribute("school")SchoolEntity school, BindingResult bindingResult, Model model) {
        try{
            if (!bindingResult.hasErrors()) {
                SchoolEntity savedSchoolEntity;
                try {
                    savedSchoolEntity = schoolService.saveSchool(school);
                } catch (SchoolWithThisNameAlreadyExistInDBException e) {
                    model.addAttribute("errorMessageSchoolAlreadyInDB", e.getMessage());
                    return SCHOOL_CREATE_OR_UPDATE_FORM;
                } catch (EmailIsNotValidException e) {
                    model.addAttribute("errorMessageEmailNotValid", e.getMessage());
                    return SCHOOL_CREATE_OR_UPDATE_FORM;
                }
                return "redirect:/schools/findSchool/" + savedSchoolEntity.getId();
            }
        } catch (NullPointerException npe){
            return npe.getMessage();
        }

        return SCHOOL_CREATE_OR_UPDATE_FORM;
    }


    // show school info
    @GetMapping(path = "/findSchool/{id}")
    public String getSchoolById(@PathVariable("id") String id, Model model) {
        try{
            model.addAttribute("school", schoolService.getSchoolById(Long.parseLong(id)));
        } catch(NullPointerException | SchoolWithThisIdDoesNotExistInDBException e){
            return e.getMessage();
        }

        return "schools/findSchoolById";
    }

    // show list of school
    @GetMapping("/listSchools")
    public String getSchools(Model model, @Param("keyword") String keyword){
        model.addAttribute("schools", schoolService.filterSchools(keyword));
        model.addAttribute("keyword", keyword);
        return "schools/listOfSchools";
    }


    // update school info
    @GetMapping({"/update/{id}"})
    public String updateSchool(@PathVariable String id, Model model)  {
        try{
            model.addAttribute("school", schoolService.getSchoolById(Long.parseLong(id)));
            model.addAttribute("contacts", contactService.getContacts());

        } catch (NullPointerException | SchoolWithThisIdDoesNotExistInDBException e){
            return e.getMessage();
        }

        return SCHOOL_CREATE_OR_UPDATE_FORM;
    }

    // delete school
    @GetMapping({"/delete/{id}"})
    public String deleteSchool(@PathVariable String id)  {
        try {
            schoolService.deleteById(Long.parseLong(id));
        } catch (NullPointerException | SchoolWithThisIdDoesNotExistInDBException e){
            return e.getMessage();
        }

        return "redirect:/schools/listSchools";
    }

}
