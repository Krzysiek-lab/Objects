package ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Enums.Subject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherViewModel {
    private Long id;

    @Length(min = 2, message = "Name has to be at least 2 characters long")
    private String name;

    private String lastName;

    @Email(message = "email has to be valid")
    @NotBlank
    private String email;

    private Subject subject;

    @Min(value = 19, message = "Age has to be minimum 19")
    @Max(100)
    private int age;

    private List<Long> students_ids = new ArrayList<>();
}
