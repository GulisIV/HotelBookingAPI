
package by.encata.gulis.hotel.controller;

import by.encata.gulis.hotel.domain.Room;
import by.encata.gulis.hotel.domain.dto.ReservationDto;
import by.encata.gulis.hotel.service.RoomService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/room")
//@PreAuthorize("isAuthenticated()")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("{number}")
    public Room getRoom (@PathVariable Long number){
        return roomService.findByNumber(number);
    }

    //@ModelAttribute
    //BindingResult bindingResult
    @PostMapping("/add")
    public Room addRoom(@Valid Room room) {
        roomService.addRoom(room);
        return room;
    }

    @DeleteMapping("/delete")
    public Long deleteRoom(Long number) {
        roomService.deleteRoom(number);
        return number;
    }

    @GetMapping("/all")
    public List<Room> getAllRooms() {
        return roomService.findAllRooms();
    }

    @GetMapping("/search/{day}")
    public List<Room> findAvailableRoomsByTime(@PathVariable DayOfWeek day,
                                               @RequestParam String from,
                                               @RequestParam String to) {

        LocalTime start = LocalTime.parse(from);
        LocalTime end = LocalTime.parse(to);

        return roomService.findAvailableRoomsByTime(day, start, end);
    }

    @PostMapping("/reserve")
    public void addReservation(@Valid ReservationDto reservationDto) {
        roomService.addRoomReservation(reservationDto);
    }


}
