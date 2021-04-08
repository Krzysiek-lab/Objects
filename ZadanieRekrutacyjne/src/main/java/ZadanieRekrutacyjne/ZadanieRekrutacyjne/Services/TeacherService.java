package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Services;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entities.Teacher;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Repositories.StudentRepository;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Repositories.TeacherRepository;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels.TeacherViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public Teacher Add(TeacherViewModel teacherViewModel) {
        var students = studentRepository.findAllById(teacherViewModel.getStudents_ids());

        var newTeacher = Teacher.builder()
                .name(teacherViewModel.getName())
                .lastName(teacherViewModel.getLastName())
                .age(teacherViewModel.getAge())
                .email(teacherViewModel.getEmail())
                .subject(teacherViewModel.getSubject())
                .students(students)
                .build();

        return teacherRepository.save(newTeacher);
    }

    public Teacher Get(Long teacherId) {
        return teacherRepository.getOne(teacherId);
    }

    public List<Teacher> GetAll() {
        return teacherRepository.findAll();
    }

    public List<Teacher> GetPage(int page, int pageSize, String columnName) {
        return teacherRepository
                .findAll(PageRequest.of(page, pageSize, Sort.by(columnName)))
                .getContent();
    }

    public List<Teacher> GetByName(String name) {
        var byName = teacherRepository.GetByName(name);
        var byLastName = teacherRepository.GetByLastName(name);

        var allWithDuplicates = new ArrayList<>(byName);
        allWithDuplicates.addAll(byLastName);

        return new ArrayList<>(new HashSet<>(allWithDuplicates));
    }

    public List<Teacher> GetForStudent(Long studentId) {
        return studentRepository.getOne(studentId).getTeachers();
    }

    public Teacher Update(TeacherViewModel teacherViewModel) {
        var teacher = teacherRepository.getOne(teacherViewModel.getId());
        var students = studentRepository.findAllById(teacherViewModel.getStudents_ids());

        teacher.setName(teacherViewModel.getName());
        teacher.setLastName(teacherViewModel.getLastName());
        teacher.setAge(teacherViewModel.getAge());
        teacher.setEmail(teacherViewModel.getEmail());
        teacher.setSubject(teacherViewModel.getSubject());
        teacher.setStudents(students);

        return teacherRepository.save(teacher);
    }

    public void Delete(Long teacherId) {
        var teacher = teacherRepository.getOne(teacherId);

        teacherRepository.delete(teacher);
    }
}
