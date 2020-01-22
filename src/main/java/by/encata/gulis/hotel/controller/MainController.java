package by.encata.gulis.hotel.controller;

import by.encata.gulis.hotel.domain.Room;
import by.encata.gulis.hotel.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class MainController {

    private final RoomService roomService;

    public MainController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public String findAllRooms() {
        roomService.findAllRooms();
        return "main";
    }

    @GetMapping
    public List<Room> findAvailableRoomsByTime(LocalDateTime from, LocalDateTime to){
        return roomService.findAvailableRoomsByTime(from, to);
    }

    @PostMapping
    public void addReservation(){
    }







}
