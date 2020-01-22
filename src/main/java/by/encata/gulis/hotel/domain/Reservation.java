package by.encata.gulis.hotel.domain;

import java.time.LocalDateTime;

public class Reservation {

    private Long userId;

    /**
     * store in UTC
     */
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

    public Reservation() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }
}
