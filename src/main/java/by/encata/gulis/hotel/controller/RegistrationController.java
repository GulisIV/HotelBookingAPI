package by.encata.gulis.hotel.controller;


import by.encata.gulis.hotel.domain.User;
import by.encata.gulis.hotel.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User addUser(@Valid User user) {
        userService.addUser(user);
        return user;
    }

}
