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
import java.util.Optional;
import java.util.Set;

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
    //check hotel work time
    //throw  new RuntimeException("hotel is not working this time");
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
    private void checkIfRoomIsAvailable(Set<Reservation> roomReservations, LocalDateTime from, LocalDateTime to) {
        LocalTime startReservation = from.toLocalTime();
        LocalTime endReservation = to.toLocalTime();

        for (Reservation checkReservation : roomReservations) {
            LocalTime existingCheckIn = checkReservation.getCheckIn().toLocalTime();
            LocalTime existingCheckOut = checkReservation.getCheckOut().toLocalTime();

            if (!startReservation.isAfter(existingCheckOut) && !existingCheckIn.isAfter(endReservation)) {
                throw new RoomIsBookedException("Room is booked for this time!");
            }
        }
/*        public static boolean isOverlapping(Date start1, Date end1, Date start2, Date end2) {
            return !start1.after(end2) && !start2.after(end1);
        }*/
    }

    public void addRoomReservation(ReservationDto reservationDto) {

        LocalDateTime from = reservationDto.getReservation().getCheckIn();
        LocalDateTime to = reservationDto.getReservation().getCheckOut();

        if (from.isEqual(to)){
            throw new InvalidReservingTime("Check reserving time!");
        }

        checkHotelSchedule(from);
        checkHotelSchedule(to);

        Room room = roomRepo.findByNumber(reservationDto.getRoomNumber());
        Set<Reservation> roomReservations = room.getReservations();

        checkIfRoomIsAvailable(roomReservations, from, to);

        roomReservations.add(reservationDto.getReservation());
        room.setReservations(roomReservations);
    }

    public Set<Room> findAvailableRoomsByTime (LocalDateTime from, LocalDateTime to){
        return null;
    }

}
