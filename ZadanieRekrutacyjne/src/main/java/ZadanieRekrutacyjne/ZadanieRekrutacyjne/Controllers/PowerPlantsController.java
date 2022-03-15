package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Controllers;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Repositories.PowerPlantRepository;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Services.PlantService;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Services.Queries;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels.ChoiceViewModel;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels.PlantViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class PowerPlantsController {


    private final PowerPlantRepository powerPlantRepository;
    private final PlantService plantService;
    private final Queries queries;


    @GetMapping("main")
    public String getMainPage(Model model) {
        model.addAttribute("choice", new ChoiceViewModel());
        return "main";
    }

    @PostMapping("main")
    public String mainForm(@ModelAttribute("choice") ChoiceViewModel choice) {
        String val = choice.getChoice();
        return "redirect:/Site/" + val;
    }


    @PostMapping("Site")
    public String Site(String given, Model model) {
        if (powerPlantRepository.findAll().size() == 0) {
            model.addAttribute("status", "database is empty, add entities to see result of queries");
        } else {
            switch (given) {
                case "power":
                    model.addAttribute("valDuplicate", queries.getDuplicatesPower());
                    model.addAttribute("valDistinct", powerPlantRepository.getDistinctPowerValues());
                    break;
                case "name":
                    model.addAttribute("valDuplicate", queries.getDuplicatesName());
                    model.addAttribute("valDistinct", powerPlantRepository.getDistinctNameValues());
                    break;
                case "place":
                    model.addAttribute("valDuplicate", queries.getDuplicatesPlace());
                    model.addAttribute("valDistinct", powerPlantRepository.getDistinctPlaceValues());
                    break;
            }
        }
        return "main2";
    }


    @GetMapping("savePowerPlant")
    public String getForm(Model model) {
        model.addAttribute("plant", new PlantViewModel());
        return "save";
    }


    @PostMapping("savePowerPlant")
    public String addPowerPlant(@ModelAttribute("plant") @Valid PlantViewModel plant, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            if (plant.getId() == null) {
                return "save";
            } else return "updatePowerPlant";
        }
        if (plant.getId() == null) {
            plantService.add(plant);
        } else {
            plantService.update(plant);
        }
        return "redirect:/main";
    }


    @GetMapping("updatePowerPlant/{id}")
    public String updatePowerPlant(@PathVariable(value = "id") Integer id, Model model) {
        var find = powerPlantRepository.getOne(id);
        var plants = plantService.powerPlantToViewModel(find);
        model.addAttribute("plant", plants);
        return "updatePowerPlant";
    }


    @GetMapping("powerPlants")
    public String findAll(Model model) {
        model.addAttribute("plant", powerPlantRepository.findAll(Sort.by(Sort.Direction.ASC, "name")));
        return "allPlants";
    }


    @GetMapping("powerPlantsByPower/{power}")
    public String findByPower(@PathVariable("power") Double power, Model model) {
        model.addAttribute("plantByPower", powerPlantRepository.getByPower(power));
        return "plantByPower";
    }

    @GetMapping("powerPlantsByName/{name}")
    public String findByName(@PathVariable("name") String name, Model model) {
        model.addAttribute("plantByName", powerPlantRepository.getByName(name));
        return "plantByName";
    }

    @GetMapping("powerPlantsByPlace/{place}")
    public String findByPlace(@PathVariable("place") String place, Model model) {
        model.addAttribute("plantByPlace", powerPlantRepository.getByPlace(place));
        return "plantByPlace";
    }

    @GetMapping("delete/powerPlants/{id}")
    public String deleteById(@PathVariable(value = "id") Integer id) {
        powerPlantRepository.deleteById(id);
        return "redirect:/powerPlants";
    }


}