package by.encata.gulis.hotel.domain.dto;

import by.encata.gulis.hotel.domain.Reservation;

import javax.validation.constraints.NotNull;

public class ReservationDto {

    @NotNull
    private Long roomNumber;

    @NotNull
    private Reservation reservation;


    public ReservationDto() {
    }

    public ReservationDto(Long roomNumber, Reservation reservation) {
        this.roomNumber = roomNumber;
        this.reservation = reservation;
    }

    public Long getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Long roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

}
