package by.encata.gulis.hotel.controller;

import by.encata.gulis.hotel.domain.Schedule;
import by.encata.gulis.hotel.service.ScheduleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.util.List;

@RestController
@RequestMapping("/hotel")
//@PreAuthorize("hasAuthority('USER')")
@PreAuthorize("hasAuthority('ADMIN')")
////@PreAuthorize("hasRole('ADMIN')")
public class HotelController {


    private final ScheduleService scheduleService;

    public HotelController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/schedule")
    public List<Schedule> getHotelSchedule() {
        return scheduleService.getAllSchedule();
    }

    @GetMapping ("/schedule/{day}")
    public Schedule setHotelWorkTimeByDay(@PathVariable DayOfWeek day) {
        return scheduleService.getScheduleByDay(day);
    }

    @PostMapping("/schedule/set")
    public Schedule setHotelWorkTimeByDay(@Valid Schedule schedule) {
        scheduleService.saveSchedule(schedule);
        return schedule;
    }


}
