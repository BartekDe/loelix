package pl.bartekde.loelix;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pl.bartekde.loelix.advert.Advertisement;
import pl.bartekde.loelix.advert.AdvertisementService;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class AdvertisementControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private AdvertisementService advertisementService;

    @Test
    void getAllAdvertisements() {


    }

}
