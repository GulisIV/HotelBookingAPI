package by.encata.gulis.hotel.controller;

import by.encata.gulis.hotel.domain.Room;
import by.encata.gulis.hotel.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    public RoomController (RoomService roomService){
        this.roomService = roomService;
    }

    @GetMapping
    public String room() {
        return "room";
    }

/*    @GetMapping
    public String addRoom() {
        return "createRoom";
    }*/


/*    @PostMapping("add")
    public String addRoom(Room room, Map<String, Object> model) {

        if(!roomService.saveRoom(room)){
            model.put("message", "Room already exists!");
            return "createRoom";
        }
        return "redirect:/";

    }*/

    @PostMapping
    public String addRoom(Room room) {
        roomService.saveRoom(room);
        return "redirect:/";

    }

/*    @GetMapping("delete")
    public String deleteRoom() {
        return "deleteRoom";
    }*/

    @DeleteMapping
    public String deleteRoom(Long number) {
        roomService.deleteRoom(number);
        return "redirect:/";
    }
}
