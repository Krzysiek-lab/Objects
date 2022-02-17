package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Services;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entity.Event;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entity.PowerPlant;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Enums.EventType;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Repositories.EventRepository;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Repositories.PowerPlantRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ServiceTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    PowerPlantRepository powerPlantRepository;

    @Autowired
    EventRepository eventRepository;

    List<Event> list;


    @BeforeEach
    void setUp() {
        Event event = new Event();
        event.setTypeOfEvent(EventType.AWARIA);
        list = new ArrayList<>();
        list.add(event);
    }


    @Test
    public void should_find_PowerPlant_By_Name() {
        PowerPlant powerPlant = new PowerPlant();
        powerPlant.setPower(1000D);
        powerPlant.setName("Elektrownia");
        powerPlant.setEvents(list);
        testEntityManager.persist(powerPlant);
        List<PowerPlant> PowerPlantEntities = new ArrayList<>();
        PowerPlantEntities.add(powerPlantRepository.getByName(powerPlant.getName()));
        Assertions.assertThat(PowerPlantEntities).hasSize(1).contains(powerPlant);
    }

    @Test
    public void should_find_Event_By_Start_Date() {
        PlantService plantService = new PlantService(eventRepository, powerPlantRepository);
        Event event = new Event();
        event.setStartDate(LocalDate.of(2022, 2, 11));
        event.setPowerDrop(230D);
        event.setTypeOfEvent(EventType.AWARIA);
        event.setEndDate(LocalDate.of(2022, 2, 13));
        testEntityManager.persist(event);
        var value = plantService.powerForPowerPlantPerDay(
                LocalDate.of(2022, 2, 11));
        org.junit.jupiter.api.Assertions.assertEquals(value.size(), 1);
    }


    @Test
    public void should_find_all_PowerPlants() {
        PowerPlant powerPlant = new PowerPlant();
        powerPlant.setPower(1000D);
        powerPlant.setName("Elektrownia");
        powerPlant.setEvents(list);
        testEntityManager.persist(powerPlant);
        Assertions.assertThat(powerPlantRepository.findAll()).hasSize(1).contains(powerPlant);
    }


    @Test
    public void should_find_all_Events() {
        Event event = new Event();
        event.setStartDate(LocalDate.of(2022, 2, 11));
        event.setPowerDrop(230D);
        event.setTypeOfEvent(EventType.AWARIA);
        event.setEndDate(LocalDate.of(2022, 2, 13));
        testEntityManager.clear();
        testEntityManager.persist(event);
        Assertions.assertThat(eventRepository.findAll()).hasSize(1).contains(event);
    }
}