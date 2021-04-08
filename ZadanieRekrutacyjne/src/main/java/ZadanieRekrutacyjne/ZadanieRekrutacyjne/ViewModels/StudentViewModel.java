package ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Enums.Field;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentViewModel {
    private Long id;

    @Length(min = 2)
    private String name;

    private String lastName;

    @Email
    private String email;

    private Field field;

    @Min(19)
    private int age;

    private List<Long> teachers_ids = new ArrayList<>();
}
