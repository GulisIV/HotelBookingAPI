package by.encata.gulis.hotel.repository;

import by.encata.gulis.hotel.domain.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.DayOfWeek;

public interface ScheduleRepo extends MongoRepository <Schedule, DayOfWeek> {

}
