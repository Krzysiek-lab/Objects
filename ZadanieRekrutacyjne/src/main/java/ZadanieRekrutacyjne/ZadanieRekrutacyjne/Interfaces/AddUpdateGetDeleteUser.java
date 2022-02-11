package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Interfaces;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entity.User;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels.UserViewModel;

import java.util.List;

public interface AddUpdateGetDeleteUser {
    void add(UserViewModel userViewModel);

    void update(UserViewModel userDto);

    void delete(Long userId);

    User getCurrentUser();

    User findCurrentUser(String name, List<User> users);
}
