package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Interfaces;

import java.sql.Timestamp;
import java.util.Map;

public interface powerForPowerPlant {

    Map<Integer, String> powerForPowerPlantPerDay(Timestamp date);
}
