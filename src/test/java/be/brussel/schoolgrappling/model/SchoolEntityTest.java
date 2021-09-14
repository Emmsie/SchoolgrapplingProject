package be.brussel.schoolgrappling.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SchoolEntityTest {

    private SchoolEntity school;

    @BeforeEach
    void setUp() {
        school = new SchoolEntity();
    }

    @Test
    void testEquals() {
        //given
        Long id = 1L;
        SchoolEntity anotherSchoolWithSameId = new SchoolEntity();
        //when
        school.setId(id);
        anotherSchoolWithSameId.setId(id);
        //then
        assertThat(school).isEqualTo(anotherSchoolWithSameId);
    }

    @Test
    void getId() {
        //given
        Long id = 1L;
        //when
        school.setId(id);
        //then
        assertEquals(id, school.getId());
    }

    @Test
    void getSchoolName() {
        //given
        String schoolName = "De Knipoog";
        //when
        school.setSchoolName(schoolName);
        //then
        assertEquals(schoolName, school.getSchoolName());
    }

    @Test
    void getTownship() {
        //given
        String township = "Koala town";
        //when
        school.setTownship(township);
        //then
        assertEquals(township, school.getTownship());
    }

    @Test
    void getPostalCode() {
        //given
        Integer postalCode = 3000;
        //when
        school.setPostalCode(postalCode);
        //then
        assertEquals(postalCode, school.getPostalCode());
    }

    @Test
    void getBrusselsSchoolNumber() {
        //given
        Integer brusselsSchoolNumber = 33;
        //when
        school.setBrusselsSchoolNumber(brusselsSchoolNumber);
        //then
        assertEquals(brusselsSchoolNumber, school.getBrusselsSchoolNumber());
    }

    @Test
    void getAddress() {
        //given
        String address = "The place to grapple 53";
        //when
        school.setAddress(address);
        //then
        assertEquals(address, school.getAddress());
    }

    @Test
    void getSchoolEmail() {
        //given
        String email = "onekoala@regenboog.be";
        //when
        school.setSchoolEmail(email);
        //then
        assertEquals(email, school.getSchoolEmail());
    }

    @Test
    void getSchoolPhoneNumber() {
        //given
        String schoolPhoneNumber = "+456123";
        //when
        school.setSchoolPhoneNumber(schoolPhoneNumber);
        //then
        assertEquals(schoolPhoneNumber, school.getSchoolPhoneNumber());
    }

    @Test
    void getContactPersons(){
        //given
        List<ContactEntity> contacts= new ArrayList<>();
        ContactEntity contact1 = mock(ContactEntity.class);
        ContactEntity contact2 = mock(ContactEntity.class);
        contacts.add(contact1);
        contacts.add(contact2);

        //when
        school.setContactPersons(contacts);
        //then
        assertEquals(contacts, school.getContactPersons());
    }
}