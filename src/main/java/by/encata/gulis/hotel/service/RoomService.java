package by.encata.gulis.hotel.service;

import by.encata.gulis.hotel.domain.Reservation;
import by.encata.gulis.hotel.domain.Room;
import by.encata.gulis.hotel.domain.Schedule;
import by.encata.gulis.hotel.domain.dto.ReservationDto;
import by.encata.gulis.hotel.exception.HotelNotWorkingException;
import by.encata.gulis.hotel.exception.InvalidReservingTime;
import by.encata.gulis.hotel.exception.RoomException;
import by.encata.gulis.hotel.exception.RoomIsBookedException;
import by.encata.gulis.hotel.repository.RoomRepo;
import by.encata.gulis.hotel.repository.ScheduleRepo;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**user cannot book room for another day!!
Change LocalDateTime to LocalTime??*/
@Service
public class RoomService {

    private final RoomRepo roomRepo;
    private final ScheduleRepo scheduleRepo;

    public RoomService(RoomRepo roomRepo, ScheduleRepo scheduleRepo) {
        this.roomRepo = roomRepo;
        this.scheduleRepo = scheduleRepo;
    }

    public void saveRoom(Room room) {
        Room roomFromDb = roomRepo.findByNumber(room.getNumber());

        if (roomFromDb != null) {
            throw new RoomException("Room with such number already exists!");
        }

        roomRepo.save(room);

    }

    public void deleteRoom (Long number){
        Room roomFromDb = roomRepo.findByNumber(number);

        if (roomFromDb != null){
            roomRepo.delete(roomFromDb);
        }

        throw new RoomException("No room with such number!");
    }

    //from and to already in UTC (convert in controller)
    private void checkHotelSchedule (LocalDateTime localDateTime) {
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        LocalTime reserveTime = localDateTime.toLocalTime();
        //Optional?????
        //use optional to code some logic for first schedule start? If there is no schedule?
        //I can find day of week by int. enum dayOfWeek.getValue();
        Optional<Schedule> optionalDaySchedule = scheduleRepo.findById(dayOfWeek);
        Schedule daySchedule = optionalDaySchedule.orElseThrow();
        if (reserveTime.getHour() < daySchedule.getOpenTime().getHour() &&
                reserveTime.getMinute() < daySchedule.getOpenTime().getMinute()){
            throw new HotelNotWorkingException("Hotel is not working this time!");
        }
    }

    //from and to already in UTC (convert in controller)
    private boolean isRoomAvailableByTime(List<Reservation> roomReservations, LocalDateTime from, LocalDateTime to) {
        //user can book room only for 1 day max!
        DayOfWeek reservationDay = from.getDayOfWeek();
        LocalTime startReservation = from.toLocalTime();
        LocalTime endReservation = to.toLocalTime();

        for (Reservation checkReservation : roomReservations) {
            LocalTime existingCheckIn = checkReservation.getCheckIn().toLocalTime();
            LocalTime existingCheckOut = checkReservation.getCheckOut().toLocalTime();

            if (reservationDay.equals(checkReservation.getCheckIn().getDayOfWeek())) {
                if (!startReservation.isAfter(existingCheckOut) && !existingCheckIn.isAfter(endReservation)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void addRoomReservation(ReservationDto reservationDto) {

        LocalDateTime from = reservationDto.getReservation().getCheckIn();
        LocalDateTime to = reservationDto.getReservation().getCheckOut();

        if (from.isEqual(to) || from.isAfter(to)){
            throw new InvalidReservingTime("Check reserving time!");
        }

        checkHotelSchedule(from);
        checkHotelSchedule(to);

        Room room = roomRepo.findByNumber(reservationDto.getRoomNumber());
        List<Reservation> roomReservations = room.getReservations();

        if (!isRoomAvailableByTime(roomReservations, from, to)){
            throw new RoomIsBookedException("Room is booked this time!");
        }

        roomReservations.add(reservationDto.getReservation());
        room.setReservations(roomReservations);
    }

    public List<Room> findAvailableRoomsByTime (LocalDateTime from, LocalDateTime to){

        if(from.isEqual(to) || from.isAfter(to)){
            throw new InvalidReservingTime("Check reserving time!");
        }

        List<Room> allRooms = roomRepo.findAll();
        List<Room> availableRooms = null;
        for (Room room : allRooms){
            List<Reservation> roomReservations = room.getReservations();
            if(isRoomAvailableByTime(roomReservations, from, to)){
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public List<Room> findAllRooms (){
        return roomRepo.findAll();
    }

}
