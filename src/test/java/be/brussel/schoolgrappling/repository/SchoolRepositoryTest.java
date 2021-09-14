package be.brussel.schoolgrappling.repository;

import be.brussel.schoolgrappling.model.SchoolEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class SchoolRepositoryTest {

    @Autowired
    private SchoolRepository schoolRepository;

    @AfterEach
    void tearDown() {
        schoolRepository.deleteAll();
    }

    @Test
    void getSchoolEntityBySchoolNameWhenSchoolIsInTheDB() {
        //given
        String schoolName = "De Regenboog";
        SchoolEntity school = new SchoolEntity(schoolName, "KoalaTown", 3000, 3, "The place to grapple", "onekoala@regenboog.be", "+0452");
        schoolRepository.save(school);

        //when
        SchoolEntity expected = schoolRepository.getSchoolEntityBySchoolName(schoolName);

        //then
        assertThat(expected).withFailMessage(" should return school with the specific schoolName when a school with this schoolName is in the db").isEqualTo(school);
    }

    @Test
    void getSchoolEntityBySchoolNameWhenSchoolIsNotInTheDB() {
        //given
        String schoolName = "De Regenboog";
        SchoolEntity school = new SchoolEntity(schoolName, "KoalaTown", 3000, 3, "The place to grapple", "onekoala@regenboog.be", "+0452");

        //when
        SchoolEntity expected = schoolRepository.getSchoolEntityBySchoolName(schoolName);

        //then
        assertThat(expected).withFailMessage(" should return null when there is no school with this name in the db").isNull();
    }

    @Test
    void existsSchoolEntityBySchoolNameTestTrue() {
        //given
        String schoolName = "De Regenboog";
        SchoolEntity school = new SchoolEntity(schoolName, "KoalaTown", 3000, 3, "The place to grapple", "onekoala@regenboog.be", "+0452");
        schoolRepository.save(school);

        //when
        boolean expected = schoolRepository.existsSchoolEntityBySchoolName(schoolName);

        //then
        assertThat(expected).withFailMessage(" should return true when schoolName is in the db").isTrue();
    }

    @Test
    void existsSchoolEntityBySchoolNameTestFalse() {
        //given
        String schoolName = "De Regenboog";

        //when
        boolean expected = schoolRepository.existsSchoolEntityBySchoolName(schoolName);

        //then
        assertThat(expected).withFailMessage(" should return false when schoolName is not in the db").isFalse();
    }

    @Test
    void filterSchoolsByTownshipWhenTownshipIsInDB() {
        //given
        String township = "KoalaTown";
        SchoolEntity school1 = new SchoolEntity("De Regenboog", township, 3000, 3, "The place to grapple", "onekoala@regenboog.be", "+0452");
        schoolRepository.save(school1);
        SchoolEntity school2 = new SchoolEntity("De Knipoog", township, 3000, 3, "The place to grapple", "onekoala@regenboog.be", "+0452");
        schoolRepository.save(school2);

        //when
        List<SchoolEntity> expected = schoolRepository.filterSchools(township);

        //then
        assertThat(expected.size()).withFailMessage(" should return a list of schools with a specific township if this township is in the db").isEqualTo(2);
        assertThat(expected).withFailMessage(" should return a list of schools with a specific township if this township is in the db").contains(school1, school2);
    }

    @Test
    void filterSchoolsByTownshipWhenTownshipIsNotInDB() {
        //given
        String township = "KoalaTown";

        //when
        List<SchoolEntity> expected = schoolRepository.filterSchools(township);

        //then
        assertThat(expected.size()).withFailMessage(" should return an empty list of schools if this township is not in the db").isZero();
    }

    @Test
    void filterSchoolsByTownshipWhenTownshipIsNotOnlyTownshipInDB() {
        //given
        String township = "KoalaTown";
        SchoolEntity school1 = new SchoolEntity("De Regenboog", "other township", 3000, 3, "The place to grapple", "onekoala@regenboog.be", "+0452");
        schoolRepository.save(school1);
        SchoolEntity school2 = new SchoolEntity("De Knipoog", township, 3000, 3, "The place to grapple", "onekoala@regenboog.be", "+0452");
        schoolRepository.save(school2);

        //when
        List<SchoolEntity> expected = schoolRepository.filterSchools(township);

        //then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected).withFailMessage(" should return a list of schools with a specific township if this township is in the db").contains(school2);
    }

    @Test
    void filterSchoolsByPostalCodeWhenPostalCodeIsInDB() {
        //given
        Integer postalCode = 3000;
        SchoolEntity school1 = new SchoolEntity("De Regenboog", "KoalaTown", postalCode, 3, "The place to grapple", "onekoala@regenboog.be", "+0452");
        schoolRepository.save(school1);
        SchoolEntity school2 = new SchoolEntity("De Knipoog", "KoalaTown", postalCode, 3, "The place to grapple", "onekoala@regenboog.be", "+0452");
        schoolRepository.save(school2);

        //when
        List<SchoolEntity> expected = schoolRepository.filterSchools(postalCode.toString());

        //then
        assertThat(expected.size()).withFailMessage(" should return a list of schools with a specific postal code if this postal code is in the db").isEqualTo(2);
        assertThat(expected).withFailMessage(" should return a list of schools with a specific postal code if this postal code is in the db").containsOnly(school1, school2);
    }

    @Test
    void filterSchoolsByPostalCodeWhenPostalCodeIsNotInDB() {
        //given
        int postalCode = 3000;

        //when
        List<SchoolEntity> expected = schoolRepository.filterSchools(Integer.toString(postalCode));

        //then
        assertThat(expected.size()).withFailMessage(" should return an empty list of schools is not in the db").isZero();
    }

    @Test
    void filterSchoolsByPostalCodeWhenPostalCodeIsNotOnlyPostelCodeInDB() {
        //given
        int postalCode = 3000;
        SchoolEntity school1 = new SchoolEntity("De Regenboog", "KoalaTown", 5200, 3, "The place to grapple", "onekoala@regenboog.be", "+0452");
        schoolRepository.save(school1);
        SchoolEntity school2 = new SchoolEntity("De Knipoog", "KoalaTown", postalCode, 3, "The place to grapple", "onekoala@regenboog.be", "+0452");
        schoolRepository.save(school2);

        //when
        List<SchoolEntity> expected = schoolRepository.filterSchools(Integer.toString(postalCode));

        //then
        assertThat(expected.size()).withFailMessage(" should return a list of schools with a specific postal code if this postal code is in the db").isEqualTo(1);
        assertThat(expected).withFailMessage(" should return a list of schools with a specific  postal code if this postal code is in the db").contains(school2);
    }
}