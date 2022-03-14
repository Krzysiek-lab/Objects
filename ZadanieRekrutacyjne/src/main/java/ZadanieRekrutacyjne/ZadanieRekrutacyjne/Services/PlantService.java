package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Services;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entity.PowerPlant;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Interfaces.AddUpdateGetPlant;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Interfaces.PlantServiceToViewModel;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Repositories.PowerPlantRepository;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels.PlantViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlantService implements AddUpdateGetPlant, PlantServiceToViewModel {


    private final PowerPlantRepository powerPlantRepository;


    public void add(PlantViewModel plantViewModel) {
        var p = PowerPlant.builder().
                name(plantViewModel.getName())
                .power(plantViewModel.getPower())
                .place(plantViewModel.getPlace())
                .build();
        powerPlantRepository.save(p);
    }

    public PlantViewModel powerPlantToViewModel(PowerPlant powerPlant) {
        return PlantViewModel.builder()
                .id(powerPlant.getId())
                .name(powerPlant.getName())
                .power(powerPlant.getPower())
                .place(powerPlant.getPlace())
                .build();

    }


    public PowerPlant update(PlantViewModel plantViewModel) {
        var plant = powerPlantRepository.getOne(plantViewModel.getId());
        plant.setName(plantViewModel.getName());
        plant.setPower(plantViewModel.getPower());
        return powerPlantRepository.save(plant);
    }

}
