package by.encata.gulis.hotel.repository;

import by.encata.gulis.hotel.domain.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepo extends MongoRepository<Room, Long> {

}
