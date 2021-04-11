package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Controllers;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entities.Student;
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
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping("getAll")
    public String getAllTeachers(Model model) {
        model.addAttribute("teachers", GetTeacherViewModels(teacherService.GetAll()));

        return "allStudents";
    }


    ///////////////////
//    @PostMapping("add")
//    public ResponseEntity<TeacherViewModel> addTeacher(@ModelAttribute("newTeacher") @Valid TeacherViewModel teacherViewModel, BindingResult bindingResult) {
//        if (!bindingResult.hasErrors()) {
//            return ResponseEntity.ok(GetTeacherViewModel(teacherService.Add(teacherViewModel)));
//        }
//
//        return ResponseEntity.badRequest().body(teacherViewModel);
//    }
    ////////////////
    @PostMapping("addOrUpdate")
    public String addOrUpdateTeacher(@ModelAttribute("newTeacher") @Valid TeacherViewModel teacherViewModel, BindingResult bindingResult) throws Exception {
        if (!bindingResult.hasErrors()) {
            if (teacherViewModel.getId() != null) {
                teacherService.Update(teacherViewModel);
            } else {
                teacherService.Add(teacherViewModel);
            }

            return "redirect:/teachers/getAll";
        }

        return "addOrUpdateTeacher";
    }
    //////////////////

//    @PutMapping("update")
//    public ResponseEntity<TeacherViewModel> updateTeacher(@ModelAttribute("updatedTeach") @Valid TeacherViewModel teacherViewModel, BindingResult bindingResult) {
//        if (!bindingResult.hasErrors()) {
//            return ResponseEntity.ok(GetTeacherViewModel(teacherService.Update(teacherViewModel)));
//        }
//
//        return ResponseEntity.badRequest().body(teacherViewModel);
//    }



    ////////////////////////////
    @GetMapping("getStudentsOfTeacher/{id}")
    public String getStudentsOfTeacher(@PathVariable Long id, Model model) {
        var teachersOfStudent = teacherService.GetForTeacher(id).stream().map(this::getGetNameAndLastNameOfStudent).collect(Collectors.toList());

        model.addAttribute("studentsOfTeacher", teachersOfStudent);

        return "studentsOfTeacher";
    }

    private String getGetNameAndLastNameOfStudent(Student t) {
        return t.getName() + " " + t.getLastName();
    }
    //////////////////////
//    @DeleteMapping("delete")
//    public ResponseEntity<String> deleteTeacher(@RequestParam() Long id) {
//        teacherService.Delete(id);
//
//        return ResponseEntity.noContent().build();
//    }

    ///////////////////////

    @GetMapping("delete/{id}")
    public String deleteTeacher(@PathVariable Long id) {
        teacherService.Delete(id);

        return "redirect:/teachers/getAll";
    }
    //////////////////////



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


//    @GetMapping("getForTeacher")
//    public ResponseEntity<List<TeacherViewModel>> getForTeacher(@ModelAttribute("id") Long teacherId) {
//        return ResponseEntity.ok(GetTeacherViewModels(teacherService.GetForTeacher(teacherId)));
//    }

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
