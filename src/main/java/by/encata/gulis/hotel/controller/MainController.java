package by.encata.gulis.hotel.controller;

import by.encata.gulis.hotel.domain.Room;
import by.encata.gulis.hotel.repository.RoomRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class MainController {

    private final RoomRepo roomRepo;

    public MainController(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }

    @GetMapping
    public String main(Map<String, Object> model) {
        Iterable<Room> rooms = roomRepo.findAll();

        model.put("rooms", rooms);

        return "main";
    }

/*    @PostMapping
    public String add(@RequestParam String text, @RequestParam String tag, Map<String, Object> model) {
        Hotel message = new Hotel(text, tag);

        roomRepo.save(message);

        Iterable<Hotel> messages = roomRepo.findAll();

        model.put("messages", messages);

        return "main";
    }*/

/*    @PostMapping("filter")
    public String filter(@RequestParam Long number, Map<String, Object> model) {

        if (number != null) {
            Room room = roomRepo.findByNumber(number);
            model.put("Room number ", room.getNumber());
            model.put("Number of beds ", room.getNumberOfBeds());
            model.put("Price ", room.getPrice());
        }
            return "main";
    }*/
}
