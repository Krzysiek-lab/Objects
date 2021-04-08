package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Controllers;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entities.Student;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Services.StudentService;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels.StudentViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    @GetMapping("getAll")
    public ResponseEntity<List<StudentViewModel>> getAllStudents() {
        return ResponseEntity.ok(GetStudentViewModels(studentService.GetAll()));
    }

    @PostMapping("add")
    public ResponseEntity<StudentViewModel> addStudent(@ModelAttribute("newStudent") @Valid StudentViewModel studentViewModel, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            return ResponseEntity.ok(GetStudentViewModel(studentService.Add(studentViewModel)));
        }

        return ResponseEntity.badRequest().body(studentViewModel);
    }

    @PutMapping("update")
    public ResponseEntity<StudentViewModel> updateStudent(@ModelAttribute("updatedTeach") @Valid StudentViewModel studentViewModel, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            return ResponseEntity.ok(GetStudentViewModel(studentService.Update(studentViewModel)));
        }

        return ResponseEntity.badRequest().body(studentViewModel);
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteStudent(@RequestParam() Long id) {
        studentService.Delete(id);

        return ResponseEntity.noContent().build();
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

