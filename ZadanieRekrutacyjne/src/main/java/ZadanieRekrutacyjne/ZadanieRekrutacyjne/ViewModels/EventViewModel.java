package ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entity.PowerPlant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EventViewModel {

    Integer id;

    @NotEmpty(message = "name can not be empty")
    String plantName;

    @NotEmpty(message = "power can not be empty")
    String typeOfEvent;

    @NotNull(message = "power can not be empty")
    @DecimalMax("10000.0") @DecimalMin("0.0")
    Double powerDrop;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    Date startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    Date endDate;

    PowerPlant PlantsForEvent;
}
