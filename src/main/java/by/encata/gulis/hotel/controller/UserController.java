package by.encata.gulis.hotel.controller;


import by.encata.gulis.hotel.domain.User;
import by.encata.gulis.hotel.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class UserController {

/*    private final UserRepo userRepo;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }*/
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String registration() {
        return "registration";
    }

    @PostMapping
    /*
     * Change Map<> model to some object ErrorMessage etc.
     */
/*    public String addUser(@Valid User user, Map<String, Object> model) {

        if(!userService.saveUser(user)){
            model.put("message", "User exists!");
            return "registration";
        }

        return "redirect:/login";*/
       /* User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.put("message", "User exists!");
            return "registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);

        return "redirect:/login";
    }*/
    public void addUser(@Valid User user) {
        userService.saveUser(user);
    }


}
