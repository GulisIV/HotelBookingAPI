package by.encata.gulis.hotel.domain.dto;

import by.encata.gulis.hotel.domain.Reservation;

import javax.validation.constraints.NotBlank;
import java.time.ZoneId;

public class ReservationDto {
    @NotBlank
    private Long roomNumber;
    @NotBlank
    private String userId;
    @NotBlank
    private Reservation reservation;


    public ReservationDto() {
    }

    public ReservationDto(Long roomNumber, String userId, Reservation reservation, ZoneId userZoneId) {
        this.roomNumber = roomNumber;
        this.userId = userId;
        this.reservation = reservation;
    }

    public Long getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Long roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

}
