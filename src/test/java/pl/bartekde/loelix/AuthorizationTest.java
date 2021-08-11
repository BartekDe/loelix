package pl.bartekde.loelix;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.CoreMatchers.is;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.bartekde.loelix.auth.jwt.JwtTokenProvider;
import pl.bartekde.loelix.auth.request.UserLoginDto;
import pl.bartekde.loelix.auth.request.UserRegisterDto;
import pl.bartekde.loelix.user.User;
import pl.bartekde.loelix.user.UserRepository;
import pl.bartekde.loelix.util.JsonUtil;

import java.util.Arrays;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = LoelixApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class AuthorizationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @After
    public void resetDb() {
        userRepository.deleteAll();
    }

    @Test
    public void whenUserRegistration_thenUserExists() throws Exception {

        String email = "test@gmail.com";

        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.email = email;
        userRegisterDto.name = "Testname";
        userRegisterDto.password = "testpass11";

        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(userRegisterDto))
                )
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        Optional<User> user = userRepository.findByEmail(email);

        assertThat(user.isPresent());
    }

    @Test
    public void givenUserRegistered_whenUserLogin_thenReturnToken() throws Exception {
        String email = "test@gmail.com";
        String password = "testpass11";

        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.email = email;
        userRegisterDto.name = "Testname";
        userRegisterDto.password = password;

        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(userRegisterDto))
                )
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.email = email;
        userLoginDto.password = password;

        String token = jwtTokenProvider.createToken(email, Arrays.asList("ROLE_USER"));

        mvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(userLoginDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("email", is(email)))
                .andExpect(jsonPath("token", Matchers.startsWith(token.substring(0, 19))));
    }

}
