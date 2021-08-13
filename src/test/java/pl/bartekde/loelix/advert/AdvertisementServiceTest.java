package pl.bartekde.loelix.advert;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.bartekde.loelix.advert.request.CreateAdvertisementRequest;
import pl.bartekde.loelix.user.User;

import java.math.BigDecimal;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AdvertisementServiceTest {

    @Autowired
    private AdvertisementService advertisementService;

    @TestConfiguration
    static class AdvertisementServiceTestContextConfiguration {
        @Bean
        public AdvertisementService employeeService() {
            return new AdvertisementService();
        }
    }

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Test
    public void testCreateAdvertisement() {

        String name = "test name";
        boolean isPriceNegotiable = true;
        double price = 100d;

        Advertisement createdAdvert = createAdvert(name, isPriceNegotiable, price).get();

        Assertions.assertNotNull(createdAdvert);
        Assertions.assertEquals(createdAdvert.getClass(), Advertisement.class);
        Assertions.assertEquals(createdAdvert.getName(), name);
        Assertions.assertEquals(createdAdvert.isPriceNegotiable(), isPriceNegotiable);
        Assertions.assertEquals(createdAdvert.getPrice().doubleValue(), price);
    }

    @Test
    public void testSavingAdvertisementToDb() {

        String name = "test name";
        boolean isPriceNegotiable = true;
        double price = 100d;

        entityManager.persistAndFlush(createAdvert(name, isPriceNegotiable, price).get());

        Advertisement found = advertisementRepository.findByName(name);
        Assertions.assertEquals(found.getName(), name);
        Assertions.assertNotNull(found.getId());
    }

    private Optional<Advertisement> createAdvert(String name, boolean isPriceNegotiable, double price) {
        CreateAdvertisementRequest dto = new CreateAdvertisementRequest();
        dto.name = name;
        dto.isPriceNegotiable = isPriceNegotiable;
        dto.price = BigDecimal.valueOf(price);

        User user = new User("test user", "email@gmail.com", "secret");

        entityManager.persistAndFlush(user);

        return Optional.of(advertisementService.createAdvert(dto, user));
    }

}
