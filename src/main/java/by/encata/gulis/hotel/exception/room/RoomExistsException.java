package by.encata.gulis.hotel.exception.room;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Room with such number already exists!")
public class RoomExistsException extends RuntimeException {

}
