package by.encata.gulis.hotel.service;

import by.encata.gulis.hotel.domain.Reservation;
import by.encata.gulis.hotel.domain.Room;
import by.encata.gulis.hotel.domain.Schedule;
import by.encata.gulis.hotel.domain.dto.ReservationDto;
import by.encata.gulis.hotel.exception.hotel.HotelNotWorkingException;
import by.encata.gulis.hotel.exception.hotel.InvalidScheduleTimeException;
import by.encata.gulis.hotel.exception.hotel.ScheduleNotFoundException;
import by.encata.gulis.hotel.exception.room.RoomExistsException;
import by.encata.gulis.hotel.exception.room.RoomIsBookedException;
import by.encata.gulis.hotel.exception.room.RoomNotFoundException;
import by.encata.gulis.hotel.repository.RoomRepo;
import by.encata.gulis.hotel.repository.ScheduleRepo;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * user cannot book room for another day!!
 * Change LocalDateTime to LocalTime??
 */
@Service
public class RoomService {

    private final RoomRepo roomRepo;
    private final ScheduleRepo scheduleRepo;

    public RoomService(RoomRepo roomRepo, ScheduleRepo scheduleRepo) {
        this.roomRepo = roomRepo;
        this.scheduleRepo = scheduleRepo;
    }

    public void addRoom(Room room) {
        Room roomFromDb = roomRepo.findByNumber(room.getNumber());

        if (roomFromDb != null) {
            throw new RoomExistsException("Room with such number already exists!");
        }

        roomRepo.save(room);

    }

    public void deleteRoom(Long number) {
        Room roomFromDb = roomRepo.findByNumber(number);

        if (roomFromDb == null) {
            throw new RoomNotFoundException("No room with such number!");
        }
        roomRepo.delete(roomFromDb);

    }

    //from and to already in UTC (convert in integration)
    private void checkHotelSchedule(DayOfWeek dayOfWeek, LocalTime from, LocalTime to) {
        Schedule daySchedule = scheduleRepo.findById(dayOfWeek).orElseThrow(()
                -> new ScheduleNotFoundException("Cannot find schedule for this day!"));

        if(from.isBefore(daySchedule.getOpenTime()) && to.isAfter(daySchedule.getCloseTime())){
            throw new HotelNotWorkingException("Hotel is not working this time!");
        }
    }

    //from and to already in UTC (convert in integration)
    //this method is working incorrect!!! roomReservations null?
    private boolean isRoomAvailableByTime(List<Reservation> roomReservations, DayOfWeek day, LocalTime from, LocalTime to) {
        //user can book room only for 1 day max!
        //method findByNumber may return empty, not null collection
        if(roomReservations.isEmpty()){
            return true;
        }

        for (Reservation checkReservation : roomReservations) {
            DayOfWeek reservationDay = checkReservation.getDay();
            int numberOfReservations = roomReservations.size();

            if (reservationDay.equals(day)) {

                LocalTime existingCheckIn = checkReservation.getCheckIn();
                LocalTime existingCheckOut = checkReservation.getCheckOut();

                if (!from.isAfter(existingCheckOut) && !existingCheckIn.isAfter(to)) {

                    numberOfReservations--;

                    if(numberOfReservations == 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void addRoomReservation(ReservationDto reservationDto) {

        DayOfWeek day = reservationDto.getReservation().getDay();
        LocalTime from = reservationDto.getReservation().getCheckIn();
        LocalTime to = reservationDto.getReservation().getCheckOut();

        if (from.equals(to) || from.isAfter(to)) {
            throw new InvalidScheduleTimeException("Check reserving time!");
        }

        checkHotelSchedule(day, from, to);

        Room room = roomRepo.findByNumber(reservationDto.getRoomNumber());
        List<Reservation> roomReservations = room.getReservations();

        if (!isRoomAvailableByTime(roomReservations, day, from, to)) {
            throw new RoomIsBookedException("Room is booked this time!");
        }

        roomReservations.add(reservationDto.getReservation());
        room.setReservations(roomReservations);
        roomRepo.save(room);
    }

    public void setRoomBreak(Long roomNumber, DayOfWeek day, LocalTime from, LocalTime to){
        Reservation roomReservation = new Reservation();
        roomReservation.setDay(day);
        roomReservation.setCheckIn(from);
        roomReservation.setCheckOut(to);

        List<Reservation> reservations = new ArrayList<>();
        reservations.add(roomReservation);

        Room room = roomRepo.findByNumber(roomNumber);
        room.setReservations(reservations);
    }

    public List<Room> findAvailableRoomsByTime(DayOfWeek day, LocalTime from, LocalTime to) {

        if (from.equals(to) || from.isAfter(to)) {
            throw new InvalidScheduleTimeException("Check reserving time!");
        }

        List<Room> allRooms = roomRepo.findAll();
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : allRooms) {
            List<Reservation> roomReservations = room.getReservations();
            if (isRoomAvailableByTime(roomReservations, day, from, to)) {
                availableRooms.add(room);
            }
        }

        if (availableRooms.isEmpty()){
            throw new RoomNotFoundException("Cannot find suitable rooms!");
        }
        return availableRooms;
    }

    public List<Room> findAllRooms() {
        return roomRepo.findAll();
    }

    public Room findByNumber(Long number){
        return roomRepo.findByNumber(number);
    }

    //I need to take time in user time zone and return in UTC time zone
/*    I don't need this method, cause user must book in hotel time, not his local time
    and I decided that hotel it in UTC time zone
    Or user is in one time zone with hotel*/
    public LocalDateTime convertStringToUtcTime (String date) {
        LocalDateTime ldt = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);
        LocalTime.parse(date);
        return LocalDateTime.from(ldt.atZone(ZoneId.of("UTC")));
    }


}
