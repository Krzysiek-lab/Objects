package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entities;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Enums.Subject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private Subject subject;

    @Column
    private int age;

    @Column(nullable = true)
    @ManyToMany
    @JoinTable(
            name = "teacher_student",
            joinColumns = {@JoinColumn(name = "TEACHER_ID", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "STUDENT_ID", referencedColumnName = "id")})
    private List<Student> students;
}
