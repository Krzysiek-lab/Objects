package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ManagementController {
    @GetMapping(value = "/")
    public String viewHomePage(){
        return "index";
    }
}

