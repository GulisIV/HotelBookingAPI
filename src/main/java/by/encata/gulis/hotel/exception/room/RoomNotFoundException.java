package by.encata.gulis.hotel.exception.room;

public class RoomNotFoundException extends RuntimeException {

    public RoomNotFoundException() {
        super();
    }

    public RoomNotFoundException(String message) {
        super(message);
    }
}
