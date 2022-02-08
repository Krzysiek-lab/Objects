package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@Table(name = "zdarzenia")
@Entity
@Builder
@NoArgsConstructor
@Data
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
    public Date startDate;

    @Column
    public Date endDate;

}