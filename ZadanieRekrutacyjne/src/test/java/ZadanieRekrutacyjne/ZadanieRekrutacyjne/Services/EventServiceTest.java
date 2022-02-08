package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Services;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventServiceTest {


    @Autowired
    EventService eventService;


    @ParameterizedTest
    @CsvSource({"1,0", "8,1", "2,1"})
    void shouldCheckIfGivenIdHasRequiredEvent(int id_input, String ex) {

        var value = eventService.NumberOfFailureEventsForId(id_input);

        assertEquals(value, Long.valueOf(ex));
    }


//    @Test
//    void shouldReturneventMapWithKeyAndValueAsIdAndEventFromGivenDdate() {
//
//        Timestamp inst = Timestamp.valueOf(LocalDateTime.now());
//
//        Map<Integer, String> map = numberOfFailureEvents.powerForPowerPlantPerDay(inst);
//
//        assertNotNull(map);
//    }
}