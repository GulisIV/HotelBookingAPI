package by.encata.gulis.hotel.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "rooms")
public class Room {

    @Id
    private Long number;

    @NotNull
    private Integer numberOfBeds;

    @NotNull
    private BigDecimal price;

    private List<Reservation> reservations = new ArrayList<>();

    private List<RoomBreak> roomBreaks = new ArrayList<>();

    public Room() {
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Integer getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(Integer numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<RoomBreak> getRoomBreaks() {
        return roomBreaks;
    }

    public void setRoomBreaks(List<RoomBreak> roomBreaks) {
        this.roomBreaks = roomBreaks;
    }
}
