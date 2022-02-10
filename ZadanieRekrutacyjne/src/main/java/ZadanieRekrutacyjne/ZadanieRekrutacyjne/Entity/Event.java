package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@AllArgsConstructor
@Table(name = "zdarzenia")
@Entity
@Builder
@NoArgsConstructor
@Getter
@Setter

public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    public Integer id;

    @ManyToOne
    @JoinColumn(name = "id_elektrowni", referencedColumnName = "id")
    private PowerPlant powerPlant;

    @Column
    public String typeOfEvent;

    @Column
    public Double powerDrop;

    @Column
    public LocalDate startDate;

    @Column
    public LocalDate endDate;


    @Override
    public String toString() {
        return "" + typeOfEvent + "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Event event = (Event) o;
        return id != null && Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}