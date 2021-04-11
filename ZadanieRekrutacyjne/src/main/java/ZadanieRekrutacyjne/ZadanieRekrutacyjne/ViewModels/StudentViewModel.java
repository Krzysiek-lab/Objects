package ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Enums.Field;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentViewModel {
    private Long id;

    @Length(min=2)
    @NotBlank
    private String name;

    private String lastName;

    @Email(message = "email has to be valid")
    @NotBlank
    private String email;

    private Field field;

    @Min(19)
    @Max(100)
    private int age;

    private List<Long> teachers_ids = new ArrayList<>();
}
