package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Repositories;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("select t from Teacher t where t.name = :name")
    public List<Student> GetByName(@Param("name") String name);

    @Query("select t from Teacher t where t.lastName = :lastName")
    public List<Student> GetByLastName(@Param("lastName") String lastName);
}
