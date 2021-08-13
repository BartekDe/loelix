package pl.bartekde.loelix.advert;

import org.springframework.stereotype.Service;
import pl.bartekde.loelix.advert.request.CreateAdvertisementRequest;
import pl.bartekde.loelix.user.User;

@Service
public class AdvertisementService {

    public Advertisement createAdvert(CreateAdvertisementRequest dto, User user) {
        Advertisement advert = new Advertisement();
        advert.setName(dto.name);
        advert.setPriceNegotiable(dto.isPriceNegotiable);
        advert.setPrice(dto.price);
        advert.setUser(user);

        return advert;
    }

}
