package pl.bartekde.loelix;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.bartekde.loelix.auth.request.UserRegisterDto;
import pl.bartekde.loelix.user.User;
import pl.bartekde.loelix.user.UserRepository;
import pl.bartekde.loelix.util.JsonUtil;

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

    @Test
    public void givenUserRegistered_whenLogin_thenReturnToken() throws Exception {

        String email = "test@gmail.com";

        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.email = email;
        userRegisterDto.name = "Testname";
        userRegisterDto.password = "testpass11";

        mvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(userRegisterDto)))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        Optional<User> user = userRepository.findByEmail(email);

        assertThat(user.isPresent());
    }

}
