package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Services;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entities.Student;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Repositories.StudentRepository;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Repositories.TeacherRepository;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels.StudentViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public Student Add(StudentViewModel studentViewModel) {
        var teachers = teacherRepository.findAllById(studentViewModel.getTeachers_ids());

        var newStudent = Student.builder()
                .name(studentViewModel.getName())
                .lastName(studentViewModel.getLastName())
                .age(studentViewModel.getAge())
                .email(studentViewModel.getEmail())
                .field(studentViewModel.getField())
                .teachers(teachers)
                .build();

        return studentRepository.save(newStudent);
    }

    public Student Get(Long studentId) {
        return studentRepository.getOne(studentId);
    }

    public List<Student> GetAll() {
        return studentRepository.findAll();
    }

    public Student Update(StudentViewModel studentViewModel) {
        var student = studentRepository.getOne(studentViewModel.getId());
        var teachers = teacherRepository.findAllById(studentViewModel.getTeachers_ids());

        student.setName(studentViewModel.getName());
        student.setLastName(studentViewModel.getLastName());
        student.setAge(studentViewModel.getAge());
        student.setEmail(studentViewModel.getEmail());
        student.setField(studentViewModel.getField());
        student.setTeachers(teachers);

        return studentRepository.save(student);
    }

    public void Delete(Long studentId) {
        var Student = studentRepository.getOne(studentId);

        studentRepository.delete(Student);
    }
}
