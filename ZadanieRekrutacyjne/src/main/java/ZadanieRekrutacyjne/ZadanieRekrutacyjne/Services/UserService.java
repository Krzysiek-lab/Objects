package ZadanieRekrutacyjne.ZadanieRekrutacyjne.Services;

import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entity.Role;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Entity.User;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Enums.RoleType;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Interfaces.AddUpdateGetDeleteUser;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Repositories.RoleRepository;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.Repositories.UserRepository;
import ZadanieRekrutacyjne.ZadanieRekrutacyjne.ViewModels.UserViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class UserService implements AddUpdateGetDeleteUser {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public void add(UserViewModel userViewModel) {
        List<Role> roles = userViewModel.getRoles();
        if (roles == null) {
            Role role = new Role();
            role.setType(RoleType.ADMIN);// pomyslec nad tym
            roleRepository.save(role);
            roles = Collections.singletonList(role);
        }

        User user1 = User.builder()
                .firstName(userViewModel.getFirstName())
                .lastName(userViewModel.getLastName())
                .login(userViewModel.getLogin())
                .password(passwordEncoder.encode(userViewModel.getPassword()))
                .roles(roles)
                .build();
        userRepository.save(user1);
    }


    public void update(UserViewModel userDto) {
        Optional<User> optionalUser = userRepository.findById(userDto.getId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setRoles(userDto.getRoles());
            user.setLogin(userDto.getLogin());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userRepository.save(user);
        }
    }


    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }



    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        List<User> users = userRepository.findAll();
        return findCurrentUser(name, users);
    }

    public User findCurrentUser(String name, List<User> users) {
        List<User> currentUser = new ArrayList<>();
        users.stream()
                .filter(e -> e.getLogin().equals(name))
                .forEach(currentUser::add);

        return currentUser.get(0);
    }

}

