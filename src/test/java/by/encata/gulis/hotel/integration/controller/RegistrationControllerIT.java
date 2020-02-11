package by.encata.gulis.hotel.integration.controller;

import by.encata.gulis.hotel.domain.Role;
import by.encata.gulis.hotel.domain.User;
import by.encata.gulis.hotel.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@TestPropertySource("/application-test.properties")
public class RegistrationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @After
    public void cleanUser(){
        userService.deleteUserByUsername("hotelUserTest");
    }

    @Test
    public void addUserTest() throws Exception {
        User user = new User();
        user.setUsername("hotelUserTest");
        user.setPassword("123");

        String userStr = new ObjectMapper().writeValueAsString(user);

        mockMvc.perform(post("/registration").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(userStr)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("hotelUserTest"))
                .andExpect(jsonPath("$.roles").value(Role.USER.toString()));
    }

}