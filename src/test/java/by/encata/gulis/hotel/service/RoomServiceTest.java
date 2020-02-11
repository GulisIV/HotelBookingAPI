package by.encata.gulis.hotel.service;

import by.encata.gulis.hotel.domain.*;
import by.encata.gulis.hotel.domain.dto.ReservationDto;
import by.encata.gulis.hotel.exception.hotel.HotelNotWorkingException;
import by.encata.gulis.hotel.exception.hotel.InvalidScheduleTimeException;
import by.encata.gulis.hotel.exception.room.RoomExistsException;
import by.encata.gulis.hotel.exception.room.RoomIsBookedException;
import by.encata.gulis.hotel.exception.room.RoomIsNotWorkingException;
import by.encata.gulis.hotel.exception.room.RoomNotFoundException;
import by.encata.gulis.hotel.exception.roomBreak.InvalidRoomBreakTimeException;
import by.encata.gulis.hotel.repository.RoomRepo;
import by.encata.gulis.hotel.repository.ScheduleRepo;
import org.junit.Assert;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @MockBean
    private RoomRepo roomRepo;

    @MockBean
    private ScheduleRepo scheduleRepo;

    @Test
    public void addRoomTest() {
        Room room = new Room();

        roomService.addRoom(room);

        Mockito.verify(roomRepo, Mockito.times(1)).save(room);

    }

    @Test(expected = RoomExistsException.class)
    public void addRoomFailTest() {
        Room room = new Room();
        room.setNumber(1L);

        Mockito
                .when(roomRepo.findById(1L))
                .thenReturn(java.util.Optional.of(room));

        roomService.addRoom(room);

        Mockito.verify(roomRepo, Mockito.times(0)).save(ArgumentMatchers.any(Room.class));

    }

    @Test
    public void deleteRoomTest() {
        Room room = new Room();

        Mockito
                .when(roomRepo.findById(1L))
                .thenReturn(java.util.Optional.of(room));

        roomService.deleteRoom(1L);

        Mockito.verify(roomRepo, Mockito.times(1)).delete(room);
    }

    @Test(expected = RoomNotFoundException.class)
    public void deleteRoomFailTest() {
        Room room = new Room();

        roomService.deleteRoom(1L);

        Mockito.verify(roomRepo, Mockito.times(0)).delete(room);
    }

    @Test
    public void deleteAllRoomsTest() {

        roomService.deleteAllRooms();

        Mockito.verify(roomRepo, Mockito.times(1)).deleteAll();
    }

    @Test
    public void addRoomReservationTest() {
        //given
        //creating reservation for reservationDto
        Reservation reservation = new Reservation();
        reservation.setDay(DayOfWeek.MONDAY);
        reservation.setCheckIn(LocalTime.of(9,0).toString());
        reservation.setCheckOut(LocalTime.of(10,0).toString());

        //creating reservationDto
        Long number = 1L;
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setRoomNumber(number);
        reservationDto.setReservation(reservation);

        //creating "active" user
        User user = new User();
        user.setId("1");

        //creating schedule
        Schedule schedule = new Schedule();
        schedule.setDay(DayOfWeek.MONDAY);
        schedule.setOpenTime(LocalTime.of(9, 0).toString());
        schedule.setCloseTime(LocalTime.of(21, 0).toString());

        //creating room and room params
        Room room = Mockito.spy(new Room());
        List<RoomBreak> roomBreaks = new ArrayList<>();
        List<Reservation> roomReservations = new ArrayList<>();

        //when
        Mockito.when(scheduleRepo.findById(DayOfWeek.MONDAY)).thenReturn(Optional.of(schedule));
        Mockito.when(roomRepo.findById(number)).thenReturn(Optional.of(room));

        Mockito.when(room.getRoomBreaks()).thenReturn(roomBreaks);
        Mockito.when(room.getReservations()).thenReturn(roomReservations);

        roomService.addRoomReservation(reservationDto, user);

        //then
        Assert.assertEquals(roomReservations.size(), 1);
        Mockito.verify(roomRepo, Mockito.times(1)).save(room);

    }

    @Test(expected = InvalidScheduleTimeException.class)
    public void addRoomReservationFailTest() {
        //given
        //creating reservation for reservationDto
        Reservation reservation = new Reservation();
        reservation.setDay(DayOfWeek.MONDAY);
        reservation.setCheckIn(LocalTime.of(10,0).toString());
        reservation.setCheckOut(LocalTime.of(9,0).toString());
        //creating reservationDto
        Long number = 1L;
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setRoomNumber(number);
        reservationDto.setReservation(reservation);

        //creating "active" user
        User user = new User();
        user.setId("1");

        //when
        roomService.addRoomReservation(reservationDto, user);
    }

    @Test(expected = HotelNotWorkingException.class)
    public void addRoomReservationFailTestByHotelNotWorking() {
        //given
        //creating reservation for reservationDto
        Reservation reservation = new Reservation();
        reservation.setDay(DayOfWeek.MONDAY);
        reservation.setCheckIn(LocalTime.of(8,0).toString());
        reservation.setCheckOut(LocalTime.of(10,0).toString());
        //creating reservationDto
        Long number = 1L;
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setRoomNumber(number);
        reservationDto.setReservation(reservation);

        //creating "active" user
        User user = new User();
        user.setId("1");

        //creating schedule
        Schedule schedule = new Schedule();
        schedule.setDay(DayOfWeek.MONDAY);
        schedule.setOpenTime(LocalTime.of(9, 0).toString());
        schedule.setCloseTime(LocalTime.of(21, 0).toString());

        //when
        Mockito.when(scheduleRepo.findById(DayOfWeek.MONDAY)).thenReturn(Optional.of(schedule));

        roomService.addRoomReservation(reservationDto, user);

    }

    @Test(expected = RoomNotFoundException.class)
    public void addRoomReservationFailTestByRoomNotFound() {
        //given
        //creating reservation for reservationDto
        Reservation reservation = new Reservation();
        reservation.setDay(DayOfWeek.MONDAY);
        reservation.setCheckIn(LocalTime.of(9,0).toString());
        reservation.setCheckOut(LocalTime.of(10,0).toString());
        //creating reservationDto
        Long number = 1L;
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setRoomNumber(number);
        reservationDto.setReservation(reservation);

        //creating "active" user
        User user = new User();
        user.setId("1");

        //creating schedule
        Schedule schedule = new Schedule();
        schedule.setDay(DayOfWeek.MONDAY);
        schedule.setOpenTime(LocalTime.of(9, 0).toString());
        schedule.setCloseTime(LocalTime.of(21, 0).toString());

        //when
        Mockito.when(scheduleRepo.findById(DayOfWeek.MONDAY)).thenReturn(Optional.of(schedule));
        Mockito.when(roomRepo.findById(1L)).thenReturn(Optional.empty());

        roomService.addRoomReservation(reservationDto, user);
    }

    @Test(expected = RoomIsNotWorkingException.class)
    public void addRoomReservationFailTestByRoomNotWorking() {
        //given
        //creating reservation for reservationDto
        Reservation reservation = new Reservation();
        reservation.setDay(DayOfWeek.MONDAY);
        reservation.setCheckIn(LocalTime.of(9,0).toString());
        reservation.setCheckOut(LocalTime.of(12,10).toString());
        //creating reservationDto
        Long number = 1L;
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setRoomNumber(number);
        reservationDto.setReservation(reservation);

        //creating "active" user
        User user = new User();
        user.setId("1");

        //creating schedule
        Schedule schedule = new Schedule();
        schedule.setDay(DayOfWeek.MONDAY);
        schedule.setOpenTime(LocalTime.of(9, 0).toString());
        schedule.setCloseTime(LocalTime.of(21, 0).toString());

        //creating room and room params
        Room room = Mockito.spy(new Room());
        List<RoomBreak> roomBreaks = new ArrayList<>();
        List<Reservation> roomReservations = new ArrayList<>();

        //creating RoomBreak
        RoomBreak roomBreak = new RoomBreak();
        roomBreak.setDay(DayOfWeek.MONDAY);
        roomBreak.setStart(LocalTime.of(12, 0).toString());
        roomBreak.setEnd(LocalTime.of(13, 0).toString());
        roomBreaks.add(roomBreak);

        //when
        Mockito.when(scheduleRepo.findById(DayOfWeek.MONDAY)).thenReturn(Optional.of(schedule));
        Mockito.when(roomRepo.findById(1L)).thenReturn(Optional.of(room));
        Mockito.when(room.getRoomBreaks()).thenReturn(roomBreaks);

        roomService.addRoomReservation(reservationDto, user);
    }

    @Test(expected = RoomIsBookedException.class)
    public void addRoomReservationFailTestByRoomIsBooked() {
        //given
        //creating reservation for reservationDto
        Reservation reservation = new Reservation();
        reservation.setDay(DayOfWeek.MONDAY);
        reservation.setCheckIn(LocalTime.of(9,0).toString());
        reservation.setCheckOut(LocalTime.of(12,10).toString());
        //creating reservationDto
        Long number = 1L;
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setRoomNumber(number);
        reservationDto.setReservation(reservation);

        //creating "active" user
        User user = new User();
        user.setId("1");

        //creating schedule
        Schedule schedule = new Schedule();
        schedule.setDay(DayOfWeek.MONDAY);
        schedule.setOpenTime(LocalTime.of(9, 0).toString());
        schedule.setCloseTime(LocalTime.of(21, 0).toString());

        //creating room and room params
        Room room = Mockito.spy(new Room());
        List<RoomBreak> roomBreaks = new ArrayList<>();
        List<Reservation> roomReservations = new ArrayList<>();

        //creating room existing reservation
        Reservation existingReservation = new Reservation();
        existingReservation.setDay(DayOfWeek.MONDAY);
        existingReservation.setCheckIn(LocalTime.of(10, 0).toString());
        existingReservation.setCheckOut(LocalTime.of(13, 0).toString());
        roomReservations.add(existingReservation);

        //when
        Mockito.when(scheduleRepo.findById(DayOfWeek.MONDAY)).thenReturn(Optional.of(schedule));
        Mockito.when(roomRepo.findById(1L)).thenReturn(Optional.of(room));
        Mockito.when(room.getRoomBreaks()).thenReturn(roomBreaks);
        Mockito.when(room.getReservations()).thenReturn(roomReservations);

        roomService.addRoomReservation(reservationDto, user);
    }

    @Test
    public void setRoomBreaksTest() {
        //given
        Room room = Mockito.spy(new Room());

        Long number = 1L;

        //creating existing RoomBreaks in room
        RoomBreak existingRoomBreak = new RoomBreak();
        existingRoomBreak.setDay(DayOfWeek.MONDAY);
        existingRoomBreak.setStart(LocalTime.of(11,0).toString());
        existingRoomBreak.setEnd(LocalTime.of(12,0).toString());
        List<RoomBreak> existingRoomBreakList = new ArrayList<>();
        existingRoomBreakList.add(existingRoomBreak);

        //creating RoomBreak to be added
        RoomBreak newRoomBreak = new RoomBreak();
        newRoomBreak.setDay(DayOfWeek.TUESDAY);
        newRoomBreak.setStart(LocalTime.of(11,0).toString());
        newRoomBreak.setEnd(LocalTime.of(12,0).toString());
        List<RoomBreak> newRoomBreakList = new ArrayList<>();
        newRoomBreakList.add(newRoomBreak);

        //when
        Mockito.when(roomRepo.findById(number)).thenReturn(Optional.of(room));

        Mockito.when(room.getRoomBreaks()).thenReturn(existingRoomBreakList);

        roomService.setRoomBreaks(number, newRoomBreakList);
        //then
        Assert.assertEquals(room.getRoomBreaks().size(), 2);
        Mockito.verify(roomRepo, Mockito.times(1)).save(room);
    }

    @Test(expected = RoomNotFoundException.class)
    public void setRoomBreaksTestFailByRoomNotFound() {

        Mockito.when(roomRepo.findById(1L)).thenReturn(Optional.empty());

        roomService.setRoomBreaks(1L, Collections.emptyList());

    }

    @Test(expected = InvalidRoomBreakTimeException.class)
    public void setRoomBreaksTestFailByInvalidRoomBreakTime() {
        //given
        Room room = new Room();

        Long number = 1L;

        //creating RoomBreak to be added
        RoomBreak newRoomBreak = new RoomBreak();
        newRoomBreak.setDay(DayOfWeek.TUESDAY);
        newRoomBreak.setStart(LocalTime.of(12,0).toString());
        newRoomBreak.setEnd(LocalTime.of(11,0).toString());
        List<RoomBreak> newRoomBreakList = new ArrayList<>();
        newRoomBreakList.add(newRoomBreak);

        //when
        Mockito.when(roomRepo.findById(number)).thenReturn(Optional.of(room));

        roomService.setRoomBreaks(number, newRoomBreakList);
        //then
        Mockito.verify(roomRepo, Mockito.times(0)).save(room);
    }

    @Test
    public void findAvailableRoomsByTimeTest() {
        //given
        //creating schedule
        Schedule schedule = new Schedule();
        schedule.setDay(DayOfWeek.MONDAY);
        schedule.setOpenTime(LocalTime.of(9, 0).toString());
        schedule.setCloseTime(LocalTime.of(21, 0).toString());

        //creating room and room params
        Room room = Mockito.spy(new Room());
        List<RoomBreak> roomBreaks = new ArrayList<>();
        List<Reservation> roomReservations = new ArrayList<>();

        //create params for reservation
        DayOfWeek day = DayOfWeek.MONDAY;
        LocalTime from = LocalTime.of(10, 0);
        LocalTime to = LocalTime.of(11, 0);

        //when
        Mockito.when(room.getRoomBreaks()).thenReturn(roomBreaks);
        Mockito.when(room.getReservations()).thenReturn(roomReservations);

        Mockito.when(scheduleRepo.findById(day)).thenReturn(Optional.of(schedule));
        Mockito.when(roomRepo.findAll()).thenReturn(List.of(room));


        List<Room> result = roomService.findAvailableRoomsByTime(day, from, to);
        //then
        Assert.assertEquals(result.size(), 1);
        Assert.assertTrue(result.contains(room));
    }

    @Test(expected = InvalidScheduleTimeException.class)
    public void findAvailableRoomsByTimeFailTest() {

        //create params for reservation
        DayOfWeek day = DayOfWeek.MONDAY;
        LocalTime from = LocalTime.of(9, 0);
        LocalTime to = LocalTime.of(8, 0);

        List<Room> result = roomService.findAvailableRoomsByTime(day, from, to);

        Assert.assertTrue(result.isEmpty());
        Mockito.verify(roomRepo, Mockito.times(0)).findAll();
    }

    @Test(expected = HotelNotWorkingException.class)
    public void findAvailableRoomsByTimeFailTestByHotelNotWorking() {

        //creating schedule
        Schedule schedule = new Schedule();
        schedule.setDay(DayOfWeek.MONDAY);
        schedule.setOpenTime(LocalTime.of(9, 0).toString());
        schedule.setCloseTime(LocalTime.of(21, 0).toString());

        //create params for reservation
        DayOfWeek day = DayOfWeek.MONDAY;
        LocalTime from = LocalTime.of(8, 0);
        LocalTime to = LocalTime.of(11, 0);

        Mockito.when(scheduleRepo.findById(day)).thenReturn(java.util.Optional.of(schedule));

        List<Room> result = roomService.findAvailableRoomsByTime(day, from, to);

        Assert.assertTrue(result.isEmpty());
    }

    @Test(expected = RoomIsNotWorkingException.class)
    public void findAvailableRoomsByTimeFailTestByRoomBreak() {

        //creating schedule
        Schedule schedule = new Schedule();
        schedule.setDay(DayOfWeek.MONDAY);
        schedule.setOpenTime(LocalTime.of(9, 0).toString());
        schedule.setCloseTime(LocalTime.of(21, 0).toString());

        //creating room and room params
        Room room = Mockito.spy(new Room());
        RoomBreak roomBreak = new RoomBreak();
        roomBreak.setDay(DayOfWeek.MONDAY);
        roomBreak.setStart(LocalTime.of(11,0).toString());
        roomBreak.setEnd(LocalTime.of(12,0).toString());

        //create params for reservation
        DayOfWeek day = DayOfWeek.MONDAY;
        LocalTime from = LocalTime.of(9, 0);
        LocalTime to = LocalTime.of(11, 30);

        Mockito.when(scheduleRepo.findById(day)).thenReturn(java.util.Optional.of(schedule));
        Mockito.when(roomRepo.findAll()).thenReturn(List.of(room));
        Mockito.when(room.getRoomBreaks()).thenReturn(List.of(roomBreak));

        List<Room> result = roomService.findAvailableRoomsByTime(day, from, to);

        Assert.assertTrue(result.isEmpty());

    }

    @Test(expected = RoomNotFoundException.class)
    public void findAvailableRoomsByTimeFailTestByRoomReservation() {
        //creating schedule
        Schedule schedule = new Schedule();
        schedule.setDay(DayOfWeek.MONDAY);
        schedule.setOpenTime(LocalTime.of(9, 0).toString());
        schedule.setCloseTime(LocalTime.of(21, 0).toString());

        //creating room and room params
        Room room = Mockito.spy(new Room());
        List<RoomBreak> roomBreaks = new ArrayList<>();

        //create params for existing reservation
        Reservation existingReservation = new Reservation();
        existingReservation.setDay(DayOfWeek.MONDAY);
        existingReservation.setCheckIn(LocalTime.of(9, 0).toString());
        existingReservation.setCheckOut(LocalTime.of(10, 30).toString());

        //create params for new reservation
        DayOfWeek day = DayOfWeek.MONDAY;
        LocalTime from = LocalTime.of(10, 0);
        LocalTime to = LocalTime.of(11, 0);

        Mockito.when(room.getRoomBreaks()).thenReturn(roomBreaks);
        Mockito.when(room.getReservations()).thenReturn(List.of(existingReservation));

        Mockito.when(scheduleRepo.findById(day)).thenReturn(java.util.Optional.of(schedule));
        Mockito.when(roomRepo.findAll()).thenReturn(List.of(room));

        List<Room> result = roomService.findAvailableRoomsByTime(day, from, to);

        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void findRoomByNumberTest() {

        Mockito
                .when(roomRepo.findById(1L))
                .thenReturn(java.util.Optional.of(new Room()));

        roomService.findRoomByNumber(1L);

        Mockito.verify(roomRepo, Mockito.times(1)).findById(1L);
    }

    @Test(expected = RoomNotFoundException.class)
    public void findRoomByNumberFailTest() {

        roomService.findRoomByNumber(1L);

        Mockito.verify(roomRepo, Mockito.times(0)).findById(1L);
    }

    @Test
    public void findAllRoomsTest() {

        roomService.findAllRooms();

        Mockito.verify(roomRepo, Mockito.times(1)).findAll();

    }


}