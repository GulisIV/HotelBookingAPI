package by.encata.gulis.hotel.integration;

import by.encata.gulis.hotel.domain.Room;
import by.encata.gulis.hotel.service.RoomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RoomControllerIntegrationTest {

    @Autowired
    private RoomService roomService;

    @Autowired
    private MockMvc mockMvc;

//    @Before
//    public void createRooms(){
//        Room room1 = new Room();
//        room1.setNumber(1L);
//        room1.setNumberOfBeds(1);
//        room1.setPrice(BigDecimal.valueOf(100));
//        roomService.addRoom(room1);
//
//        Room room2 = new Room();
//        room2.setNumber(2L);
//        room2.setNumberOfBeds(2);
//        room2.setPrice(BigDecimal.valueOf(200));
//        roomService.addRoom(room2);
//    }

    @Test
    @WithMockUser(username = "qwe")
    public void getRoom() throws Exception {
        mockMvc.perform(get("/room/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.number").value(1L))
                .andExpect(jsonPath("$.price").value(BigDecimal.valueOf(100)));
    }

    @Test
    @WithMockUser(username = "qwe")
    public void addRoom() throws Exception {
        Room room3 = new Room();
        room3.setNumber(3L);
        room3.setNumberOfBeds(3);
        room3.setPrice(BigDecimal.valueOf(300));


        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(room3);


        mockMvc.perform(post("/room/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.number").value(3L))
                .andExpect(jsonPath("$.price").value(BigDecimal.valueOf(300)));
    }

/*    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

    @Test
    public void deleteRoom() {
    }

    @Test
    public void getAllRooms() {
    }

    @Test
    public void findAvailableRoomsByTime() {
    }

    @Test
    public void addReservation() {
    }
}