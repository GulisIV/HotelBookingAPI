package by.encata.gulis.hotel.exception.hotel;

public class EmptyScheduleException extends RuntimeException {

    public EmptyScheduleException() {
        super();
    }

    public EmptyScheduleException(String message) {
        super(message);
    }
}
