package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entity;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Role")
public class Role {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "typ_roli")
    @Enumerated(EnumType.STRING)
    private RoleType type;


    @Override
    public String toString() {
        return "" + type ;
    }
}