package be.brussel.schoolgrappling.model;

import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "contacts")
public class ContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "contact_sequence")
    @SequenceGenerator(name="contact_sequence",
            sequenceName = "contact_sequence",
            allocationSize = 1)
    private Long id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Surname is required")
    private String lastName;

    @NotBlank(message = "Username is required")
    private String userName;

    @Email(message = "Email is not valid")
    @NotBlank(message = "Email address is required")
    private String email;

    @Enumerated(EnumType.STRING)
    private Function function;

    @NotEmpty(message = "Phone number is required")
    private String phoneNumber;

    @ManyToMany(mappedBy = "contactPersons")
    @NotNull(message = "School is required")
    private List<SchoolEntity> school = new ArrayList<>();

    private String city;

    public void addSchool(SchoolEntity schoolEntity){
        school.add(schoolEntity);
    }

    @Builder
    public ContactEntity(String firstName,
                         String lastName,
                         String userName,
                         String email,
                         Function function,
                         String phoneNumber,
                         SchoolEntity school,
                         String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.function = function;
        this.phoneNumber = phoneNumber;
        this.city = city;
    }
}