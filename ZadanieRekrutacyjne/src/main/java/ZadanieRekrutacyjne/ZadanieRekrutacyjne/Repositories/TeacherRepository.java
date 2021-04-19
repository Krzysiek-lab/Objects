package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Repositories;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    // połaczenie do bazy danych z tabelą Teacher z Id typu Long

    // zapytania do szukania danego Teacher'a w kolumnach name i lastName
    @Query("select t from Teacher t where t.name = :name")
    public List<Teacher> GetByName(@Param("name") String name);

    @Query("select t from Teacher t where t.lastName = :lastName")
    public List<Teacher> GetByLastName(@Param("lastName") String lastName);
}
