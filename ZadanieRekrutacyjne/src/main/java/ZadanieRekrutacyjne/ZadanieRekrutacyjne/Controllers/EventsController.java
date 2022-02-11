package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Controllers;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Repositories.EventRepository;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Repositories.PowerPlantRepository;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Services.EventService;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Services.PlantService;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels.EventViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class EventsController {

    private final PowerPlantRepository powerPlantRepository;
    private final PlantService PlantService;
    private final EventService eventService;
    private final EventRepository eventRepository;


    @ResponseBody
    @GetMapping("events/{date}")
    public Map<Integer, String> findByDate(@PathVariable("date") String date) {
        var dates = getDate(date);
        return PlantService.powerForPowerPlantPerDay(dates);
    }

    private Timestamp getDate(String date) {
        return Timestamp.valueOf(date);
    }


    @GetMapping("eventForm")
    public String getForm(Model model) {
        model.addAttribute("event", new EventViewModel());
        return "saveEvent";
    }

    @PostMapping("saveEvent")
    public String addEvent(@ModelAttribute("event") @Valid EventViewModel eventViewModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            if (eventViewModel.getId() == null) {
                return "saveEvent";

            } else {
                return "updateEvent";
            }
        }
        if (eventViewModel.getId() == null) {
            eventService.add(eventViewModel);
        } else {
            eventService.update(eventViewModel);
        }
        return "redirect:/events";
    }


    @GetMapping("updateEvent/{id}")
    public String updateEvent(@PathVariable(value = "id") Integer id, Model model) {
        var find = eventRepository.getOne(id);
        var events = eventService.eventToViewModel(find);
        model.addAttribute("event", events);
        return "updateEvent";
    }


    @GetMapping("events")
    public String findAll(Model model) {
        model.addAttribute("event", eventRepository.findAll());
        return "allEvents";
    }


    @GetMapping("events/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("event", eventRepository.getOne(id));

        return "event";
    }


    @DeleteMapping("delete/event/{id}")
    public String deleteById(@PathVariable(value = "id") Integer id) {
        eventRepository.deleteById(id);
        return "redirect:/events";
    }


}

