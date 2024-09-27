package NimbusAPI.repo;

import NimbusAPI.model.CityBookmark;
import NimbusAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepo extends JpaRepository<CityBookmark,Long> {

    List<CityBookmark> findByUser(User user);

}
