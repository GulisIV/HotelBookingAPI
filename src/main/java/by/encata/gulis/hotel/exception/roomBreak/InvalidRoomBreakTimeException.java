package by.encata.gulis.hotel.exception.roomBreak;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Check room break time values!")
public class InvalidRoomBreakTimeException extends RuntimeException {

}
