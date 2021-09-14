package be.brussel.schoolgrappling.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity(name = "tournaments")
public class TournamentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name of the tournament cannot be empty.")
    private String tournamentName;

    @NotNull(message = "Date cannot be empty.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull(message = "Date for the deadline cannot be empty.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadlineDate;

    @NotNull(message = "Starting hour cannot be empty.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startingHour;

    @NotNull(message = "Max capacity cannot be empty.")
    private Integer maxCapacity;

    @NotNull(message = "Pool size cannot be empty.")
    @Min(value = 5, message = "Pool size cannot be lower than 5")
    @Max(value = 9, message = "Pool size cannot be higher than 9.")
    private Integer maxPool;

    @NotNull(message = "Price cannot be empty.")
    private Double price;

    @NotBlank(message = "Location cannot be empty.")
    private String venue;

    @NotBlank(message = "city cannot be empty.")
    private String city;

    @ManyToMany
    @ToString.Exclude
    private Set<SchoolEntity> schools = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Grade gradeMorning;

    @Enumerated(EnumType.STRING)
    private Grade gradeAfternoon;

    private String dutchDescription;

    private String frenchDescription;

    @Builder
    public TournamentEntity(Long id, String tournamentName, LocalDate date, LocalDate deadlineDate, LocalTime startingHour,
                            Integer maxCapacity, Integer maxPool, String venue, String city, Double price,
                            Set<SchoolEntity> schools, Grade gradeMorning, Grade gradeAfternoon, String dutchDescription, String frenchDescription) {
        this.id = id;
        this.tournamentName = tournamentName;
        this.date = date;
        this.deadlineDate = deadlineDate;
        this.startingHour = startingHour;
        this.maxCapacity = maxCapacity;
        this.maxPool = maxPool;
        this.price = price;
        this.venue = venue;
        this.city = city;
        this.schools = schools;
        this.gradeMorning = gradeMorning;
        this.gradeAfternoon = gradeAfternoon;
        this.dutchDescription = dutchDescription;
        this.frenchDescription = frenchDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TournamentEntity that = (TournamentEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
