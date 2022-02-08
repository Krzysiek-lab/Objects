package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@Entity
@Table(name = "elektrownie")
@NoArgsConstructor
@Builder
@Data
public class PowerPlant {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    public Integer id;

    @Column
    public String name;

    @Column
    public Double power;

    @OneToMany(mappedBy = "powerPlant")
    private List<Event> events;


}
