package by.encata.gulis.hotel.service;

import by.encata.gulis.hotel.domain.Reservation;
import by.encata.gulis.hotel.domain.Room;
import by.encata.gulis.hotel.domain.Schedule;
import by.encata.gulis.hotel.domain.dto.ReservationDto;
import by.encata.gulis.hotel.exception.room.RoomExistsException;
import by.encata.gulis.hotel.exception.room.RoomNotFoundException;
import by.encata.gulis.hotel.repository.RoomRepo;
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
    public void addRoom() {
        Room room = new Room();

        roomService.addRoom(room);

        Mockito.verify(roomRepo, Mockito.times(1)).save(room);

    }

    @Test(expected = RoomExistsException.class)
    public void addRoomFailTest() {
        Room room = new Room();
        room.setNumber(1L);

        Mockito.doReturn(new Room())
                .when(roomRepo)
                .findByNumber(1L);

        roomService.addRoom(room);

        Mockito.verify(roomRepo, Mockito.times(0)).save(ArgumentMatchers.any(Room.class));

    }

    @Test
    public void deleteRoom() {
        Room room = new Room();

        Mockito.doReturn(room)
                .when(roomRepo)
                .findByNumber(1L);

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
    public void addRoomReservation() {
        Reservation reservation = new Reservation();
        reservation.setDay(DayOfWeek.MONDAY);
        reservation.setCheckIn(LocalTime.of(8,0));

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setUserId("1");
        reservationDto.setRoomNumber(1L);

        //checkHotelWorkTime return void!!
        Mockito.doReturn(new Schedule())
                .when(scheduleRepo)
                .findById(DayOfWeek.MONDAY);


    }

    @Test
    public void findAvailableRoomsByTime() {

    }
}