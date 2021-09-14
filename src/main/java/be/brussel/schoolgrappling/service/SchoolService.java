package be.brussel.schoolgrappling.service;

import be.brussel.schoolgrappling.exception.EmailIsNotValidException;
import be.brussel.schoolgrappling.exception.schoolexception.SchoolWithThisIdDoesNotExistInDBException;
import be.brussel.schoolgrappling.exception.schoolexception.SchoolWithThisNameAlreadyExistInDBException;
import be.brussel.schoolgrappling.model.SchoolEntity;
import be.brussel.schoolgrappling.repository.SchoolRepository;
import be.brussel.schoolgrappling.exception.schoolexception.*;
import be.brussel.schoolgrappling.service.validation.EmailValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class SchoolService {

    private SchoolRepository schoolRepository;
    private EmailValidator emailValidator;


    public SchoolService(SchoolRepository schoolRepository, EmailValidator emailValidator) {
        this.schoolRepository = schoolRepository;
        this.emailValidator = emailValidator;
    }

    public List<SchoolEntity> getSchools() {
       return schoolRepository.findAll();
    }


    public  List<SchoolEntity> filterSchools(String keyword){
        if(keyword != null){
            return schoolRepository.filterSchools(keyword);
        }
        return getSchools();
    }

    public SchoolEntity saveSchool(SchoolEntity school) throws SchoolWithThisNameAlreadyExistInDBException, EmailIsNotValidException {
        if(Objects.isNull(school)){
            throw new NullPointerException("There in no school in the request");
        }

        if(schoolRepository.existsSchoolEntityBySchoolName(school.getSchoolName()) &&
                        !schoolRepository.getSchoolEntityBySchoolName(school.getSchoolName()).equals(school)){
            throw new SchoolWithThisNameAlreadyExistInDBException("The school '"+school.getSchoolName()+"' already exists in the database");
        }
        if(!emailValidator.emailIsValid(school.getSchoolEmail())){
            throw new EmailIsNotValidException("Email should be in a valid format, please fill in a valid email format");
        }
        return schoolRepository.save(school);
    }

    public void deleteById(Long id) throws SchoolWithThisIdDoesNotExistInDBException {
        if(Objects.isNull(id)){
            throw new NullPointerException("There in no id in the request");
        }
        if(!schoolRepository.existsById(id)){
            throw new SchoolWithThisIdDoesNotExistInDBException("School with id " + id + " does not exist");
        }
        schoolRepository.deleteById(id);
    }

    public SchoolEntity getSchoolById(Long id) throws SchoolWithThisIdDoesNotExistInDBException {
        if(Objects.isNull(id)){
            throw new NullPointerException("There in no id in the request");
        }
        if(!schoolRepository.existsById(id)){
            throw new SchoolWithThisIdDoesNotExistInDBException("School with id " + id + " does not exist");
        }
        return schoolRepository.getById(id);
    }

}
