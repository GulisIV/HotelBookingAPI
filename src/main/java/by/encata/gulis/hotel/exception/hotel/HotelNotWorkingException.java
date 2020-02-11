package by.encata.gulis.hotel.exception.hotel;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Hotel is not working this time!")
public class HotelNotWorkingException extends RuntimeException {

}
