package by.encata.gulis.hotel.exception;

public class InvalidReservingTime extends RuntimeException {

    public InvalidReservingTime() {
        super();
    }

    public InvalidReservingTime(String message) {
        super(message);
    }
}
