package dev.emma.hotelmodulith.reservations;

import java.time.LocalDate;

/**
 * Second acteur réagissant à la creation d'une reservation
 * Cela pour demontrer le mecanisme event-driven de Modulith sur un cas plausible
 * @param reservationId
 * @param roomId
 * @param guestId
 * @param date
 */
public record ReservationCreatedEvent(long reservationId,long roomId, long guestId, LocalDate date) {
}
