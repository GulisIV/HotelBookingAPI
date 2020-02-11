
package by.encata.gulis.hotel.controller;

import by.encata.gulis.hotel.domain.Room;
import by.encata.gulis.hotel.domain.RoomBreak;
import by.encata.gulis.hotel.domain.User;
import by.encata.gulis.hotel.domain.dto.ReservationDto;
import by.encata.gulis.hotel.service.RoomService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/room")
@PreAuthorize("hasAuthority('USER')")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("{number}")
    public Room getRoom (@PathVariable Long number){
        return roomService.findRoomByNumber(number);
    }

    @PostMapping(path = "/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Room addRoom(@Valid @RequestBody Room room) {
        roomService.addRoom(room);
        return room;
    }

    @DeleteMapping("{number}/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Long deleteRoom(@PathVariable Long number) {
        roomService.deleteRoom(number);
        return number;
    }

    @GetMapping("/all")
    public List<Room> getAllRooms() {
        return roomService.findAllRooms();
    }

    @GetMapping("/search/{day}")
    public List<Room> findAvailableRoomsByTime(@PathVariable Integer day,
                                               @RequestParam String from,
                                               @RequestParam String to) {

        return roomService.findAvailableRoomsByTime(DayOfWeek.of(day),
                LocalTime.parse(from),
                LocalTime.parse(to));
    }

    @PostMapping("/reserve")
    public ReservationDto addReservation(@Valid @RequestBody ReservationDto reservationDto,
                                         @AuthenticationPrincipal User activeUser) {

        roomService.addRoomReservation(reservationDto, activeUser);
        return reservationDto;
    }

    @PostMapping("/{number}/breaks")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<RoomBreak> setRoomBreak(@PathVariable Long number,
                                        @RequestBody List<RoomBreak> breaksList) {

        roomService.setRoomBreaks(number, breaksList);
        return breaksList;
    }


}
