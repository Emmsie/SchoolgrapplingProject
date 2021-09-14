package be.brussel.schoolgrappling.controller;


import be.brussel.schoolgrappling.exception.EmailIsNotValidException;
import be.brussel.schoolgrappling.exception.contactexception.ContactWithThisIdDoesNotExistInDBException;
import be.brussel.schoolgrappling.exception.contactexception.ContactWithThisUserNameAlreadyExistsInDBException;
import be.brussel.schoolgrappling.model.ContactEntity;
import be.brussel.schoolgrappling.service.ContactService;
import be.brussel.schoolgrappling.service.SchoolService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/contacts")
public class ContactController {

    private ContactService contactService;
    private SchoolService schoolService;

    private static final String CONTACT_CREATE_OR_UPDATE_FORM= "contacts/createOrUpdateContactsForm";

    @GetMapping("/new")
    public String initCreateContactForm(Model model){
        model.addAttribute("contact", ContactEntity.builder().build());
        return CONTACT_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/new")
    public String processContactCreationForm(@Valid @ModelAttribute("contact") ContactEntity contact, BindingResult bindingResult, Model model) {

        try{
            if (!bindingResult.hasErrors()) {
                ContactEntity savedContactEntity;
                try {
                    savedContactEntity = contactService.saveContact(contact);
                } catch (ContactWithThisUserNameAlreadyExistsInDBException e) {
                    model.addAttribute("errorMessageContactAlreadyInDB", e.getMessage());
                    return CONTACT_CREATE_OR_UPDATE_FORM;
                } catch (EmailIsNotValidException e) {
                    model.addAttribute("errorMessageEmailNotValid", e.getMessage());
                    return CONTACT_CREATE_OR_UPDATE_FORM;
                }
                return "redirect:/schools/findSchool/" + savedContactEntity.getId();
            }
        } catch (NullPointerException npe){
            return npe.getMessage();
        }

        return CONTACT_CREATE_OR_UPDATE_FORM;
    }

    @GetMapping(path = "/findContact/{id}")
    public String getContactById(@PathVariable("id") String id, Model model) {

        try{
            model.addAttribute("school", contactService.getContactById(Long.parseLong(id)));
        } catch(NullPointerException | ContactWithThisIdDoesNotExistInDBException e){
            return e.getMessage();
        }
        return "contacts/findContactById";
    }

    @GetMapping("/listContacts")
    public String getContacts(Model model, @Param("keyword") String keyword){
        model.addAttribute("contacts", contactService.filterContactsByName(keyword));
        model.addAttribute("keyword", keyword);
        return "contacts/listOfContacts";
    }

    @GetMapping({"/update/{id}"})
    public String updateContact(@PathVariable String id, Model model)  {
        try{
            model.addAttribute("contact", contactService.getContactById(Long.parseLong(id)));
            model.addAttribute("school", schoolService.getSchools());

        } catch (NullPointerException | ContactWithThisIdDoesNotExistInDBException e){
            return e.getMessage();
        }

        return CONTACT_CREATE_OR_UPDATE_FORM;
    }

    @GetMapping({"/delete/{id}"})
    public String deleteContact(@PathVariable String id)  {
        try {
            contactService.deleteById(Long.parseLong(id));
        } catch (NullPointerException | ContactWithThisIdDoesNotExistInDBException e){
            return e.getMessage();
        }

        return "redirect:/contacts/listContacts";
    }
}
