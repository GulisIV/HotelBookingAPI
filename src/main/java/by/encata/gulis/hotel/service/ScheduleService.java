package by.encata.gulis.hotel.service;

import by.encata.gulis.hotel.domain.Schedule;
import by.encata.gulis.hotel.exception.hotel.EmptyScheduleException;
import by.encata.gulis.hotel.exception.hotel.InvalidScheduleTimeException;
import by.encata.gulis.hotel.exception.hotel.ScheduleNotFoundException;
import by.encata.gulis.hotel.repository.ScheduleRepo;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepo scheduleRepo;

    public ScheduleService(ScheduleRepo scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
    }


    public void saveSchedule(Schedule schedule) {
        if (schedule == null){
            throw new EmptyScheduleException("Empty schedule value!");
        }
        if(schedule.getOpenTime().equals(schedule.getCloseTime()) ||
                schedule.getOpenTime().isAfter(schedule.getCloseTime())){
            throw new InvalidScheduleTimeException("Check time values!");
        }
        scheduleRepo.save(schedule);
    }

    public Schedule getScheduleByDay(DayOfWeek day){
        return scheduleRepo.findById(day).orElseThrow(()
                -> new ScheduleNotFoundException("Cannot find schedule for this day!"));
    }

    public List<Schedule> getAllSchedule(){
        return scheduleRepo.findAll();
    }


}
