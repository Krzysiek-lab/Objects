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
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    @GetMapping("getAll")
    public String getAllStudents(Model model) {
        model.addAttribute("students", GetStudentViewModels(studentService.GetAll()));

        return "allStudents";
    }

    @GetMapping("addOrUpdate")
    public String addEmpty(Model model, @RequestParam(value = "id") Optional<Long> id) {
        var student = id.isPresent() ? studentService.Get(id.get()) : new Student();
        model.addAttribute("newStudent", student);

        return "addOrUpdateStudent";
    }

    @PostMapping("addOrUpdate")
    public String addOrUpdateStudent(@ModelAttribute("newStudent") @Valid StudentViewModel studentViewModel, BindingResult bindingResult) throws Exception {
        if (!bindingResult.hasErrors()) {
            if (studentViewModel.getId() != null) {
                studentService.Update(studentViewModel);
            } else {
                studentService.Add(studentViewModel);
            }

            return "redirect:/students/getAll";
        }

        return "addOrUpdateStudent";
    }

//    @PutMapping("update")
//    public ResponseEntity<StudentViewModel> updateStudent(@ModelAttribute("updatedTeach") @Valid StudentViewModel studentViewModel, BindingResult bindingResult) {
//        if (!bindingResult.hasErrors()) {
//            return ResponseEntity.ok(GetStudentViewModel(studentService.Update(studentViewModel)));
//        }
//
//        return ResponseEntity.badRequest().body(studentViewModel);
//    }

    @GetMapping("getTeachersOfStudent/{id}")
    public String getTeachersOfStudent(@PathVariable Long id, Model model) {
        var teachersOfStudent = studentService.GetForStudent(id).stream().map(this::getGetNameAndLastNameOfTeacher).collect(Collectors.toList());

        model.addAttribute("teachersOfStudents", teachersOfStudent);

        return "teachersOfStudent";
    }

    private String getGetNameAndLastNameOfTeacher(Teacher t) {
        return t.getName() + " " + t.getLastName();
    }

    @GetMapping("delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.Delete(id);

        return "redirect:/students/getAll";
    }

    @GetMapping("getPageable")
    public ResponseEntity<List<StudentViewModel>> getPageable(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "0") int pageSize,
            @RequestParam(defaultValue = "name") String columnName) {
        return ResponseEntity.ok(GetStudentViewModels(studentService.GetPage(page, pageSize, columnName)));
    }

    @GetMapping("get")
    public ResponseEntity<StudentViewModel> getStudent(@RequestParam() Long id) {
        return ResponseEntity.ok(GetStudentViewModel(studentService.Get(id)));
    }

    @GetMapping("getByName")
    public ResponseEntity<List<StudentViewModel>> getStudentByName(@RequestParam() String name) {
        return ResponseEntity.ok(GetStudentViewModels(studentService.GetByName(name)));
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

