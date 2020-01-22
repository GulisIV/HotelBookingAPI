package by.encata.gulis.hotel.domain.dto;

import by.encata.gulis.hotel.domain.Reservation;

import java.time.ZoneId;

public class ReservationDto {

    private Long roomNumber;
    private Long userId;
    private Reservation reservation;
    private ZoneId userZoneId;

    public ReservationDto() {
    }

    public ReservationDto(Long roomNumber, Long userId, Reservation reservation, ZoneId userZoneId) {
        this.roomNumber = roomNumber;
        this.userId = userId;
        this.reservation = reservation;
        this.userZoneId = userZoneId;
    }

    public Long getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Long roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public ZoneId getUserZoneId() {
        return userZoneId;
    }

    public void setUserZoneId(ZoneId userZoneId) {
        this.userZoneId = userZoneId;
    }
}
