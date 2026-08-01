package dev.emma.hotelmodulith.reservations;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
    List<Reservation> findAll();
    List<Reservation> findByDate(LocalDate date);
    List<Reservation> findByGuestId(long guestId);
    List<Reservation> findByDateAndGuestId(LocalDate date, long guestId);
    Reservation findById(long id);
    Reservation create(Reservation reservation);
    Reservation update(long id,Reservation reservation);
    void delete(long id);
}
