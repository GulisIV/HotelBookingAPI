package by.encata.gulis.hotel.domain;

import javax.validation.constraints.NotBlank;
import java.time.DayOfWeek;

public class RoomBreak {

    @NotBlank
    private DayOfWeek day;

    @NotBlank
    private String start;

    @NotBlank
    private String end;

    public RoomBreak() {
    }

    public RoomBreak(@NotBlank DayOfWeek day, @NotBlank String start, @NotBlank String end) {
        this.day = day;
        this.start = start;
        this.end = end;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
