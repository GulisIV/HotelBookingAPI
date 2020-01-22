package by.encata.gulis.hotel.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Set;

@Document(collection = "rooms")
public class Room {

    @Id
    private String roomId;

    private Long number;

//    @NotBlank(message = "You need to place some beds")
    private Byte numberOfBeds;
//    @NotBlank(message = "You need to take some money for it")
    private BigDecimal price;

    private Set<Reservation> reservations;

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Byte getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(Byte numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }


}
