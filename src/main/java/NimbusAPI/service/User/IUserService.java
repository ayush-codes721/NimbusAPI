package NimbusAPI.service.User;

import NimbusAPI.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface IUserService  {

    Optional<User> OptionalgetUserByUsername(String username);
    User getUserById(Long id);
    User createUser(User user);
    User getUserByUsername(String username);


}
