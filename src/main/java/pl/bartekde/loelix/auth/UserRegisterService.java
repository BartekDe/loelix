package pl.bartekde.loelix.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.bartekde.loelix.user.User;
import pl.bartekde.loelix.user.UserRepository;
import pl.bartekde.loelix.auth.request.UserRegisterDto;
import pl.bartekde.loelix.exception.EmailExistsException;
import pl.bartekde.loelix.exception.UsernameExistsException;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserRegisterService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public void registerUser(UserRegisterDto userRegisterDto) throws EmailExistsException, UsernameExistsException {

        if (emailExists(userRegisterDto.email)) {
            throw new EmailExistsException();
        }

        if (nameExists(userRegisterDto.name)) {
            throw new UsernameExistsException();
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(userRegisterDto.password);
        User newUser = new User(
                userRegisterDto.name,
                userRegisterDto.email,
                password
        );
        newUser.setRoles(Arrays.asList("ROLE_USER"));

        System.out.println(newUser);

        entityManager.persist(newUser);
    }

    private boolean emailExists(String email) {
        Optional<User> users = userRepository.findByEmail(email);

        if (users.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean nameExists(String username) {
        User user = userRepository.findByName(username).orElse(null);

        if (user != null) {
            return true;
        } else {
            return false;
        }
    }


}
