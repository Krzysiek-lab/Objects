package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Controllers;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entities.Teacher;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Services.TeacherService;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels.TeacherViewModel;
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
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping("getAll")
    public ResponseEntity<List<TeacherViewModel>> getAllTeachers() {
        return ResponseEntity.ok(GetTeacherViewModels(teacherService.GetAll()));
    }

    @PostMapping("add")
    public ResponseEntity<TeacherViewModel> addTeacher(@ModelAttribute("newTeacher") @Valid TeacherViewModel teacherViewModel, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            return ResponseEntity.ok(GetTeacherViewModel(teacherService.Add(teacherViewModel)));
        }

        return ResponseEntity.badRequest().body(teacherViewModel);
    }

    @PutMapping("update")
    public ResponseEntity<TeacherViewModel> updateTeacher(@ModelAttribute("updatedTeach") @Valid TeacherViewModel teacherViewModel, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            return ResponseEntity.ok(GetTeacherViewModel(teacherService.Update(teacherViewModel)));
        }

        return ResponseEntity.badRequest().body(teacherViewModel);
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteTeacher(@RequestParam() Long id) {
        teacherService.Delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("getPageable")
    public ResponseEntity<List<TeacherViewModel>> getPageable(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "0") int pageSize,
            @RequestParam(defaultValue = "name") String columnName) {
        return ResponseEntity.ok(GetTeacherViewModels(teacherService.GetPage(page, pageSize, columnName)));
    }

    @GetMapping("get")
    public ResponseEntity<TeacherViewModel> getTeacher(@RequestParam() Long id) {
        return ResponseEntity.ok(GetTeacherViewModel(teacherService.Get(id)));
    }

    @GetMapping("getByName")
    public ResponseEntity<List<TeacherViewModel>> getTeacherByName(@RequestParam() String name) {
        return ResponseEntity.ok(GetTeacherViewModels(teacherService.GetByName(name)));
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
