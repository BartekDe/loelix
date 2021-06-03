package pl.bartekde.loelix.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bartekde.loelix.auth.jwt.JwtTokenProvider;
import pl.bartekde.loelix.auth.request.UserLoginDto;
import pl.bartekde.loelix.auth.request.UserRegisterDto;
import pl.bartekde.loelix.user.User;
import pl.bartekde.loelix.user.UserRepository;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRegisterService userRegisterService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity register(@Validated @RequestBody UserRegisterDto registrationData) {
        this.userRegisterService.registerUser(registrationData);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/login")
    public ResponseEntity login(@Validated @RequestBody UserLoginDto userLoginDto) {
//        try {
            this.authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginDto.email, userLoginDto.password)
            );

            User user = this.userRepository.findByEmail(userLoginDto.email)
                    .orElseThrow(() -> new UsernameNotFoundException(
                            "User with email " + userLoginDto.email + " not found"
                    ));

            String token = jwtTokenProvider.createToken(userLoginDto.email, user.getRoles());

            Map<Object, Object> response = new HashMap<>(2);
            response.put("email", userLoginDto.email);
            response.put("token", token);

            return ResponseEntity.ok(response);
//        } catch (AuthenticationException e) {
//            throw new BadCredentialsException("Invalid user email/password supplied.");
//        }

    }

}
