package by.encata.gulis.hotel.exception.room;

public class RoomExistsException extends RuntimeException {

    public RoomExistsException() {
        super();
    }

    public RoomExistsException(String message) {
        super(message);
    }
}
