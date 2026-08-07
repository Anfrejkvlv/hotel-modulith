package dev.emma.hotelmodulith.reservations;

import dev.emma.hotelmodulith.reservations.dto.ReservationRequest;
import dev.emma.hotelmodulith.reservations.dto.ReservationResponse;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
    List<ReservationResponse> findAll();
    List<ReservationResponse> findByDate(LocalDate date);
    List<ReservationResponse> findByGuestId(long guestId);
    List<ReservationResponse> findByDateAndGuestId(LocalDate date, long guestId);
    ReservationResponse findById(long id);
    ReservationResponse create(ReservationRequest request);
    ReservationResponse update(long id, ReservationRequest request);
    void delete(long id);
}
