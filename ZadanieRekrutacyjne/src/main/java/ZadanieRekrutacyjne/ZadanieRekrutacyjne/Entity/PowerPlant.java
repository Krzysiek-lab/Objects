package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entity;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@AllArgsConstructor
@Entity
@Table(name = "elektrownie")
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class PowerPlant {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    public Integer id;

    @Column
    public String name;

    @Column
    public Double power;

    @Column
    public String place;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PowerPlant that = (PowerPlant) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
