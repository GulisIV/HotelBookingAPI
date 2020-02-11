package by.encata.gulis.hotel.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "User with such username already exists!")
public class UserExistsException extends RuntimeException {

}
