package by.encata.gulis.hotel.repository;

import by.encata.gulis.hotel.domain.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigDecimal;
import java.util.List;

public interface RoomRepo extends MongoRepository<Room, Long> {
    Room findByRoomId (String roomId);
    Room findByNumber (Long number);
    List<Room> findByNumberOfBeds (Byte numberOfBeds);
    List<Room> findByPrice (BigDecimal price);
}
