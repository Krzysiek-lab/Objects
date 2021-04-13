package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Controllers;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entities.Student;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entities.Teacher;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Services.StudentService;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Services.TeacherService;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels.StudentForTeacherViewModel;
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
    private final StudentService studentService;

    @GetMapping(value = "")
    public String viewHomePage(
            @RequestParam(value = "page") Optional<Integer> page,
            @RequestParam(value = "pageSize") Optional<Integer> pageSize,
            @RequestParam(value = "column") Optional<String> column,
            @RequestParam(value = "sortAscending") Optional<Boolean> sortAscending,
            Model model) {
        var currentPage = page.orElse(1);
        var currentPageSize = pageSize.orElse(5);
        var currentSortColumn = column.orElse("id");
        var currentDirection = sortAscending.orElse(true);

        var teachers = teacherService.GetPage(currentPage - 1, currentPageSize, currentSortColumn, currentDirection);
        model.addAttribute("teachers", GetTeacherViewModels(teachers.getContent()));

        int totalPages = teachers.getTotalPages();
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", currentPageSize);
        model.addAttribute("currentPageNumber", currentPage);

        if (totalPages > 0) {
            var pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "teachers";
    }

    @GetMapping("newTeacherForm")
    public String newTeacher(Model model) {
        var newTeacher = new TeacherViewModel();
        model.addAttribute("teacher", newTeacher);

        return "new_teacher";
    }

    @GetMapping("getFiltered")
    public String getFiltered(@RequestParam(value = "filterValue") String filterValue, Model model) {
        model.addAttribute("teachers", GetTeacherViewModels(teacherService.GetByName(filterValue)));

        return "teachers";
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
        var teacher = teacherService.Get(id);
        var teacherViewModel = GetTeacherViewModel(teacher);
        var teacherStudents = teacher.getStudents();
        var students = GetStudentIdsViewModels(studentService.GetAll(), teacherStudents);

        model.addAttribute("teacher", teacherViewModel);
        model.addAttribute("students", students);

        return "update_teacher";
    }

    @GetMapping("delete")
    public ResponseEntity<String> deleteTeacher(@RequestParam(value = "id") Long id) {
        teacherService.Delete(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("getForStudent")
    public String getForStudent(@RequestParam(value = "studentId") Long studentId, Model model){
        var studentTeachers = GetTeacherViewModels(studentService.GetForStudent(studentId));

        model.addAttribute("teachers", studentTeachers);

        return "teachers";
    }

    @GetMapping("addStudentToTeacher")
    public String addStudentToTeacher(@RequestParam(value = "teacherId") Long teacherId, @RequestParam(value = "studentId") Long studentId, Model model) {
        var student = studentService.Get(studentId);
        var teacher = teacherService.Get(teacherId);

        var teacherStudents = teacher.getStudents();
        teacherStudents.add(student);
        teacher.setStudents(teacherStudents);

        teacherService.Update(GetTeacherViewModel(teacher));

        var students = GetStudentIdsViewModels(studentService.GetAll(), teacher.getStudents());
        model.addAttribute("students", students);

        return "teacher_students";
    }

    @GetMapping("removeStudentOfTeacher")
    public String removeStudentOfTeacher(@RequestParam(value = "teacherId") Long teacherId, @RequestParam(value = "studentId") Long studentId, Model model) {
        var student = studentService.Get(studentId);
        var teacher = teacherService.Get(teacherId);

        var teacherStudents = teacher.getStudents();
        teacherStudents.remove(student);
        teacher.setStudents(teacherStudents);

        teacherService.Update(GetTeacherViewModel(teacher));

        var students = GetStudentIdsViewModels(studentService.GetAll(), teacher.getStudents());
        model.addAttribute("students", students);

        return "teacher_students";
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

    private List<StudentForTeacherViewModel> GetStudentIdsViewModels(List<Student> students, List<Student> teacherStudents) {
        var studentViewModels = new ArrayList<StudentForTeacherViewModel>();

        for (var student : students) {
            studentViewModels.add(GetStudentIdsViewModel(student, teacherStudents));
        }

        return studentViewModels;
    }

    private StudentForTeacherViewModel GetStudentIdsViewModel(Student student, List<Student> teacherStudents) {
        return StudentForTeacherViewModel.builder()
                .id(student.getId())
                .name(student.getName() + " " + student.getLastName())
                .assignedToCurrentTeacher(teacherStudents.contains(student))
                .build();
    }
}
