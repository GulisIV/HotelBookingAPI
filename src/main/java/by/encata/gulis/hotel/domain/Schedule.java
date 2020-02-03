package by.encata.gulis.hotel.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Document(collection = "schedule")
public class Schedule {

    @Id
    private DayOfWeek day;

    @NotBlank
    private LocalTime openTime;
    @NotBlank
    private LocalTime closeTime;

    public Schedule() {
    }

    public Schedule(DayOfWeek day, @NotBlank LocalTime openTime, @NotBlank LocalTime closeTime) {
        this.day = day;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public LocalTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }
}


