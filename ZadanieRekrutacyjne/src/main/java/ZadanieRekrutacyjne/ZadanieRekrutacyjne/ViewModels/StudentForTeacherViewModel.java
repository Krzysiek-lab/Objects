package ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentForTeacherViewModel {
    private Long id;

    private String name;

    private Boolean assignedToCurrentTeacher;
}
