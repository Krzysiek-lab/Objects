package ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class TeacherForStudentViewModel {
    private Long id;

    private String name;

    private Boolean assignedToCurrentStudent;
}
