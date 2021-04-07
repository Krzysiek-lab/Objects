package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Controllers;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entities.Teacher;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Enums.Subject;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Services.TeacherService;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels.TeacherViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping("teachers/getAll")
    @ResponseBody
    public List<TeacherViewModel> GetTeachers() {
        var teachers = teacherService.GetAll();
        var teacherViewModels = new ArrayList<TeacherViewModel>();

        for (Teacher teacher : teachers) {
            teacherViewModels.add(TeacherViewModel.builder()
                    .id(teacher.getId())
                    .name(teacher.getName())
                    .lastName(teacher.getLastName())
                    .age(teacher.getAge())
                    .email(teacher.getEmail())
                    .subject(teacher.getSubject())
                    .students_ids(teacher.getStudents().stream().map(s -> s.getId()).collect(Collectors.toList()))
                    .build());
        }

        return teacherViewModels;
    }
}
