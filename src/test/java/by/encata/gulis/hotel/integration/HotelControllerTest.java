package by.encata.gulis.hotel.integration;

import by.encata.gulis.hotel.domain.Schedule;
import by.encata.gulis.hotel.service.ScheduleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@DataMongoTest
//@ExtendWith(SpringExtension.class)

@RunWith(SpringRunner.class)
//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@ContextConfiguration
//@WithUserDetails(value = "qwe")

public class HotelControllerTest {

//    @Autowired
//    private HotelController hotelController;
//
//    @Autowired
//    private MongoTemplate mongoTemplate;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private MockMvc mockMvc;


    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    @WithMockUser(username = "qwe")
    public void testSetHotelWorkTimeByDay() throws Exception {
        Schedule schedule = new Schedule();
        schedule.setDay(DayOfWeek.MONDAY);
        schedule.setOpenTime(LocalTime.of(9, 0));
        schedule.setCloseTime(LocalTime.of(21, 0));

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(schedule);

        mockMvc.perform(post("/hotel/schedule/set").contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(requestJson));
    }

    @Before
    public void setSchedule(){
        Schedule schedule = new Schedule();
        schedule.setDay(DayOfWeek.MONDAY);
        schedule.setOpenTime(LocalTime.of(9, 0));
        schedule.setCloseTime(LocalTime.of(21, 0));
        scheduleService.saveSchedule(schedule);
    }

    @Test
    @WithMockUser(username = "qwe", authorities = "ADMIN")
    public void testGetHotelSchedule() throws Exception{
        Schedule schedule = new Schedule();
        schedule.setDay(DayOfWeek.MONDAY);
        schedule.setOpenTime(LocalTime.of(9, 0));
        schedule.setCloseTime(LocalTime.of(21, 0));

        String scheduleStr = new ObjectMapper().writeValueAsString(schedule);

//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
//        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
//        String requestJson=ow.writeValueAsString(schedule);

        mockMvc.perform(get("/hotel/schedule").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(scheduleStr));
    }

//    @Test
//    public void setHotelWorkTimeByDay() throws Exception {
//        /*Schedule schedule = new Schedule();
//        schedule.setDay(DayOfWeek.MONDAY);
//        schedule.setOpenTime(LocalTime.of(8,0));
//        schedule.setCloseTime(LocalTime.of(20, 30));
//        schedule.setId(1L);
//
//
//        mongoTemplate.save(schedule, "schedule");
//
//        *//*        Assert.assertThat(mongoTemplate.findAll(Schedule.class, "schedule")*/
//
//
// /*       this.mockMvc.perform(get("/hotel/schedule/monday"))
//                .andExpect(status().isOk());
//
//        this.mockMvc.perform(post("/hotel/schedule/monday").contentType(MediaType.APPLICATION_JSON)
//                .content("{\"username":"qwe","password":"$2a$08$uHu9c7Ur.2twWCRZEntd8ey/LWzaQAb7Txes3Fg6SmUqdrB5FRfai",
//                "role":"USER"
//    }"))
//                .andExpect(status().isOk());
//*/
//        /*// given
//        DBObject objectToSave = BasicDBObjectBuilder.start()
//                .add("key", "value")
//                .get();
//
//        // when
//        mongoTemplate.save(objectToSave, "collection");
//
//        // then
//        assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("key")
//                .containsOnly("value");*/
//
////        Schedule schedule = new Schedule();
////        schedule.setDay(DayOfWeek.MONDAY);
////        schedule.setOpenTime(LocalTime.of(8,0));
////        schedule.setCloseTime(LocalTime.of(20, 30));
//
///*
//        DBObject objectToSave = BasicDBObjectBuilder.start()
//                .add("key", "value")
//                .get();
//
//        // when
//        mongoTemplate.save(objectToSave, "collection");
//
//        // then
//        assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("key")
//                .containsOnly("value");
//
//*/
//
//
//    }


//    @Autowired
//private WebApplicationContext context;
//    private MockMvc mvc;
//
//    @Before
//    public void setup() {
//        mvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//    }
}