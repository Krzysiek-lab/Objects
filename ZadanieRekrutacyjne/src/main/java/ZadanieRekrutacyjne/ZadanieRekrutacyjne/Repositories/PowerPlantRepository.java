package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Repositories;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entity.PowerPlant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PowerPlantRepository extends JpaRepository<PowerPlant, Integer> {

    @Query("select p from PowerPlant p where p.name = :name")
    List<PowerPlant> getByName(@Param("name") String name);

    @Query("select p from PowerPlant p where p.power = :power")
    List<PowerPlant> getByPower(@Param("power") Double power);

    @Query("select p from PowerPlant p where p.place = :place")
    List<PowerPlant> getByPlace(@Param("place") String place);


    ////////////////
//to nowe
    @Query("select p from PowerPlants p where p.power in (select p from PowerPlant p GROUP BY p.power having " +
            "count(p.power) >=2")
    List<PowerPlant> getDuplicatePowerValues();

//    @Query("select p from PowerPlant p GROUP BY p.power having count(p.power) >=2")
//    List<PowerPlant> getDuplicatePowerValues();


    @Query("select p from PowerPlant p GROUP BY p.power having count(p.power) =1")
    List<PowerPlant> getDistinctPowerValues();

    //to nowe
    @Query("select p from PowerPlants p where p.place in (select p from PowerPlant p GROUP BY p.place having " +
            "count(p.place) >=2")
    List<PowerPlant> getDuplicatePlaceValues();


//    @Query("select p from PowerPlant p GROUP BY p.place having count(p.place) >=2")
//    List<PowerPlant> getDuplicatePlaceValues();


    @Query("select p from PowerPlant p GROUP BY p.place having count(p.place) =1")
    List<PowerPlant> getDistinctPlaceValues();

    //to nowe
    @Query("select p from PowerPlants p where p.name in (select p from PowerPlant p GROUP BY p.name having " +
            "count(p.name) >=2")
    List<PowerPlant> getDuplicateNameValues();


//    @Query("select p from PowerPlant p GROUP BY p.name having count(p.name) >=2")
//    List<PowerPlant> getDuplicateNameValues();


    @Query("select p from PowerPlant p GROUP BY p.name having count(p.name) =1")
    List<PowerPlant> getDistinctNameValues();
////////////

}