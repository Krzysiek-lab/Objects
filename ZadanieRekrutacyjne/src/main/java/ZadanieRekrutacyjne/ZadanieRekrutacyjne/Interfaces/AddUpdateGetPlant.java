package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Interfaces;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entity.Event;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entity.PowerPlant;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels.PlantViewModel;

import java.util.Collection;

public interface AddUpdateGetPlant {
    void add(PlantViewModel plantViewModel);

    PowerPlant update(PlantViewModel plantViewModel);

    Collection<Event> getAllEvents();
}
