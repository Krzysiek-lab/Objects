package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Services;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entity.Event;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entity.PowerPlant;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Interfaces.AddUpdateGetPlant;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Interfaces.PlantServiceToViewModel;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Interfaces.powerForPowerPlant;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Repositories.EventRepository;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Repositories.PowerPlantRepository;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels.PlantViewModel;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.*;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class PlantService implements powerForPowerPlant, AddUpdateGetPlant, PlantServiceToViewModel {


    private final EventRepository eventRepository;
    private final PowerPlantRepository powerPlantRepository;

    public Map<Integer, String> powerForPowerPlantPerDay(Timestamp date) {
        List<Event> list = new ArrayList<>(getAllEvents());
        Map<Integer, String> map = new HashMap<>();
        list.stream()
                .filter(e -> e.startDate.equals(date))
                .forEach(e -> map.put(e.id, e.typeOfEvent));
        return map;
    }


    public Collection<Event> getAllEvents() {
        return eventRepository.findAll();
    }


    public void add(PlantViewModel plantViewModel) {
        var p = PowerPlant.builder().
                name(plantViewModel.getName())
                .power(plantViewModel.getPower())
                .events(plantViewModel.getListOfEventsForPlant())
                .build();
        powerPlantRepository.save(p);
    }

    public PlantViewModel powerPlantToViewModel(PowerPlant powerPlant) {
        return PlantViewModel.builder()
                .id(powerPlant.getId())
                .name(powerPlant.getName())
                .power(powerPlant.getPower())
                .listOfEventsForPlant(powerPlant.getEvents())
                .build();

    }


    public PowerPlant update(PlantViewModel plantViewModel) {
        var plant = powerPlantRepository.getOne(plantViewModel.getId());

        plant.setName(plantViewModel.getName());
        plant.setPower(plantViewModel.getPower());

        return powerPlantRepository.save(plant);
    }
}
