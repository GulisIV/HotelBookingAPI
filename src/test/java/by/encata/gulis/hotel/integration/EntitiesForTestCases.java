package by.encata.gulis.hotel.integration;

import by.encata.gulis.hotel.domain.Role;
import by.encata.gulis.hotel.domain.Room;
import by.encata.gulis.hotel.domain.Schedule;
import by.encata.gulis.hotel.domain.User;
import by.encata.gulis.hotel.service.RoomService;
import by.encata.gulis.hotel.service.ScheduleService;
import by.encata.gulis.hotel.service.UserService;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;


public class EntitiesForTestCases {
/*        - Создание комнаты.
        - Создать расписание для работы гостиницы(пн - чт: 09.00 - 21.00, пт: 11.00 - 18.00)
        - Для первой комнаты задать перерывы на пн-чт: с 12.00-13.00, пт: 14.00 - 15.00. Для второй комнаты задать перерыв пн-чт: с 11.00-12.00, пт: 13.00 - 14.00.

        - Получить все свободные комнаты для понедельника с 09.00 до 12.00. (должна вернуться только первая комната)
        - Получить все свободные комнаты с 14.00 до 18.00 для пятницы. (должна вернуться только вторая комната)
        - Создать бронирование для первой комнаты с 10.40 - 12.00 (должно создаться бронирование)
        - Создать бронирование для первой комнаты с 8.30 - 12.00 (должна вернуться ошибка)
        - Создать бронирование для первой комнаты с 12.30 - 14.00 для вторника (должна вернуться ошибка)*/

    private RoomService roomService;
    private ScheduleService scheduleService;
    private UserService userService;

    public EntitiesForTestCases(RoomService roomService, ScheduleService scheduleService, UserService userService) {
        this.roomService = roomService;
        this.scheduleService = scheduleService;
        this.userService = userService;
    }

    public void createUser(){
        User user = new User();
        user.setUsername("notIlya");
        user.setPassword("upass");
        user.setRole(String.valueOf(Role.USER));
        userService.addUser(user);
    }

    public void createAdmin(){
        User admin = new User();
        admin.setUsername("Ilya");
        admin.setPassword("apass");
        admin.setRole(String.valueOf(Role.ADMIN));
        userService.addUser(admin);
    }

    public void addRoom1() {
        Room room1 = new Room();
        room1.setNumber(1L);
        room1.setNumberOfBeds(1);
        room1.setPrice(BigDecimal.valueOf(100));
        roomService.addRoom(room1);
    }

    public void addRoom2() {
        Room room2 = new Room();
        room2.setNumber(2L);
        room2.setNumberOfBeds(2);
        room2.setPrice(BigDecimal.valueOf(200));
        roomService.addRoom(room2);
    }

    public void setSchedule() {
        scheduleService.saveSchedule(new Schedule(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(21, 0)));
        scheduleService.saveSchedule(new Schedule(DayOfWeek.TUESDAY, LocalTime.of(9, 0), LocalTime.of(21, 0)));
        scheduleService.saveSchedule(new Schedule(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(21, 0)));
        scheduleService.saveSchedule(new Schedule(DayOfWeek.THURSDAY, LocalTime.of(9, 0), LocalTime.of(21, 0)));
        scheduleService.saveSchedule(new Schedule(DayOfWeek.FRIDAY, LocalTime.of(11, 0), LocalTime.of(18, 0)));
    }

    public void createRoom1Breaks() {
        roomService.setRoomBreak(1L, DayOfWeek.MONDAY, LocalTime.of(12, 0), LocalTime.of(13, 0));
        roomService.setRoomBreak(1L, DayOfWeek.TUESDAY, LocalTime.of(12, 0), LocalTime.of(13, 0));
        roomService.setRoomBreak(1L, DayOfWeek.WEDNESDAY, LocalTime.of(12, 0), LocalTime.of(13, 0));
        roomService.setRoomBreak(1L, DayOfWeek.THURSDAY, LocalTime.of(12, 0), LocalTime.of(13, 0));
        roomService.setRoomBreak(1L, DayOfWeek.FRIDAY, LocalTime.of(14, 0), LocalTime.of(15, 0));
    }

    public void createRoom2Breaks() {
        roomService.setRoomBreak(2L, DayOfWeek.MONDAY, LocalTime.of(11, 0), LocalTime.of(12, 0));
        roomService.setRoomBreak(2L, DayOfWeek.TUESDAY, LocalTime.of(11, 0), LocalTime.of(12, 0));
        roomService.setRoomBreak(2L, DayOfWeek.WEDNESDAY, LocalTime.of(11, 0), LocalTime.of(12, 0));
        roomService.setRoomBreak(2L, DayOfWeek.THURSDAY, LocalTime.of(11, 0), LocalTime.of(12, 0));
        roomService.setRoomBreak(2L, DayOfWeek.FRIDAY, LocalTime.of(13, 0), LocalTime.of(14, 0));
    }


}
