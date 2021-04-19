package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Controllers;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entities.Student;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entities.Teacher;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Services.StudentService;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Services.TeacherService;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels.StudentViewModel;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels.TeacherForStudentViewModel;
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
@RequestMapping("students")
public class StudentController {
    private final StudentService studentService;
    private final TeacherService teacherService;

    @GetMapping(value = "")
    public String viewHomePage(
            @RequestParam(value = "page") Optional<Integer> page,
            @RequestParam(value = "pageSize") Optional<Integer> pageSize,
            @RequestParam(value = "column") Optional<String> column,
            @RequestParam(value = "sortAscending") Optional<Boolean> sortAscending,
            Model model) {
        // jest Optional bo mozna nie podawac tych wartosci ale one i tak sa podane z management.js ale nie wszystkiie
        var currentPage = page.orElse(1);
        var currentPageSize = pageSize.orElse(5);
        var currentSortColumn = column.orElse("id");// z management.js linijka 2
        var currentDirection = sortAscending.orElse(true);

        var students = studentService.GetPage(currentPage - 1, currentPageSize, currentSortColumn, currentDirection);
        model.addAttribute("students", GetStudentViewModels(students.getContent()));

        int totalPages = students.getTotalPages();
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", currentPageSize);
        model.addAttribute("currentPageNumber", currentPage);

        if (totalPages > 0) {
            // jesli ilosc stron kest wieksza od 0 to wez wartosc stron od 1 do totalPages zamien je na listę
            // i dodaj do templatki students gdzie wartos strony jest wylistowana na przycisku i po jego nacisnieciu wywoluje sie metoda
            // getPaginatedPageForStudents z studentManagment zwracająca do div'a student.container dane z danej strony
            var pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "students";
    }

    @GetMapping("newStudentForm")
    public String newStudent(Model model) {
        // trzeba stworzyc z getMappingiem bo innaczej nie pokaze strony z formularzem dodawania nowego studenya
        var newStudent = new StudentViewModel();
        model.addAttribute("student", newStudent);

        return "new_student";
    }

    @GetMapping("getFiltered")
    public String getFiltered(@RequestParam(value = "filterValue") String filterValue, Model model) {
        // metoda pobierjaca wartosc dla filtervalue z funkcji getFilteredForStudents z studentMagement,js
        // ktora zamienia cialo students-container na wyszukanego teachera
        model.addAttribute("students", GetStudentViewModels(studentService.GetByName(filterValue)));

        return "students";
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
        var student = studentService.Get(id);// zwracam studenta
        var studentViewModel = GetStudentViewModel(student);// zamianiam go na viewModel
        var studentTeachers = student.getTeachers();// zwracam teacherow tego studenta
        var teachers = GetTeacherIdsViewModels(teacherService.GetAll(), studentTeachers);
        // budowanie teacheridsviewModels i zwracanie do templatki do obiektu teachers gdzie sprawdzam
        // czy wartos assigned jest true czy nie i w zaleznosi wywoluke ze studentManagement odpowiedzia metode
        // jesli metoda GetTeacherIdsViewModels sprawdzajca czy dany teacher jest przypisany do studenta i zamienijaca go na viewModel
        // jesli jej wynikiem jest ze teaxher jest nie przypisany to po przekazaniu do templatki tego obiektu wykona sie if
        model.addAttribute("student", studentViewModel);// oddaje do obejktu z templatki studentViewModela z danego Student
        model.addAttribute("teachers", teachers);

        return "update_student";
    }

    @GetMapping("delete")
    public ResponseEntity<String> deleteStudent(@RequestParam(value = "id") Long id) {
        // usuwanie studenta
        studentService.Delete(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("getForTeacher")
    public String getForStudent(@RequestParam(value = "teacherId") Long teacherId, Model model) {
        var teacherStudents = GetStudentViewModels(teacherService.GetForTeacher(teacherId));

        model.addAttribute("students", teacherStudents);

        return "students";
    }

    @GetMapping("addTeacherToStudent")
    public String addTeacherToStudent(@RequestParam(value = "teacherId") Long teacherId, @RequestParam(value = "studentId") Long studentId, Model model) {
        var student = studentService.Get(studentId);
        var teacher = teacherService.Get(teacherId);

        var studentTeachers = student.getTeachers();
        studentTeachers.add(teacher);
        student.setTeachers(studentTeachers);

        studentService.Update(GetStudentViewModel(student));

        var teachers = GetTeacherIdsViewModels(teacherService.GetAll(), student.getTeachers());
        model.addAttribute("teachers", teachers);

        return "student_teachers";
    }

    @GetMapping("removeTeacherOfStudent")
    public String removeTeacherOfStudent(@RequestParam(value = "teacherId") Long teacherId, @RequestParam(value = "studentId") Long studentId, Model model) {
        var student = studentService.Get(studentId);
        var teacher = teacherService.Get(teacherId);

        var studentTeachers = student.getTeachers();
        studentTeachers.remove(teacher);
        student.setTeachers(studentTeachers);

        studentService.Update(GetStudentViewModel(student));

        var teachers = GetTeacherIdsViewModels(teacherService.GetAll(), student.getTeachers());

        model.addAttribute("teachers", teachers);

        return "student_teachers";
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

    private List<TeacherForStudentViewModel> GetTeacherIdsViewModels(List<Teacher> teachers, List<Teacher> studentTeachers) {
        var teacherViewModels = new ArrayList<TeacherForStudentViewModel>();

        for (var teacher : teachers) {
            teacherViewModels.add(GetTeacherIdsViewModel(teacher, studentTeachers));
        }

        return teacherViewModels;
    }

    private TeacherForStudentViewModel GetTeacherIdsViewModel(Teacher teacher, List<Teacher> studentTeachers) {
        return TeacherForStudentViewModel.builder()
                .id(teacher.getId())
                .name(teacher.getName() + " " + teacher.getLastName())
                .assignedToCurrentStudent(studentTeachers.contains(teacher))
                .build();
    }
}

