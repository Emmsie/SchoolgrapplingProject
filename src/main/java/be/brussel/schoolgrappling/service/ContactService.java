package be.brussel.schoolgrappling.service;

import be.brussel.schoolgrappling.exception.EmailIsNotValidException;
import be.brussel.schoolgrappling.exception.contactexception.ContactWithThisIdDoesNotExistInDBException;
import be.brussel.schoolgrappling.exception.contactexception.ContactWithThisUserNameAlreadyExistsInDBException;
import be.brussel.schoolgrappling.model.ContactEntity;
import be.brussel.schoolgrappling.repository.ContactRepository;

import be.brussel.schoolgrappling.service.validation.EmailValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ContactService {

    private ContactRepository contactRepository;
    private EmailValidator emailValidator;

    public ContactService(ContactRepository contactRepository, EmailValidator emailValidator) {
        this.contactRepository = contactRepository;
        this.emailValidator = emailValidator;
    }

    public List<ContactEntity> getContacts() {
        return contactRepository.findAll();
    }

    public  List<ContactEntity> filterContactsBySchool(String keyword){
        if(keyword != null){
            return contactRepository.findAllBySchool_SchoolName(keyword);
        }
        return getContacts();
    }

    public  List<ContactEntity> filterContactsByName(String keyword){
        if(keyword != null){
            return contactRepository.filterContactsByName(keyword);
        }
        return getContacts();
    }

    public ContactEntity saveContact(ContactEntity contact) throws ContactWithThisUserNameAlreadyExistsInDBException, EmailIsNotValidException {

        if (Objects.isNull(contact)){
            throw new NullPointerException("No object found in this request");
        }
        if(contactRepository.existsContactEntityByUserName(contact.getUserName()) &&
                !contactRepository.getContactEntityByUserName(contact.getUserName()).equals(contact)) {
            throw new ContactWithThisUserNameAlreadyExistsInDBException("This username is already in use");
        }
        if(!emailValidator.emailIsValid(contact.getEmail())){
            throw new EmailIsNotValidException("This email address is not valid");
        }
        return contactRepository.save(contact);
    }

    public void deleteById(Long id) throws ContactWithThisIdDoesNotExistInDBException {

        if(Objects.isNull(id)){
            throw new NullPointerException("Id not found");
        }
        if(!contactRepository.existsById(id)){
            throw new ContactWithThisIdDoesNotExistInDBException("No contact found with id " + id);
        }
        contactRepository.deleteById(id);
    }

    public ContactEntity getContactById(Long id) throws ContactWithThisIdDoesNotExistInDBException {
        if(Objects.isNull(id)){
            throw new NullPointerException("Id not found");
        }
        if(!contactRepository.existsById(id)){
            throw new ContactWithThisIdDoesNotExistInDBException("No contact found with id " + id);
        }
        return contactRepository.getById(id);
    }
}
