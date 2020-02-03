package by.encata.gulis.hotel.service;

import by.encata.gulis.hotel.domain.Schedule;
import by.encata.gulis.hotel.exception.hotel.InvalidScheduleTimeException;
import by.encata.gulis.hotel.exception.hotel.ScheduleNotFoundException;
import by.encata.gulis.hotel.repository.ScheduleRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduleServiceTest {

    @Autowired
    private ScheduleService scheduleService;

    @MockBean
    private ScheduleRepo scheduleRepo;

    @Test
    public void saveSchedule() {
        Schedule schedule = new Schedule();
        schedule.setOpenTime(LocalTime.of(8,0));
        schedule.setCloseTime(LocalTime.of(20,30));

        scheduleService.saveSchedule(schedule);

        Mockito.verify(scheduleRepo, Mockito.times(1)).save(schedule);

    }

    @Test(expected = InvalidScheduleTimeException.class)
    public void saveScheduleInvalidScheduleTimeTest() {
        Schedule schedule = new Schedule();
        schedule.setOpenTime(LocalTime.of(20,30));
        schedule.setCloseTime(LocalTime.of(8,0));

        scheduleService.saveSchedule(schedule);

        Mockito.verify(scheduleRepo, Mockito.times(0)).save(ArgumentMatchers.any(Schedule.class));
    }

    @Test
    public void getScheduleByDayTest() {

        Mockito.doReturn(Optional.of(new Schedule()))
                .when(scheduleRepo)
                .findById(DayOfWeek.MONDAY);

        scheduleService.getScheduleByDay(DayOfWeek.MONDAY);

        Mockito.verify(scheduleRepo, Mockito.times(1)).findById(ArgumentMatchers.any(DayOfWeek.class));
    }

    @Test(expected = ScheduleNotFoundException.class)
    public void getScheduleNotFoundTest() {
        scheduleService.getScheduleByDay(DayOfWeek.MONDAY);

        Mockito.verify(scheduleRepo, Mockito.times(0)).findById(ArgumentMatchers.any(DayOfWeek.class));

    }

    @Test
    public void getAllScheduleTest() {

        scheduleService.getAllSchedule();

        Mockito.verify(scheduleRepo, Mockito.times(1)).findAll();
    }
}