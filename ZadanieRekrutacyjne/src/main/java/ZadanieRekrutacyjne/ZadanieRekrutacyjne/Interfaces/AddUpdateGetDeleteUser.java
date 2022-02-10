package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Interfaces;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels.UserViewModel;

public interface AddUpdateGetDeleteUser {
    void add(UserViewModel userViewModel);

    void update(UserViewModel userDto);

    void delete(Long userId);
}
