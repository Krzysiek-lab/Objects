package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Services;

import org.springframework.beans.factory.annotation.Autowired;

class EventServiceTest {


    @Autowired
    EventService eventService;

    @Autowired
    PlantService plantService;

//    @ParameterizedTest
//    @CsvSource({"1,0", "8,1", "2,1"})
//    void shouldCheckIfGivenIdHasRequiredEvent(int id_input, String ex) {
//
//        var value = eventService.NumberOfFailureEventsForId(id_input);
//
//        assertEquals(value, Long.valueOf(ex));
//    }

}