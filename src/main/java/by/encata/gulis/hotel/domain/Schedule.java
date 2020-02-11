package by.encata.gulis.hotel.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;

@Document(collection = "schedule")
public class Schedule {

    @Id
    private DayOfWeek day;

    @NotNull
    private String openTime;

    @NotNull
    private String closeTime;

    public Schedule() {
    }

    public Schedule(DayOfWeek day, @NotNull String openTime, @NotNull String closeTime) {
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

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }
}


