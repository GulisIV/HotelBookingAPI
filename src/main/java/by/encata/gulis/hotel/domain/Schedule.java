package by.encata.gulis.hotel.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Document
public class Schedule {


    private Long id;
    //В новом API соответствующие классы
    // java.time.LocalDate и java.time.LocalTime хранят чистые кортежи (yyyy,MM,dd) и (HH,mm,dd) соответственно,
    // и никакой лишней информации или логики в этих классах нет.
    // Также введен класс java.time.LocalDateTime который хранит оба кортежа.
    @Id
    private DayOfWeek day;
    private LocalTime openTime;
    private LocalTime closeTime;

    public Schedule() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


