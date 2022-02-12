package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Interfaces;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Enums.EventType;

import java.time.LocalDate;
import java.util.Map;

public interface powerForPowerPlant {

    Map<Integer, EventType> powerForPowerPlantPerDay(LocalDate date);
}
