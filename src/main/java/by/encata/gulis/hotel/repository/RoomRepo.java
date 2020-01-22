package by.encata.gulis.hotel.repository;

import by.encata.gulis.hotel.domain.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigDecimal;
import java.util.Set;

public interface RoomRepo extends MongoRepository<Room, Long> {
    Room findByRoomId (String roomId);
    Room findByNumber (Long number);
    Set<Room> findByNumberOfBeds (Byte numberOfBeds);
    Set<Room> findByPrice (BigDecimal price);
}
