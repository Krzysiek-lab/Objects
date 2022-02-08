package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Interfaces;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entity.Event;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels.EventViewModel;

public interface EventServiceToViewModel {
    EventViewModel eventToViewModel(Event event);
}
