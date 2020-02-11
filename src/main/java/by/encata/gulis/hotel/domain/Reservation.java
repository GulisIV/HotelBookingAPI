package by.encata.gulis.hotel.domain;

import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;

public class Reservation {

    private String userId;

    @NotNull
    private DayOfWeek day;

    @NotNull
    private String checkIn;

    @NotNull
    private String checkOut;

    public Reservation() {
    }

    public Reservation(@NotNull DayOfWeek day, @NotNull String checkIn, @NotNull String checkOut) {
        this.day = day;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
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

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }
}
