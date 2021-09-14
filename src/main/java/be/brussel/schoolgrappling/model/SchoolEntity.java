package be.brussel.schoolgrappling.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"contactPersons"})
@Entity(name = "schools")
public class SchoolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "school_sequence")
    @SequenceGenerator(name="school_sequence",
            sequenceName = "school_sequence",
            allocationSize = 1)
    private Long id;

    @NotBlank(message = "Name is mandatory, please fill in a school name")
    private String schoolName;

    private String township;

    private Integer postalCode;

    private Integer brusselsSchoolNumber;

    private String address;

    @Email(message = "Email should be in a valid format, please fill in a valid email format")
    private String schoolEmail;

    private String schoolPhoneNumber;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "schools_contacts",
            joinColumns = @JoinColumn(name = "school_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id"))
    private List<ContactEntity> contactPersons = new ArrayList<>();

    @Builder
    public SchoolEntity(String schoolName, String township, int postalCode, int brusselsSchoolNumber, String address, String schoolEmail, String schoolPhoneNumber) {
        this.schoolName = schoolName;
        this.township = township;
        this.postalCode = postalCode;
        this.brusselsSchoolNumber = brusselsSchoolNumber;
        this.address = address;
        this.schoolEmail = schoolEmail;
        this.schoolPhoneNumber = schoolPhoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchoolEntity)) return false;
        SchoolEntity that = (SchoolEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
