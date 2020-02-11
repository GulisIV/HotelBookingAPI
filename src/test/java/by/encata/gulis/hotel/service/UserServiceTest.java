package by.encata.gulis.hotel.service;

import by.encata.gulis.hotel.domain.Role;
import by.encata.gulis.hotel.domain.User;
import by.encata.gulis.hotel.exception.user.UserExistsException;
import by.encata.gulis.hotel.repository.UserRepo;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void addUserTest() {

        User user = new User();

        userService.addUser(user);

        Assert.assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));
        Mockito.verify(userRepo, Mockito.times(1)).save(user);
    }

    @Test(expected = UserExistsException.class)
    public void addUserFailTest() {
        User user = new User();
        user.setUsername("Ilya");

        Mockito.doReturn(new User())
                .when(userRepo)
                .findByUsername("Ilya");

        userService.addUser(user);

        Assert.assertFalse(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));
        Mockito.verify(userRepo, Mockito.times(0)).save(ArgumentMatchers.any(User.class));

    }

    @Test
    public void findAllUsersTest(){
        userService.findAll();

        Mockito.verify(userRepo, Mockito.times(1)).findAll();
    }

    @Test
    public void deleteUserByUsernameTest() {

        String name = "Ilya";

        Mockito.doReturn(new User())
                .when(userRepo)
                .findByUsername(name);

        userService.deleteUserByUsername(name);

        Mockito.verify(userRepo, Mockito.times(1)).deleteUserByUsername(name);
    }
}