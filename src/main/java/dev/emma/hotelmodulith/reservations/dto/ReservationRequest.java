package dev.emma.hotelmodulith.reservations.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ReservationRequest(
       @NotBlank long roomId,
       @NotBlank long guestId,
       @NotBlank LocalDate date
) {}
