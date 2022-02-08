package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Repositories;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entity.PowerPlant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PowerPlantRepository extends JpaRepository<PowerPlant, Integer> {

    @Query("select p from PowerPlant p where p.name = :name")
    PowerPlant getByName(@Param("name") String name);
}