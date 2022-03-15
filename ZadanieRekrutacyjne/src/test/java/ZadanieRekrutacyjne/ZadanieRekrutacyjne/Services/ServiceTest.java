package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Services;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entity.PowerPlant;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Repositories.PowerPlantRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ServiceTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    PowerPlantRepository powerPlantRepository;


    @Test
    public void should_find_PowerPlant_By_Name() {
        PowerPlant powerPlant = new PowerPlant();
        powerPlant.setPower(1000D);
        powerPlant.setName("Elektrownia");
        powerPlant.setPlace("Polska");
        testEntityManager.persist(powerPlant);
        List<PowerPlant> PowerPlantEntities = new ArrayList<>(powerPlantRepository.getByName(powerPlant.getName()));
        Assertions.assertThat(PowerPlantEntities).hasSize(1).contains(powerPlant);
    }


    @Test
    public void should_find_all_PowerPlants() {
        PowerPlant powerPlant = new PowerPlant();
        powerPlant.setPower(1000D);
        powerPlant.setName("Elektrownia");
        powerPlant.setPlace("Polska");
        testEntityManager.persist(powerPlant);
        Assertions.assertThat(powerPlantRepository.findAll()).hasSize(1).contains(powerPlant);
    }


}