package by.encata.gulis.hotel.service;

import by.encata.gulis.hotel.domain.*;
import by.encata.gulis.hotel.domain.dto.ReservationDto;
import by.encata.gulis.hotel.exception.hotel.HotelNotWorkingException;
import by.encata.gulis.hotel.exception.hotel.InvalidScheduleTimeException;
import by.encata.gulis.hotel.exception.hotel.ScheduleNotFoundException;
import by.encata.gulis.hotel.exception.room.RoomExistsException;
import by.encata.gulis.hotel.exception.room.RoomIsBookedException;
import by.encata.gulis.hotel.exception.room.RoomIsNotWorkingException;
import by.encata.gulis.hotel.exception.room.RoomNotFoundException;
import by.encata.gulis.hotel.exception.roomBreak.InvalidRoomBreakTimeException;
import by.encata.gulis.hotel.repository.RoomRepo;
import by.encata.gulis.hotel.repository.ScheduleRepo;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;


@Service
public class RoomService {

    private final RoomRepo roomRepo;
    private final ScheduleRepo scheduleRepo;

    public RoomService(RoomRepo roomRepo, ScheduleRepo scheduleRepo) {
        this.roomRepo = roomRepo;
        this.scheduleRepo = scheduleRepo;
    }

    public void addRoom(Room room) {
        Optional<Room> roomFromDb = roomRepo.findById(room.getNumber());

        if (roomFromDb.isPresent()) {
            throw new RoomExistsException();
        }

        roomRepo.save(room);

    }

    public void deleteRoom(Long number) {
        Optional<Room> roomFromDb = roomRepo.findById(number);

        if (!roomFromDb.isPresent()) {
            throw new RoomNotFoundException();
        }

        roomRepo.delete(roomFromDb.get());

    }

    private void checkHotelSchedule(DayOfWeek dayOfWeek, LocalTime from, LocalTime to) {

        Schedule daySchedule = scheduleRepo.findById(dayOfWeek).orElseThrow(ScheduleNotFoundException::new);

        LocalTime hotelOpens = LocalTime.parse(daySchedule.getOpenTime());
        LocalTime hotelCloses = LocalTime.parse(daySchedule.getCloseTime());

        if (from.isBefore(hotelOpens) || to.isAfter(hotelCloses)) {
            throw new HotelNotWorkingException();
        }
    }

    private boolean isReservationCrossingRoomBreak(Room room, Reservation reservation) {

        List<RoomBreak> roomBreaks = room.getRoomBreaks();
        LocalTime from = LocalTime.parse(reservation.getCheckIn());
        LocalTime to = LocalTime.parse(reservation.getCheckOut());

        if (!roomBreaks.isEmpty()) {
            for (RoomBreak roomBreak : roomBreaks) {
                if (roomBreak.getDay().equals(reservation.getDay())) {
                    LocalTime roomBreakStart = LocalTime.parse(roomBreak.getStart());
                    LocalTime roomBreakEnd = LocalTime.parse(roomBreak.getEnd());

                    if (!from.isAfter(roomBreakEnd) && !roomBreakStart.isAfter(to)) {
                        return !from.equals(roomBreakEnd) && !to.equals(roomBreakStart);
                    }
                }
            }
        }

        return false;
    }

    private boolean isReservationCrossingRoomReservations(Room room, Reservation reservation) {

        List<Reservation> roomReservations = room.getReservations();
        int numberOfReservations = roomReservations.size();

        if (!roomReservations.isEmpty()) {

            LocalTime newCheckIn = LocalTime.parse(reservation.getCheckIn());
            LocalTime newCheckOut = LocalTime.parse(reservation.getCheckOut());

            for (Reservation checkReservation : roomReservations) {
                if (reservation.getDay().equals(checkReservation.getDay())) {

                    LocalTime existingCheckIn = LocalTime.parse(checkReservation.getCheckIn());
                    LocalTime existingCheckOut = LocalTime.parse(checkReservation.getCheckOut());

                    if (!newCheckIn.isAfter(existingCheckOut) && !existingCheckIn.isAfter(newCheckOut)) {

                        numberOfReservations--;

                        if (numberOfReservations == 0) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void addRoomReservation(ReservationDto reservationDto, User user ) {

        Reservation newReservation = reservationDto.getReservation();
        newReservation.setUserId(user.getId());

        DayOfWeek day = reservationDto.getReservation().getDay();
        LocalTime from = LocalTime.parse(newReservation.getCheckIn());
        LocalTime to = LocalTime.parse(newReservation.getCheckOut());

        if (from.equals(to) || from.isAfter(to)) {
            throw new InvalidScheduleTimeException();
        }

        checkHotelSchedule(day, from, to);

        Optional<Room> roomOptional = roomRepo.findById(reservationDto.getRoomNumber());

        if(!roomOptional.isPresent()){
            throw new RoomNotFoundException();
        }

        Room room = roomOptional.get();

        if(isReservationCrossingRoomBreak(room, newReservation)){
            throw new RoomIsNotWorkingException();
        }

        if (isReservationCrossingRoomReservations(room, newReservation)) {
            throw new RoomIsBookedException();
        }

        List<Reservation> roomReservations = room.getReservations();
        roomReservations.add(newReservation);
        room.setReservations(roomReservations);
        roomRepo.save(room);
    }

    private void checkRoomBreakValid(RoomBreak roomBreak){

        if(roomBreak.getDay() == null || roomBreak.getStart().isEmpty() || roomBreak.getEnd().isEmpty()){
            throw new InvalidRoomBreakTimeException();
        }

        LocalTime start = LocalTime.parse(roomBreak.getStart());
        LocalTime end = LocalTime.parse(roomBreak.getEnd());

        if (start.equals(end) || start.isAfter(end)){
            throw new InvalidRoomBreakTimeException();
        }
    }

    public void setRoomBreaks(Long roomNumber, List<RoomBreak> breaksList) {

        Optional<Room> roomOptional = roomRepo.findById(roomNumber);

        if(!roomOptional.isPresent()){
            throw new RoomNotFoundException();
        }

        Room room = roomOptional.get();

        List<RoomBreak> existingBreakList = room.getRoomBreaks();

        List<RoomBreak> notSaved = new ArrayList<>();

        if (!existingBreakList.isEmpty()) {

            for (RoomBreak newRoomBreak : breaksList) {

                checkRoomBreakValid(newRoomBreak);
                ListIterator<RoomBreak> iterator = existingBreakList.listIterator();
                boolean isSet = false;

                while (iterator.hasNext()) {
                    RoomBreak iteratorRoomBrake = iterator.next();
                    if (newRoomBreak.getDay().equals(iteratorRoomBrake.getDay())) {
                        iteratorRoomBrake.setStart(newRoomBreak.getStart());
                        iteratorRoomBrake.setEnd(newRoomBreak.getEnd());
                        isSet = true;
                    }
                }

                if (!isSet) {
                    notSaved.add(newRoomBreak);
                }
            }

            if (!notSaved.isEmpty()) {
                existingBreakList.addAll(notSaved);
            }

            room.setRoomBreaks(existingBreakList);
            roomRepo.save(room);
        } else {

            for (RoomBreak newRoomBreak : breaksList) {
                checkRoomBreakValid(newRoomBreak);
            }

            room.setRoomBreaks(breaksList);
            roomRepo.save(room);
        }
    }

    public List<Room> findAvailableRoomsByTime(DayOfWeek day, LocalTime from, LocalTime to) {

        if (from.equals(to) || from.isAfter(to)) {
            throw new InvalidScheduleTimeException();
        }

        checkHotelSchedule(day, from, to);

        Reservation newReservation = new Reservation();
        newReservation.setDay(day);
        newReservation.setCheckIn(from.toString());
        newReservation.setCheckOut(to.toString());

        List<Room> allRooms = roomRepo.findAll();
        List<Room> availableRooms = new ArrayList<>();

        int numberOfRooms = allRooms.size();

        for (Room room : allRooms) {

            if (isReservationCrossingRoomBreak(room, newReservation)) {
                numberOfRooms--;
                if (numberOfRooms == 0) {
                    throw new RoomIsNotWorkingException();
                }
                continue;
            }

            if (!isReservationCrossingRoomReservations(room, newReservation)) {
                availableRooms.add(room);
            }

        }

        if (availableRooms.isEmpty()){
            throw new RoomNotFoundException();
        }
        return availableRooms;
    }

    public Room findRoomByNumber(Long number){
        return roomRepo.findById(number).orElseThrow(RoomNotFoundException::new);
    }

    public List<Room> findAllRooms() {
        return roomRepo.findAll();
    }

    public void deleteAllRooms(){
        roomRepo.deleteAll();
    }
}
