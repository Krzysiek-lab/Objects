package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Controllers;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entities.Student;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entities.Teacher;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Services.StudentService;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels.StudentViewModel;
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

@Controller
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping(value = "/")
    public String viewHomePage(@RequestParam(value = "column") Optional<String> column, Model model) {
        model.addAttribute("students", GetStudentViewModels(studentService.GetPage(0, 10, column.orElse("id"))));

        return "index";
    }

    @GetMapping("newStudentForm")
    public String newStudent(Model model) {
        var newStudent = new StudentViewModel();
        model.addAttribute("student", newStudent);

        return "new_student";
    }

    @PostMapping("saveStudent")
    public String saveStudent(@ModelAttribute("student") @Valid StudentViewModel student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            if (student.getId() == null) {
                return "new_student";
            } else {
                return "update_student";
            }
        }

        if (student.getId() == null) {
            studentService.Add(student);
        } else {
            studentService.Update(student);
        }

        return "redirect:/";
    }

    @GetMapping("updateStudentForm/{id}")
    public String updateStudent(@PathVariable(value = "id") Long id, Model model) {
        var student = GetStudentViewModel(studentService.Get(id));
        model.addAttribute("student", student);

        return "update_student";
    }

    @GetMapping("deleteStudent/{id}")
    public String deleteStudent(@PathVariable(value = "id") Long id) {
        studentService.Delete(id);

        return "redirect:/";
    }


    @GetMapping("get/{id}")
    public String getStudent(@PathVariable Long id) {
        GetStudentViewModel(studentService.Get(id));
        return "get_student";
    }


    private List<StudentViewModel> GetStudentViewModels(List<Student> students) {
        var studentViewModels = new ArrayList<StudentViewModel>();

        for (var student : students) {
            studentViewModels.add(GetStudentViewModel(student));
        }

        return studentViewModels;
    }

    private StudentViewModel GetStudentViewModel(Student student) {
        return StudentViewModel.builder()
                .id(student.getId())
                .name(student.getName())
                .lastName(student.getLastName())
                .age(student.getAge())
                .email(student.getEmail())
                .field(student.getField())
                .teachers_ids(student.getTeachers().stream().map(s -> s.getId()).collect(Collectors.toList()))
                .build();
    }
}

