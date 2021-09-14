package be.brussel.schoolgrappling.repository;

import be.brussel.schoolgrappling.model.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, Long> {

    public boolean existsContactEntityByUserName(String userName);

    public ContactEntity getContactEntityByUserName(String userName);

//    @Query("SELECT c FROM contacts c INNER JOIN schools s ON c.school = s.schoolName WHERE s.schoolName LIKE %?1%")
//    public List<ContactEntity> filterContactsBySchool(String keyword);

    public List<ContactEntity> findAllBySchool_SchoolName(String schoolName);

    @Query("SELECT c FROM contacts c WHERE CONCAT(c.firstName, ' ', c.lastName) LIKE %?1%") //Stole this from Manon :), removed LOWER clause.
    public List<ContactEntity> filterContactsByName(String keyword);

}
