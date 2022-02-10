package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Interfaces;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entity.Event;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entity.PowerPlant;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels.EventViewModel;

import java.util.Collection;

public interface AddUpdateGetEvent {
    EventViewModel eventToViewModel(Event event);

    Event update(EventViewModel eventViewModel);

    Collection<PowerPlant> getAllPowerPlants();
}
