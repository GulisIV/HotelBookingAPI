package by.encata.gulis.hotel.exception.room;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Reservation crossing room breaks!")
public class RoomIsNotWorkingException extends RuntimeException {

}
