package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Services;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entity.Event;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entity.PowerPlant;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Interfaces.AddPlant;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Interfaces.EventServiceToViewModel;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Interfaces.NumberOfFailureEventsForId;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Repositories.EventRepository;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Repositories.PowerPlantRepository;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels.EventViewModel;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class EventService implements NumberOfFailureEventsForId, AddPlant, EventServiceToViewModel {


    private final PowerPlantRepository powerPlantRepository;
    private final EventRepository eventRepository;


    public long NumberOfFailureEventsForId(int givenId) {
        var plant = getAllPowerPlants().stream()
                .filter(e -> e.id == givenId)
                .findAny();

        return plant.map(value -> value.getEvents()
                .stream()
                .filter(z -> z.typeOfEvent.equals("AWARIA"))
                .count()).orElse(0L);
    }

    public void add(EventViewModel eventViewModel) {
        var e = Event.builder().
                typeOfEvent(eventViewModel.getTypeOfEvent())
                .powerDrop(eventViewModel.getPowerDrop())
                .powerPlant(eventViewModel.getPlantsForEvent())
                .endDate(eventViewModel.getEndDate())
                .startDate(eventViewModel.getStartDate())
                .build();
        eventRepository.save(e);
    }

    public EventViewModel eventToViewModel(Event event) {
        return EventViewModel.builder()
                .id(event.getId())
                .typeOfEvent(event.typeOfEvent)
                .powerDrop(event.powerDrop)
                .endDate(event.endDate)
                .startDate(event.startDate)
                .build();

    }


    public Event update(EventViewModel eventViewModel) {
        var event = eventRepository.getOne(eventViewModel.getId());

        event.setTypeOfEvent(eventViewModel.getTypeOfEvent());
        event.setStartDate(eventViewModel.getStartDate());
        event.setEndDate(eventViewModel.getEndDate());
        event.setPowerDrop(eventViewModel.getPowerDrop());

        return eventRepository.save(event);
    }

    public Collection<PowerPlant> getAllPowerPlants() {
        return powerPlantRepository.findAll();
    }

}
