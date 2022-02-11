package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Controllers;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Repositories.EventRepository;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Repositories.PowerPlantRepository;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Services.EventService;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Services.PlantService;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Services.UserService;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels.PlantViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class PowerPlantsController {


    private final PowerPlantRepository powerPlantRepository;
    private final EventRepository eventRepository;
    private final PlantService plantService;
    private final EventService eventService;
    private final UserService userService;


    @GetMapping("form")
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
        return "redirect:/powerPlants";
    }


    @GetMapping("updatePowerPlant/{id}")
    public String updatePowerPlant(@PathVariable(value = "id") Integer id, Model model) {
        var find = powerPlantRepository.getOne(id);
        var plants = plantService.powerPlantToViewModel(find);
        model.addAttribute("plant", plants);
        return "updatePowerPlant";
    }


    @ResponseBody
    @GetMapping("failures/{id}")
    public long failures(@PathVariable("id") int id) {
        return eventService.NumberOfFailureEventsForId(id);
    }


    @GetMapping("powerPlants")
    public String findAll(Model model) {
        model.addAttribute("plant", powerPlantRepository.findAll(Sort.by(Sort.Direction.ASC, "name")));
        model.addAttribute("user",userService.getCurrentUser());
        return "allPlants";
    }


    @GetMapping("powerPlants/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("plant", powerPlantRepository.getOne(id));
        return "plant";
    }


    @GetMapping("powerPlantsByName/{name}")
    public String findByName(@PathVariable("name") String name, Model model) {
        model.addAttribute("plantByName", powerPlantRepository.getByName(name));
        return "plantByName";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("delete/powerPlants/{id}")
    public String deleteById(@PathVariable(value = "id") Integer id) {
        powerPlantRepository.deleteById(id);
        return "redirect:/powerPlants";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("addEventToPlant/{plantId}/{eventId}")
    public String addEventToPlant(@PathVariable(value = "plantId") Long plantId,
                                  @PathVariable(value = "eventId")
                                          Long eventId, Model model) {

        if (powerPlantRepository.findById(Math.toIntExact(plantId)).isPresent()
                && eventRepository.findById(Math.toIntExact(eventId)).isPresent()) {
            var pl = powerPlantRepository.getOne(Math.toIntExact(plantId));
            var ev = eventRepository.getOne(Math.toIntExact(eventId));


            ev.setPowerPlant(pl);
            var eventsForPlant = pl.getEvents();
            eventsForPlant.add(ev);
            pl.setEvents(eventsForPlant);

            powerPlantRepository.save(pl);
        }

        var t = powerPlantRepository.getOne(Math.toIntExact(plantId)).getEvents();
        model.addAttribute("plant", t);

        return "plant_events";
    }

}