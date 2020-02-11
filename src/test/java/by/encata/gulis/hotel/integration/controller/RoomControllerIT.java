package by.encata.gulis.hotel.integration.controller;

import by.encata.gulis.hotel.domain.Reservation;
import by.encata.gulis.hotel.domain.Room;
import by.encata.gulis.hotel.domain.RoomBreak;
import by.encata.gulis.hotel.domain.Schedule;
import by.encata.gulis.hotel.domain.dto.ReservationDto;
import by.encata.gulis.hotel.service.RoomService;
import by.encata.gulis.hotel.service.ScheduleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
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

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@TestPropertySource("/application-test.properties")
public class RoomControllerIT {

    @Autowired
    private RoomService roomService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp(){
        roomService.deleteAllRooms();
        scheduleService.deleteAll();

        //creating room1 with room breaks
        Room room1 = new Room();
        room1.setNumber(1L);
        room1.setNumberOfBeds(1);
        room1.setPrice(BigDecimal.valueOf(100));
        roomService.addRoom(room1);

        List<RoomBreak> breaksForRoomOne = new ArrayList<>();
        breaksForRoomOne.add(new RoomBreak(DayOfWeek.MONDAY, LocalTime.of(12, 0).toString(), LocalTime.of(13, 0).toString()));
        breaksForRoomOne.add(new RoomBreak(DayOfWeek.TUESDAY, LocalTime.of(12, 0).toString(), LocalTime.of(13, 0).toString()));
        breaksForRoomOne.add(new RoomBreak(DayOfWeek.WEDNESDAY, LocalTime.of(12, 0).toString(), LocalTime.of(13, 0).toString()));
        breaksForRoomOne.add(new RoomBreak(DayOfWeek.THURSDAY, LocalTime.of(12, 0).toString(), LocalTime.of(13, 0).toString()));
        breaksForRoomOne.add(new RoomBreak(DayOfWeek.FRIDAY, LocalTime.of(14, 0).toString(), LocalTime.of(15, 0).toString()));
        roomService.setRoomBreaks(1L, breaksForRoomOne);

        //creating room2 with room breaks
        Room room2 = new Room();
        room2.setNumber(2L);
        room2.setNumberOfBeds(2);
        room2.setPrice(BigDecimal.valueOf(200));
        roomService.addRoom(room2);

        List<RoomBreak> breaksForRoomTwo = new ArrayList<>();
        breaksForRoomTwo.add(new RoomBreak(DayOfWeek.MONDAY, LocalTime.of(11, 0).toString(), LocalTime.of(12, 0).toString()));
        breaksForRoomTwo.add(new RoomBreak(DayOfWeek.TUESDAY, LocalTime.of(11, 0).toString(), LocalTime.of(12, 0).toString()));
        breaksForRoomTwo.add(new RoomBreak(DayOfWeek.WEDNESDAY, LocalTime.of(11, 0).toString(), LocalTime.of(12, 0).toString()));
        breaksForRoomTwo.add(new RoomBreak(DayOfWeek.THURSDAY, LocalTime.of(11, 0).toString(), LocalTime.of(12, 0).toString()));
        breaksForRoomTwo.add(new RoomBreak(DayOfWeek.FRIDAY, LocalTime.of(13, 0).toString(), LocalTime.of(14, 0).toString()));
        roomService.setRoomBreaks(2L, breaksForRoomTwo);

        //creating schedule for whole week
        List<Schedule> scheduleList = new ArrayList<>();
        scheduleList.add(new Schedule(DayOfWeek.MONDAY, LocalTime.of(9, 0).toString(), LocalTime.of(21, 0).toString()));
        scheduleList.add(new Schedule(DayOfWeek.TUESDAY, LocalTime.of(9, 0).toString(), LocalTime.of(21, 0).toString()));
        scheduleList.add(new Schedule(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0).toString(), LocalTime.of(21, 0).toString()));
        scheduleList.add(new Schedule(DayOfWeek.THURSDAY, LocalTime.of(9, 0).toString(), LocalTime.of(21, 0).toString()));
        scheduleList.add(new Schedule(DayOfWeek.FRIDAY, LocalTime.of(11, 0).toString(), LocalTime.of(18, 0).toString()));
        scheduleService.saveScheduleList(scheduleList);

    }

    @After
    public void cleanAfter(){
        roomService.deleteAllRooms();
        scheduleService.deleteAll();

    }

    @Test
    @WithUserDetails("hotelUser")
    public void getRoom() throws Exception {
        mockMvc.perform(get("/room/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.number").value(1L))
                .andExpect(jsonPath("$.price").value(BigDecimal.valueOf(100)));
    }

    @Test
    @WithUserDetails("hotelAdmin")
    public void addRoomNumberOne() throws Exception {
        roomService.deleteRoom(1L);

        Room room1 = new Room();
        room1.setNumber(1L);
        room1.setNumberOfBeds(1);
        room1.setPrice(BigDecimal.valueOf(100));

        String roomStr = new ObjectMapper().writeValueAsString(room1);

        mockMvc.perform(post("/room/add").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(roomStr)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.number").value(1L))
                .andExpect(jsonPath("$.numberOfBeds").value(1))
                .andExpect(jsonPath("$.price").value(BigDecimal.valueOf(100)));
    }

    @Test
    @WithUserDetails("hotelAdmin")
    public void addRoomNumberTwo() throws Exception {
        roomService.deleteRoom(2L);

        Room room2 = new Room();
        room2.setNumber(2L);
        room2.setNumberOfBeds(2);
        room2.setPrice(BigDecimal.valueOf(200));

        String roomStr = new ObjectMapper().writeValueAsString(room2);

        mockMvc.perform(post("/room/add").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(roomStr)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.number").value(2L))
                .andExpect(jsonPath("$.numberOfBeds").value(2))
                .andExpect(jsonPath("$.price").value(BigDecimal.valueOf(200)));
    }

    @Test
    @WithUserDetails("hotelAdmin")
    public void deleteRoom() throws Exception {

        mockMvc.perform(delete("/room/{number}/delete", 1L).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(String.valueOf(1L)));
    }

    @Test
    @WithUserDetails("hotelUser")
    public void getAllRooms() throws Exception {
        mockMvc.perform(get("/room/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].number").value(String.valueOf(1L)))
                .andExpect(jsonPath("$[0].numberOfBeds").value(String.valueOf(1)))
                .andExpect(jsonPath("$[1].number").value(String.valueOf(2L)))
                .andExpect(jsonPath("$[1].numberOfBeds").value(String.valueOf(2)));
    }

    @Test
    @WithUserDetails("hotelUser")
    public void addReservationForRoomOneMondaySuccess() throws Exception {
        List<RoomBreak> breaksForRoomOne = new ArrayList<>();
        breaksForRoomOne.add(new RoomBreak(DayOfWeek.MONDAY, LocalTime.of(12, 0).toString(), LocalTime.of(13, 0).toString()));
        roomService.setRoomBreaks(1L, breaksForRoomOne);

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setRoomNumber(1L);
        reservationDto.setReservation(new Reservation(DayOfWeek.of(1), LocalTime.of(10, 40).toString(), LocalTime.of(12, 0).toString()));


        String reservationStr = new ObjectMapper().writeValueAsString(reservationDto);

        mockMvc.perform(post("/room/reserve").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(reservationStr)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("hotelUser")
    public void addReservationForRoomOneMondayFail() throws Exception {
        List<RoomBreak> breaksForRoomOne = new ArrayList<>();
        breaksForRoomOne.add(new RoomBreak(DayOfWeek.MONDAY, LocalTime.of(12, 0).toString(), LocalTime.of(13, 0).toString()));
        roomService.setRoomBreaks(1L, breaksForRoomOne);

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setRoomNumber(1L);
        reservationDto.setReservation(new Reservation(DayOfWeek.of(1), LocalTime.of(8, 30).toString(), LocalTime.of(12, 0).toString()));


        String reservationStr = new ObjectMapper().writeValueAsString(reservationDto);

        mockMvc.perform(post("/room/reserve").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(reservationStr)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Hotel is not working this time!"));
    }

    @Test
    @WithUserDetails("hotelUser")
    public void addReservationForRoomOneTuesdayFail() throws Exception {
        List<RoomBreak> breaksForRoomOne = new ArrayList<>();
        breaksForRoomOne.add(new RoomBreak(DayOfWeek.MONDAY, LocalTime.of(12, 0).toString(), LocalTime.of(13, 0).toString()));
        breaksForRoomOne.add(new RoomBreak(DayOfWeek.TUESDAY, LocalTime.of(12, 0).toString(), LocalTime.of(13, 0).toString()));
        roomService.setRoomBreaks(1L, breaksForRoomOne);

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setRoomNumber(1L);
        reservationDto.setReservation(new Reservation(DayOfWeek.of(2), LocalTime.of(12, 30).toString(), LocalTime.of(14, 0).toString()));


        String reservationStr = new ObjectMapper().writeValueAsString(reservationDto);

        mockMvc.perform(post("/room/reserve").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(reservationStr)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Reservation crossing room breaks!"));
    }

    @Test
    @WithUserDetails("hotelUser")
    public void findAvailableRoomsByTimeRoomOne() throws Exception {

        String start = LocalTime.of(9, 0).toString();
        String end = LocalTime.of(12, 0).toString();

        mockMvc.perform(get("/room/search/{day}", 1)
                .param("from", start)
                .param("to", end)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].number").value(1L))
                .andExpect(jsonPath("$[0].numberOfBeds").value(1));
    }

    @Test
    @WithUserDetails("hotelUser")
    public void findAvailableRoomsByTimeRoomTwo() throws Exception {

        String start = LocalTime.of(14, 0).toString();
        String end = LocalTime.of(18, 0).toString();

        mockMvc.perform(get("/room/search/{day}", 5)
                .param("from", start)
                .param("to", end)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].number").value(2L))
                .andExpect(jsonPath("$[0].numberOfBeds").value(2));
    }

    @Test
    @WithUserDetails("hotelAdmin")
    public void setRoomOneBreaks() throws Exception {
        List<RoomBreak> breaksForRoomOne = new ArrayList<>();
        breaksForRoomOne.add(new RoomBreak(DayOfWeek.MONDAY, LocalTime.of(12, 0).toString(), LocalTime.of(13, 0).toString()));
        breaksForRoomOne.add(new RoomBreak(DayOfWeek.TUESDAY, LocalTime.of(12, 0).toString(), LocalTime.of(13, 0).toString()));
        breaksForRoomOne.add(new RoomBreak(DayOfWeek.WEDNESDAY, LocalTime.of(12, 0).toString(), LocalTime.of(13, 0).toString()));
        breaksForRoomOne.add(new RoomBreak(DayOfWeek.THURSDAY, LocalTime.of(12, 0).toString(), LocalTime.of(13, 0).toString()));
        breaksForRoomOne.add(new RoomBreak(DayOfWeek.FRIDAY, LocalTime.of(14, 0).toString(), LocalTime.of(15, 0).toString()));

        String breakForRoomOneStr = new ObjectMapper().writeValueAsString(breaksForRoomOne);

        mockMvc.perform(post("/room/{number}/breaks", 1L).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(breakForRoomOneStr)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(breakForRoomOneStr));
    }

    @Test
    @WithUserDetails("hotelAdmin")
    public void addRoomTwoBreaks() throws Exception {
        List<RoomBreak> breaksForRoomTwo = new ArrayList<>();
        breaksForRoomTwo.add(new RoomBreak(DayOfWeek.MONDAY, LocalTime.of(11, 0).toString(), LocalTime.of(12, 0).toString()));
        breaksForRoomTwo.add(new RoomBreak(DayOfWeek.TUESDAY, LocalTime.of(11, 0).toString(), LocalTime.of(12, 0).toString()));
        breaksForRoomTwo.add(new RoomBreak(DayOfWeek.WEDNESDAY, LocalTime.of(11, 0).toString(), LocalTime.of(12, 0).toString()));
        breaksForRoomTwo.add(new RoomBreak(DayOfWeek.THURSDAY, LocalTime.of(11, 0).toString(), LocalTime.of(12, 0).toString()));
        breaksForRoomTwo.add(new RoomBreak(DayOfWeek.FRIDAY, LocalTime.of(13, 0).toString(), LocalTime.of(14, 0).toString()));

        String breakForRoomTwoStr = new ObjectMapper().writeValueAsString(breaksForRoomTwo);

        mockMvc.perform(post("/room/{number}/breaks", 2L).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(breakForRoomTwoStr)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(breakForRoomTwoStr));
    }

}