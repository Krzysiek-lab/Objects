package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Controllers;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entities.Teacher;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Services.TeacherService;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels.StudentViewModel;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels.TeacherViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@RequestMapping("teachers")
public class TeacherController {

    private final TeacherService teacherService;


    @GetMapping("")
    public String viewHomePage(Model model) {
        model.addAttribute("teachers", GetTeacherViewModels(teacherService.GetPage(0, 10, "id")));

        return "teachers";
    }

    @GetMapping("newTeacherForm")
    public String newTeacher(Model model) {
        var newTeacher = new TeacherViewModel();
        model.addAttribute("teacher", newTeacher);

        return "new_teacher";
    }

    @PostMapping("saveTeacher")
    public String saveTeacher(@ModelAttribute("teacher") @Valid TeacherViewModel teacher, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            if (teacher.getId() == null) {
                return "new_teacher";
            } else {
                return "update_teacher";
            }
        }

        if (teacher.getId() == null) {
            teacherService.Add(teacher);
        } else {
            teacherService.Update(teacher);
        }

        return "redirect:/";
    }

    @GetMapping("updateTeacherForm/{id}")
    public String updateTeacher(@PathVariable(value = "id") Long id, Model model) {
        var teacher = GetTeacherViewModel(teacherService.Get(id));
        model.addAttribute("teacher", teacher);

        return "update_teacher";
    }

    @GetMapping("deleteTeacher/{id}")
    public String deleteTeacher(@PathVariable(value = "id") Long id) {
        teacherService.Delete(id);

        return "redirect:/";
    }

    @GetMapping("getForStudent")
    public String getForStudent(@RequestParam(value = "studentId") Long studentId, Model model) {
        model.addAttribute("teachers", GetTeacherViewModels(teacherService.GetForStudent(studentId)));

        return "teachers";
    }

    private List<TeacherViewModel> GetTeacherViewModels(List<Teacher> teachers) {
        var teacherViewModels = new ArrayList<TeacherViewModel>();

        for (var teacher : teachers) {
            teacherViewModels.add(GetTeacherViewModel(teacher));
        }

        return teacherViewModels;
    }

    private TeacherViewModel GetTeacherViewModel(Teacher teacher) {
        return TeacherViewModel.builder()
                .id(teacher.getId())
                .name(teacher.getName())
                .lastName(teacher.getLastName())
                .age(teacher.getAge())
                .email(teacher.getEmail())
                .subject(teacher.getSubject())
                .students_ids(teacher.getStudents().stream().map(s -> s.getId()).collect(Collectors.toList()))
                .build();
    }
}
