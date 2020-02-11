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
@PreAuthorize("hasAuthority('USER')")
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
    public Schedule getHotelScheduleByDay(@PathVariable Integer day) {
        return scheduleService.getScheduleByDay(DayOfWeek.of(day));
    }

    @PostMapping("/schedule/set")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Schedule setHotelScheduleByDay(@Valid @RequestBody Schedule schedule) {
        scheduleService.saveSchedule(schedule);
        return schedule;
    }

    @PostMapping("/schedule/set_week")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Schedule> setHotelScheduleByWeek(@Valid @RequestBody List<Schedule> scheduleList) {
        scheduleService.saveScheduleList(scheduleList);
        return scheduleList;
    }



}
