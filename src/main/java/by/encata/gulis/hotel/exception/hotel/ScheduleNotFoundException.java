package by.encata.gulis.hotel.exception.hotel;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Cannot find schedule for this day!")
public class ScheduleNotFoundException extends RuntimeException {

}
