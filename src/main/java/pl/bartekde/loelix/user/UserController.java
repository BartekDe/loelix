package pl.bartekde.loelix.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "users")
public class UserController {

    @GetMapping("{id}")
    public User getUserData() {
        return new User();
    }
}
