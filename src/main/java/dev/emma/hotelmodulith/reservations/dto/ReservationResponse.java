package dev.emma.hotelmodulith.reservations.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ReservationResponse(
        long reservationId,
        long roomId,
        long guestId,
        LocalDate date
) {}
