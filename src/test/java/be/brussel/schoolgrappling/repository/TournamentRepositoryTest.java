package be.brussel.schoolgrappling.repository;

import be.brussel.schoolgrappling.model.TournamentEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TournamentRepositoryTest {

    @Autowired
    private TournamentRepository tournamentRepository;

    @BeforeEach
    void setUp() throws Exception {

    }

    @Test
    void findTournamentEntityByTournamentName() throws Exception{
        // given
        String tournamentName = "Schoolgrappling 15";
        TournamentEntity tournament = TournamentEntity.builder()
                .id(1L)
                .tournamentName(tournamentName)
                .date(LocalDate.now())
                .deadlineDate(LocalDate.now())
                .startingHour(LocalTime.now())
                .venue("Location")
                .city("somewhere")
                .maxCapacity(250)
                .maxPool(6)
                .price(3.00)
                .build();
        tournamentRepository.save(tournament);

        // when
        TournamentEntity expected = tournamentRepository.findTournamentEntityByTournamentName(tournamentName);

        // then
        assertEquals(expected, tournament);
    }

    @Test
    void existsTournamentEntityByTournamentName() throws Exception{
        // given
        String tournamentName = "Schoolgrappling";
        TournamentEntity tournament = TournamentEntity.builder()
                .id(1L)
                .tournamentName(tournamentName)
                .date(LocalDate.now())
                .deadlineDate(LocalDate.now())
                .startingHour(LocalTime.now())
                .venue("Location")
                .city("somewhere")
                .maxCapacity(250)
                .maxPool(6)
                .price(3.00)
                .build();
        tournamentRepository.save(tournament);

        // when
        boolean expected = tournamentRepository.existsTournamentEntityByTournamentName(tournamentName);

        // then
        assertTrue(expected);
    }

    @Test
    void filterTournamentsByName() throws Exception {
        // given
        String tournamentName = "Tournament Name";
        TournamentEntity tournament = TournamentEntity.builder()
                .id(2L)
                .tournamentName(tournamentName)
                .date(LocalDate.now())
                .deadlineDate(LocalDate.now())
                .startingHour(LocalTime.now())
                .venue("Location")
                .city("somewhere")
                .maxCapacity(250)
                .maxPool(6)
                .price(3.00)
                .build();
        tournamentRepository.save(tournament);

        TournamentEntity tournament2 = TournamentEntity.builder()
                .id(3L)
                .tournamentName(tournamentName + "12")
                .date(LocalDate.now())
                .deadlineDate(LocalDate.now())
                .startingHour(LocalTime.now())
                .venue("Location")
                .city("somewhere")
                .maxCapacity(250)
                .maxPool(6)
                .price(3.00)
                .build();
        tournamentRepository.save(tournament2);

        // when
        List<TournamentEntity> expected = tournamentRepository.filterTournamentsByName(tournamentName);

        // then
        assertEquals(expected.size(), 2);
        assertEquals(expected.get(0), tournament);
        assertEquals(expected.get(1), tournament2);
    }

    @Test
    void filterTournamentByLocation() throws Exception{
        // given
        String location = "Location";
        TournamentEntity tournament3 = TournamentEntity.builder()
                .id(4L)
                .tournamentName("tournamentName")
                .date(LocalDate.now())
                .deadlineDate(LocalDate.now())
                .startingHour(LocalTime.now())
                .venue(location)
                .city("somewhere")
                .maxCapacity(250)
                .maxPool(6)
                .price(3.00)
                .build();
        tournamentRepository.save(tournament3);

        TournamentEntity tournament4 = TournamentEntity.builder()
                .id(5L)
                .tournamentName("tournamentName 2")
                .date(LocalDate.now())
                .deadlineDate(LocalDate.now())
                .startingHour(LocalTime.now())
                .venue(location)
                .city("somewhere")
                .maxCapacity(250)
                .maxPool(6)
                .price(3.00)
                .build();
        tournamentRepository.save(tournament4);

        // when
        List<TournamentEntity> expected = tournamentRepository.filterTournamentsByLocation(location);

        // then
        assertEquals(expected.size(), 2);
        assertEquals(expected.get(0), tournament3);
        assertEquals(expected.get(1), tournament4);
    }
}