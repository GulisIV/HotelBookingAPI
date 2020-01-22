package by.encata.gulis.hotel.service;

import by.encata.gulis.hotel.domain.Schedule;
import by.encata.gulis.hotel.repository.ScheduleRepo;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    private final ScheduleRepo scheduleRepo;

    public ScheduleService(ScheduleRepo scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
    }


    public void saveSchedule(Schedule schedule) {
        scheduleRepo.save(schedule);
    }


}
