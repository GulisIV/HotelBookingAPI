package by.encata.gulis.hotel.service;

import by.encata.gulis.hotel.domain.Schedule;
import by.encata.gulis.hotel.exception.hotel.InvalidScheduleTimeException;
import by.encata.gulis.hotel.exception.hotel.ScheduleNotFoundException;
import by.encata.gulis.hotel.repository.ScheduleRepo;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepo scheduleRepo;

    public ScheduleService(ScheduleRepo scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
    }

    public void saveSchedule(Schedule schedule) {
        if (schedule == null) {
            throw new InvalidScheduleTimeException();
        }
        LocalTime start = LocalTime.parse(schedule.getOpenTime());
        LocalTime end = LocalTime.parse(schedule.getCloseTime());
        if (start.equals(end) || start.isAfter(end)) {
            throw new InvalidScheduleTimeException();
        }

        scheduleRepo.save(schedule);
    }

    public void saveScheduleList(List<Schedule> scheduleList) {

        for (Schedule schedule : scheduleList) {
            saveSchedule(schedule);
        }
    }

    public Schedule getScheduleByDay(DayOfWeek day) {
        return scheduleRepo.findById(day).orElseThrow(ScheduleNotFoundException::new);
    }

    public List<Schedule> getAllSchedule() {
        return scheduleRepo.findAll();
    }

    public void deleteAll(){
        scheduleRepo.deleteAll();
    }

}
