package pl.bartekde.loelix.advert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.bartekde.loelix.advert.request.CreateAdvertisementRequest;
import pl.bartekde.loelix.user.User;

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

    @PostMapping(path = "/")
    public void createAdvertisement(@RequestBody @Validated CreateAdvertisementRequest createAdvertisementRequest) {

    }

}
