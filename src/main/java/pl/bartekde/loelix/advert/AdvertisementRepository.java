package pl.bartekde.loelix.advert;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bartekde.loelix.appuser.User;

import java.util.List;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    public List<Advertisement> findByUser(User user);

}
