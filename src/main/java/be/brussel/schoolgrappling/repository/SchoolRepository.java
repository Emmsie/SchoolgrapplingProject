package be.brussel.schoolgrappling.repository;

import be.brussel.schoolgrappling.model.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolRepository extends JpaRepository<SchoolEntity, Long> {

    SchoolEntity getSchoolEntityBySchoolName(String schoolName);

    boolean existsSchoolEntityBySchoolName(String schoolName);

    @Query("SELECT s FROM schools s WHERE CONCAT(s.postalCode, '') LIKE %?1% OR LOWER(s.township) LIKE LOWER(CONCAT('%', ?1,'%'))")
    List<SchoolEntity> filterSchools(String keyword);

}
