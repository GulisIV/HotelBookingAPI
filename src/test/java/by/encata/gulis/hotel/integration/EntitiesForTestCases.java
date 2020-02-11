package by.encata.gulis.hotel.integration;

import by.encata.gulis.hotel.domain.*;
import by.encata.gulis.hotel.service.RoomService;
import by.encata.gulis.hotel.service.ScheduleService;
import by.encata.gulis.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class EntitiesForTestCases {
/*        - Создание комнаты.
        - Создать расписание для работы гостиницы(пн - чт: 09.00 - 21.00, пт: 11.00 - 18.00)
        - Для первой комнаты задать перерывы на пн-чт: с 12.00-13.00, пт: 14.00 - 15.00. Для второй комнаты задать перерыв пн-чт: с 11.00-12.00, пт: 13.00 - 14.00.


        - Получить все свободные комнаты для понедельника с 09.00 до 12.00. (должна вернуться только первая комната)
        - Получить все свободные комнаты с 14.00 до 18.00 для пятницы. (должна вернуться только вторая комната)


        - Создать бронирование для первой комнаты с 10.40 - 12.00 (должно создаться бронирование)
        - Создать бронирование для первой комнаты с 8.30 - 12.00 (должна вернуться ошибка)
        - Создать бронирование для первой комнаты с 12.30 - 14.00 для вторника (должна вернуться ошибка)*/

    @Autowired
    private RoomService roomService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private UserService userService;

//    public void createUser(){
//        User user = new User();
//        user.setUsername("hotelUser");
//        user.setPassword("123");
//        userService.addUser(user);
//    }
//
//    public void createAdmin(){
//        User admin = new User();
//        admin.setUsername("hotelAdmin");
//        admin.setPassword("321");
//        userService.addUser(admin);
//        admin.getRoles().add(Role.ADMIN);
//    }

    public void createRoomOne() {
        Room room1 = new Room();
        room1.setNumber(1L);
        room1.setNumberOfBeds(1);
        room1.setPrice(BigDecimal.valueOf(100));
        roomService.addRoom(room1);
    }

    public void createRoomTwo() {
        Room room2 = new Room();
        room2.setNumber(2L);
        room2.setNumberOfBeds(2);
        room2.setPrice(BigDecimal.valueOf(200));
        roomService.addRoom(room2);
    }

    public void setBreaksForRoomOne() {
        List<RoomBreak> breaksForRoomOne = new ArrayList<>();
        breaksForRoomOne.add(new RoomBreak(DayOfWeek.MONDAY, LocalTime.of(12, 0).toString(), LocalTime.of(13, 0).toString()));
        breaksForRoomOne.add(new RoomBreak(DayOfWeek.TUESDAY, LocalTime.of(12, 0).toString(), LocalTime.of(13, 0).toString()));
        breaksForRoomOne.add(new RoomBreak(DayOfWeek.WEDNESDAY, LocalTime.of(12, 0).toString(), LocalTime.of(13, 0).toString()));
        breaksForRoomOne.add(new RoomBreak(DayOfWeek.THURSDAY, LocalTime.of(12, 0).toString(), LocalTime.of(13, 0).toString()));
        breaksForRoomOne.add(new RoomBreak(DayOfWeek.FRIDAY, LocalTime.of(14, 0).toString(), LocalTime.of(15, 0).toString()));

        roomService.setRoomBreaks(1L, breaksForRoomOne);
    }

    public void setBreaksForRoomTwo() {
        List<RoomBreak> breaksForRoomTwo = new ArrayList<>();
        breaksForRoomTwo.add(new RoomBreak(DayOfWeek.MONDAY, LocalTime.of(11, 0).toString(), LocalTime.of(12, 0).toString()));
        breaksForRoomTwo.add(new RoomBreak(DayOfWeek.TUESDAY, LocalTime.of(11, 0).toString(), LocalTime.of(12, 0).toString()));
        breaksForRoomTwo.add(new RoomBreak(DayOfWeek.WEDNESDAY, LocalTime.of(11, 0).toString(), LocalTime.of(12, 0).toString()));
        breaksForRoomTwo.add(new RoomBreak(DayOfWeek.THURSDAY, LocalTime.of(11, 0).toString(), LocalTime.of(12, 0).toString()));
        breaksForRoomTwo.add(new RoomBreak(DayOfWeek.FRIDAY, LocalTime.of(13, 0).toString(), LocalTime.of(14, 0).toString()));

        roomService.setRoomBreaks(2L, breaksForRoomTwo);
    }

    public void setHotelScheduleByWeek() {
        List<Schedule> scheduleList = new ArrayList<>();
        scheduleList.add(new Schedule(DayOfWeek.MONDAY, LocalTime.of(9, 0).toString(), LocalTime.of(21, 0).toString()));
        scheduleList.add(new Schedule(DayOfWeek.TUESDAY, LocalTime.of(9, 0).toString(), LocalTime.of(21, 0).toString()));
        scheduleList.add(new Schedule(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0).toString(), LocalTime.of(21, 0).toString()));
        scheduleList.add(new Schedule(DayOfWeek.THURSDAY, LocalTime.of(9, 0).toString(), LocalTime.of(21, 0).toString()));
        scheduleList.add(new Schedule(DayOfWeek.FRIDAY, LocalTime.of(11, 0).toString(), LocalTime.of(18, 0).toString()));

        scheduleService.saveScheduleList(scheduleList);
    }

    public void clearDb() {
        //userService.deleteAll();
        scheduleService.deleteAll();
        roomService.deleteAllRooms();
    }

}
