package pl.bartekde.loelix.advert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bartekde.loelix.appuser.User;

import java.util.List;

@RestController
@RequestMapping(path = "advertisements")
public class AdvertisementController {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @GetMapping(path = "/user/{user}")
    public List<Advertisement> getUserAdvertisements(@PathVariable User user) {
        return this.advertisementRepository.findByUser(user);
    }

}
