package ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entity.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PlantViewModel {

    Integer id;

    @NotEmpty(message = "name can not be empty")
    String name;

    @NotNull(message = "power can not be empty")
    @DecimalMax("10000.0") @DecimalMin("0.0")
    Double power;


    List<Event> listOfEventsForPlant = new ArrayList<>();
}
