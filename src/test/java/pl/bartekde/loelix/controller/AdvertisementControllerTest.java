package pl.bartekde.loelix.controller;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.bartekde.loelix.LoelixApplication;
import pl.bartekde.loelix.auth.request.UserLoginDto;
import pl.bartekde.loelix.auth.request.UserRegisterDto;
import pl.bartekde.loelix.user.User;
import pl.bartekde.loelix.user.UserRepository;
import pl.bartekde.loelix.util.JsonUtil;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = LoelixApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class AdvertisementControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    // TODO: when testing controllers use some helper classes to perform registration and login
    @Test
    public void testNewUserShouldNotHaveAdverts() throws Exception {

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

        String loginResponse = mvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(userLoginDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("email", is(email)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String token = new JSONObject(loginResponse)
                .getString("token");

        Optional<User> user = userRepository.findByEmail(email);

        Long userId = user.map(User::getId).orElse(0L);

        mvc.perform(get("/advertisement/user/" + userId)
                        .header("Authorization", "Bearer " + token)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));

    }

}
