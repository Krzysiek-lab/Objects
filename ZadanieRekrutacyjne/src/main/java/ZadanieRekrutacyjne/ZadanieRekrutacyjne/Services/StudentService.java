package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Services;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entities.Student;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entities.Teacher;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Repositories.StudentRepository;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Repositories.TeacherRepository;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels.StudentViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public Student Add(StudentViewModel studentViewModel) {
        // lista id teacherów danego studenta
        var teachers = teacherRepository.findAllById(studentViewModel.getTeachers_ids());

        // metoda tworząca z podanych z formularza zwalidowanych daneych obiekt Teacher i zapisująca go do bazy danych
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
        // metoda znajdujaca studenta o danym id
        return studentRepository.getOne(studentId);
    }

    public List<Student> GetAll() {
        // metoda zwracająca wszytkich Studentów
        return studentRepository.findAll();
    }

    public Page<Student> GetPage(int page, int pageSize, String columnName, boolean asc) {
        //metoda zwracajaca wszystkich Studentów na stronie o podanych parametrach, gdzie kierunek ortowania zależny jest
        // od podanej jako parametr metody wartości boolean asc, wartosc ta jest w management.js domyslnie (tam sortStudentAscending)
        // true ale po wejsciu na strone uruchamia sie funkcja ktora po kliknięciu w nazwę danej kolumny zmienia wartosc sortStudentAscending
        // i zapisuje nazwe kliknietej kolumny do zmiennej selectedStudentColumn by po jej drugim kliknieciu metoda znow
        // zmienila wartosc zmiennej sortStudentAscending
        // metoda ta (GetPage) wywolywana jest w Controlerze viewHomePage gdzie podawane są wartości page czyli numer strony
        // pageSize czyli dlugosc strony, column czyli nazwa kolumny po ktorej ma byc sortowanie i sortAscending decydującej kierunek sortowania
        // po zwroceniu znalezionych wartosci metoda z repozytorium findAll zapisana  jest do zmiennej students zamieniona na viewModel
        // i zwracana do templatkic students do obiektu students i wylistowana
        // wartosc wszyskich stron jest zapisana do zmiennej totlPages przekazanej do templatki student jako atrybut totalPages
        // gdzie za pomoca funkcji jquerowej $if sprawdzane jest czy ilosc stron jest wieksza niz 0 a kesli tak to wyswietlamy nowy przycisk z numerem strony
        var sortable = asc ? Sort.by(columnName).ascending() : Sort.by(columnName).descending();
        return studentRepository.findAll(PageRequest.of(page, pageSize, sortable));
    }

    public List<Student> GetByName(String name) {
        // metoda zwracająca Studenta o danym imieniu lub nazwisku, potem zamieniam die listy na jedna z niepowatarzającymi
        // sie wartosciami
        var byName = studentRepository.GetByName(name);
        var byLastName = studentRepository.GetByLastName(name);

        var allWithDuplicates = new ArrayList<>(byName);
        allWithDuplicates.addAll(byLastName);

        return new ArrayList<>(new HashSet<>(allWithDuplicates));
    }

    public List<Teacher> GetForStudent(Long studentId) {
        // metoda zwracająca liste Teacher'ów dla danego studenta
        return studentRepository.getOne(studentId).getTeachers();
    }

    public Student Update(StudentViewModel studentViewModel) {
        // metoda zmieniająca podanego viewModel'a na Studenta i zapisujaca go do repozytorium
        var student = studentRepository.getOne(studentViewModel.getId());

        student.setName(studentViewModel.getName());
        student.setLastName(studentViewModel.getLastName());
        student.setAge(studentViewModel.getAge());
        student.setEmail(studentViewModel.getEmail());
        student.setField(studentViewModel.getField());

        return studentRepository.save(student);
    }

    public void Delete(Long studentId) {
        // metoda usuwajaca studenta o podanym id
        var student = studentRepository.getOne(studentId);

        studentRepository.delete(student);
    }
}
