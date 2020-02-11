package by.encata.gulis.hotel.repository;

import by.encata.gulis.hotel.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User, Long> {
    User findByUsername(String username);

    void deleteUserByUsername (String username);
}
