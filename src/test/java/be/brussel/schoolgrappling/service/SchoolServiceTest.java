package be.brussel.schoolgrappling.service;

import be.brussel.schoolgrappling.exception.EmailIsNotValidException;
import be.brussel.schoolgrappling.exception.schoolexception.SchoolWithThisIdDoesNotExistInDBException;
import be.brussel.schoolgrappling.exception.schoolexception.SchoolWithThisNameAlreadyExistInDBException;
import be.brussel.schoolgrappling.model.SchoolEntity;
import be.brussel.schoolgrappling.repository.SchoolRepository;
import be.brussel.schoolgrappling.exception.schoolexception.*;
import be.brussel.schoolgrappling.service.validation.EmailValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SchoolServiceTest {

    @Mock
    private SchoolRepository schoolRepository;
    private SchoolService schoolService;

    @BeforeEach
    void setUp() {
        EmailValidator emailValidator = new EmailValidator();
        schoolService = new SchoolService(schoolRepository, emailValidator);
    }

    @Test
    void getSchools() {
        //when
        schoolService.getSchools();
        //then
        verify(schoolRepository).findAll();
    }

    @Test
    void filterSchools() {
        //given
        String keyword = "3000";
        //when
        schoolService.filterSchools(keyword);
        //then
        verify(schoolRepository).filterSchools(keyword);

    }

    @Test
    void filterSchoolsWithNullAsParameterWillThrow() {
        //given
        String keyword = null;
        //when
        schoolService.filterSchools(keyword);
        //then
        verify(schoolRepository).findAll();
    }

    @Test
    void saveSchoolAllOk() throws Exception{
        //given
        SchoolEntity school = new SchoolEntity("De Regenboog", "KoalaTown", 3000, 3, "The place to grapple", "onekoala@regenboog.be", "+0452");
        //when
        schoolService.saveSchool(school);
        //then
        ArgumentCaptor<SchoolEntity> schoolEntityArgumentCaptor = ArgumentCaptor.forClass(SchoolEntity.class);

        verify(schoolRepository).save(schoolEntityArgumentCaptor.capture());

        SchoolEntity capturedSchool = schoolEntityArgumentCaptor.getValue();

        assertThat(capturedSchool).withFailMessage(" should save school with the specific schoolName in the db, when schoolName is not already in db & email is valid").isEqualTo(school);
    }

    @Test
    void saveSchoolWithNullAsParameterWillThrow() {
        //given
        SchoolEntity school = null;
        //when
        //then
        assertThatThrownBy(() -> schoolService.saveSchool(school))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("There in no school in the request");

        verify(schoolRepository, never()).save(any());
    }

    @Test
    void saveSchoolWhitInvalidEmailWillThrow() throws Exception{
        //given
        String invalidEmail = "invalid email";
        SchoolEntity school = new SchoolEntity("De Regenboog", "KoalaTown", 3000, 3, "The place to grapple", invalidEmail, "+0452");
        //when
        //then
        assertThatThrownBy(() -> schoolService.saveSchool(school))
                .isInstanceOf(EmailIsNotValidException.class)
                .hasMessageContaining("Email should be in a valid format, please fill in a valid email format");

        verify(schoolRepository, never()).save(any());
    }

    @Test
    void saveSchoolWhenSchoolNameIsTakenWillThrow() {
        //given
        String schoolName = "De Regenboog";
        SchoolEntity school = new SchoolEntity(schoolName, "KoalaTown", 3000, 3, "The place to grapple", "onekoala@regenboog.be", "+0452");
        SchoolEntity schoolAlreadyInDB = new SchoolEntity(schoolName, "KoalaTown", 3000, 3, "The place to grapple", "onekoala@regenboog.be", "+0452");

        school.setId(1L);
        schoolAlreadyInDB.setId(3L);

        given(schoolRepository.existsSchoolEntityBySchoolName(anyString()))
                .willReturn(true);
        given(schoolRepository.getSchoolEntityBySchoolName(anyString()))
                .willReturn(schoolAlreadyInDB);
        //when
        //then
        assertThatThrownBy(() -> schoolService.saveSchool(school))
                .isInstanceOf(SchoolWithThisNameAlreadyExistInDBException.class)
                .hasMessageContaining("The school '"+schoolName+"' already exists in the database");
        verify(schoolRepository, never()).save(any());
    }

    @Test
    void deleteById() throws Exception {
        //given
        Long id = 1L;
        given(schoolRepository.existsById(id)).willReturn(true);
        //when
        schoolService.deleteById(id);
        //then
        verify(schoolRepository).deleteById(id);
    }

    @Test
    void deleteByIdWithNullAsParameterWillThrow() {
        //given
        Long id = null;
        //when
        //then
        assertThatThrownBy(() -> schoolService.deleteById(id))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("There in no id in the request");

        verify(schoolRepository, never()).deleteById(any());
    }

    @Test
    void deleteByIdWithIdNotInDBWillThrow() {
        //given
        Long id = 1L;
        given(schoolRepository.existsById(id)).willReturn(false);
        //when
        //then
        assertThatThrownBy(() -> schoolService.deleteById(id))
                .isInstanceOf(SchoolWithThisIdDoesNotExistInDBException.class)
                .hasMessageContaining("School with id " + id + " does not exist");

        verify(schoolRepository, never()).deleteById(any());
    }

    @Test
    void getSchoolById() throws Exception {
        //given
        Long id = 1L;
        given(schoolRepository.existsById(id))
                .willReturn(true);
        //when
        schoolService.getSchoolById(id);
        //then
        verify(schoolRepository).getById(id);
    }

    @Test
    void getSchoolByIdWithNullAsParameterWillThrow() {
        //given
        Long id = null;
        //when
        //then
        assertThatThrownBy(() -> schoolService.getSchoolById(id))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("There in no id in the request");

        verify(schoolRepository, never()).getById(any());
    }

    @Test
    void getSchoolByIdWithIdNotInDBWillThrow() {
        //given
        Long id = 1L;
        given(schoolRepository.existsById(id)).willReturn(false);
        //when
        //then
        assertThatThrownBy(() -> schoolService.deleteById(id))
                .isInstanceOf(SchoolWithThisIdDoesNotExistInDBException.class)
                .hasMessageContaining("School with id " + id + " does not exist");

        verify(schoolRepository, never()).getById(any());
    }
}