package by.encata.gulis.hotel.integration.controller;

import by.encata.gulis.hotel.domain.Schedule;
import by.encata.gulis.hotel.repository.UserRepo;
import by.encata.gulis.hotel.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@DataMongoTest
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HotelControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @Before
    public void setUp(){

    }


//    @After
//    public void clean(){
//        EntitiesForTestCases entities = new EntitiesForTestCases();
//        entities.clearDb();
//    }

    @Test
    @WithUserDetails("hotelAdminTest")
    public void testSetHotelScheduleByDayMonday() throws Exception {
        Schedule schedule = new Schedule();
        schedule.setDay(DayOfWeek.MONDAY);
        schedule.setOpenTime(LocalTime.of(9, 0).toString());
        schedule.setCloseTime(LocalTime.of(21, 0).toString());

        String scheduleStr = new ObjectMapper().writeValueAsString(schedule);

        mockMvc.perform(post("/hotel/schedule/set").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(scheduleStr))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.day").value(DayOfWeek.MONDAY.toString()));
    }

    @Test
    @WithUserDetails("hotelUser")
    public void testGetHotelScheduleMonday() throws Exception {
        mockMvc.perform(get("/hotel/schedule/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.day").value(DayOfWeek.MONDAY.toString()))
                .andExpect(jsonPath("$.openTime").value(LocalTime.of(9, 0).toString()));
    }

    @Test
    @WithUserDetails("hotelAdmin")
    public void testSetHotelScheduleByWeek() throws Exception {
        List<Schedule> scheduleList = new ArrayList<>();
        scheduleList.add(new Schedule(DayOfWeek.MONDAY, LocalTime.of(9, 0).toString(), LocalTime.of(21, 0).toString()));
        scheduleList.add(new Schedule(DayOfWeek.TUESDAY, LocalTime.of(9, 0).toString(), LocalTime.of(21, 0).toString()));
        scheduleList.add(new Schedule(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0).toString(), LocalTime.of(21, 0).toString()));
        scheduleList.add(new Schedule(DayOfWeek.THURSDAY, LocalTime.of(9, 0).toString(), LocalTime.of(21, 0).toString()));
        scheduleList.add(new Schedule(DayOfWeek.FRIDAY, LocalTime.of(11, 0).toString(), LocalTime.of(18, 0).toString()));

        String scheduleStr = new ObjectMapper().writeValueAsString(scheduleList);

        mockMvc.perform(post("/hotel/schedule/set_week").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(scheduleStr)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(scheduleStr));
    }

    @Test
    @WithUserDetails("hotelUser")
    public void testGetHotelSchedule() throws Exception {
        mockMvc.perform(get("/hotel/schedule")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].day").value(DayOfWeek.MONDAY.toString()))
                .andExpect(jsonPath("$[0].openTime").value(LocalTime.of(9, 0).toString()))
                .andExpect(jsonPath("$[2].day").value(DayOfWeek.WEDNESDAY.toString()))
                .andExpect(jsonPath("$[2].openTime").value(LocalTime.of(9, 0).toString()))
                .andExpect(jsonPath("$[4].day").value(DayOfWeek.FRIDAY.toString()))
                .andExpect(jsonPath("$[4].openTime").value(LocalTime.of(11, 0).toString()));
    }

}
