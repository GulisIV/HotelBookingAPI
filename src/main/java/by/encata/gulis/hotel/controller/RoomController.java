package by.encata.gulis.hotel.controller;

import by.encata.gulis.hotel.domain.Room;
import by.encata.gulis.hotel.service.RoomService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/room")
@PreAuthorize("hasAuthority('ADMIN')")
public class RoomController {

    private final RoomService roomService;

    public RoomController (RoomService roomService){
        this.roomService = roomService;
    }

    @GetMapping
    public String room() {
        return "room";
    }

    @PostMapping
    public String addRoom(Room room) {
        roomService.saveRoom(room);
        return "redirect:/";

    }

    @DeleteMapping
    public String deleteRoom(Long number) {
        roomService.deleteRoom(number);
        return "redirect:/";
    }



}
