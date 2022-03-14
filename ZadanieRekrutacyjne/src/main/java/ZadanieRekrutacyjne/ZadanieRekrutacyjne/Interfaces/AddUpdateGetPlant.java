package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Interfaces;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entity.PowerPlant;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels.PlantViewModel;


public interface AddUpdateGetPlant {
    void add(PlantViewModel plantViewModel);

    PowerPlant update(PlantViewModel plantViewModel);

}
