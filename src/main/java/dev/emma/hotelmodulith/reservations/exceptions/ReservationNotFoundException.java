package dev.emma.hotelmodulith.reservations.exceptions;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(long reservationId) {
        super("Reservation Introuvable : "+reservationId);
    }
}
