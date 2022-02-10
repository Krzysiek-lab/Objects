package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Controllers;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Repositories.UserRepository;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Services.UserService;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels.UserViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
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
public class RegistrationController {

    private final UserService userService;
    private final UserRepository userRepository;


    @GetMapping({"/login"})
    public String loginPage() {
        return "login-form";
    }


    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new UserViewModel());
        return "registration-form";
    }


    @PostMapping("/registration")
    public String registrationUsersAdding(@ModelAttribute("user") @Valid UserViewModel user2,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            if (user2.getId() == null) {
                return "registration-form";
            } else {
                return "registration-formUpdate";
            }
        } else {
            if (user2.getId() == null) {
                userService.add(user2);
            } else {
                userService.update(user2);
            }
        }
        return "redirect:/login";
    }


    @Secured("ROLE_ADMIN")
    @GetMapping("users/update/{id}")
    public String updateUser(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("user", userRepository.getOne(id));
        return "registration-formUpdate";
    }


    @GetMapping("allUsers")
    public String findAll(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "allUsers";
    }


    @Secured("ROLE_ADMIN")
    @GetMapping("users/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") Long id) {
        userService.delete(id);
        return "redirect:/allUsers";
    }
}
