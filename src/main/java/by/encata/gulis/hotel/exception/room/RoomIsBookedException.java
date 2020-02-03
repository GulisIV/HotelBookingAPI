package by.encata.gulis.hotel.exception.room;

public class RoomIsBookedException extends RuntimeException {

    public RoomIsBookedException() {
        super();
    }

    public RoomIsBookedException(String message) {
        super(message);
    }
}
