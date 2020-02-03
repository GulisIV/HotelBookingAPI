package by.encata.gulis.hotel.domain;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Reservation {

    private String userId;

/*    private LocalDateTime checkIn;
    private LocalDateTime checkOut;*/
    private DayOfWeek day;
    private LocalTime checkIn;
    private LocalTime checkOut;

    public Reservation() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public LocalTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalTime checkOut) {
        this.checkOut = checkOut;
    }
}
