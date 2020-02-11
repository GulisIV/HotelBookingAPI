package by.encata.gulis.hotel.exception.hotel;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Check reserving time values!")
public class InvalidScheduleTimeException extends RuntimeException {

}
