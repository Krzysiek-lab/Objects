package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Services;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entity.PowerPlant;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@AllArgsConstructor
public class Queries {

    EntityManager entityManager;


    public List<PowerPlant> getDuplicatesPower() {
        var list = entityManager.createQuery("select p from PowerPlant p where exists" +
                " (select p from PowerPlant p GROUP BY p.power having count(p.power) >=2)", PowerPlant.class)
                .getResultList();
        entityManager.close();
        return list;
    }

    public List<PowerPlant> getDuplicatesPlace() {
        var list = entityManager.createQuery("select p from PowerPlant p where exists " +
                "(select p from PowerPlant p GROUP BY p.place having count(p.place) >=2)", PowerPlant.class)
                .getResultList();
        entityManager.close();
        return list;
    }


    public List<PowerPlant> getDuplicatesName() {
        var list = entityManager.createQuery("select p from PowerPlant p where exists " +
                "(select p from PowerPlant p GROUP BY p.name having count(p.name) >=2)", PowerPlant.class)
                .getResultList();
        entityManager.close();
        return list;
    }
}

