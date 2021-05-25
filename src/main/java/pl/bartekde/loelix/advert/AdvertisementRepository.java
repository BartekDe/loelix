package pl.bartekde.loelix.advert;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bartekde.loelix.appuser.User;

import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    public List<Advertisement> findByUser(User user);

}
