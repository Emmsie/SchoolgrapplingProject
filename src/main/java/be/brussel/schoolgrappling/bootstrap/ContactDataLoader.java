package be.brussel.schoolgrappling.bootstrap;

import be.brussel.schoolgrappling.model.*;
import be.brussel.schoolgrappling.repository.SchoolRepository;
import be.brussel.schoolgrappling.repository.TournamentRepository;
import be.brussel.schoolgrappling.repository.ContactRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Slf4j
@Component
@AllArgsConstructor
public class ContactDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private ContactRepository contactRepository;
    private SchoolRepository schoolRepository;
    private TournamentRepository tournamentRepository;

    private List<ContactEntity> getContacts() {

        SchoolEntity knipoog = new SchoolEntity(
                "De Knipoog",
                "KoalaTown",
                3000,
                3,
                "The place to grapple",
                "onekoala@regenboog.be",
                "+0452");

        SchoolEntity regenboog = new SchoolEntity(
                "De Regenboog",
                "Brussel",
                1000,
                3,
                "The place to grapple",
                "regenboog@regenboog.be",
                "+0452");

        schoolRepository.save(knipoog);
        schoolRepository.save(regenboog);

        TournamentEntity tournament1 = TournamentEntity.builder()
                .tournamentName("Schoolgrappling 1")
                .date(LocalDate.now())
                .deadlineDate(LocalDate.now())
                .startingHour(LocalTime.now())
                .venue("Location 1")
                .city("somewhere 1")
                .maxCapacity(250)
                .maxPool(7)
                .gradeMorning(Grade.PRIMARY_1)
                .gradeAfternoon(Grade.PRIMARY_2)
                .price(3.00)
                .build();

        TournamentEntity tournament2 = TournamentEntity.builder()
                .tournamentName("Schoolgrappling 2")
                .date(LocalDate.now())
                .deadlineDate(LocalDate.now())
                .startingHour(LocalTime.now())
                .venue("Location 2")
                .city("somewhere 2")
                .maxCapacity(250)
                .maxPool(6)
                .gradeMorning(Grade.PRIMARY_3)
                .gradeAfternoon(Grade.PRIMARY_4)
                .price(4.00)
                .build();

        tournamentRepository.save(tournament1);
        tournamentRepository.save(tournament2);

        ContactEntity dieter = ContactEntity.builder()
                .firstName("Dieter")
                .lastName("De Rouck")
                .userName("Dieter")
                .email("somemail@gmail.com")
                .function(Function.VAKLEERKRACHT)
                .phoneNumber("4567895646")
//                .school(knipoog)
                .city("KoalaTown").build();

        dieter.addSchool(knipoog);

        ContactEntity matteo = ContactEntity.builder()
                .firstName("Matteo")
                .lastName("DK")
                .userName("Mat")
                .email("mdk@gmail.com")
                .function(Function.LO_LEERKRACHT)
                .phoneNumber("6546546")
//                .school(regenboog)
                .city("City").build();

        matteo.addSchool(regenboog);

        List<ContactEntity> contacts = new ArrayList<>();
        contacts.add(dieter);
        contacts.add(matteo);

        return contacts;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        contactRepository.saveAll(getContacts());
    }
}