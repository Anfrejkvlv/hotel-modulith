package dev.emma.hotelmodulith.reservations.internal;

import dev.emma.hotelmodulith.reservations.Reservation;
import dev.emma.hotelmodulith.reservations.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v2/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Reservation>> getReservations(
            @RequestParam(required = false) Long guestId,
            @RequestParam(required = false) LocalDate date) {
        if (date !=null && guestId != null) return ResponseEntity.ok(reservationService.findByDateAndGuestId(date, guestId));
        if (date !=null) return ResponseEntity.ok(reservationService.findByDate(date));
        if (guestId != null) return ResponseEntity.ok(reservationService.findByGuestId(guestId));
        return ResponseEntity.ok(reservationService.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation) {
        return ResponseEntity.ok(reservationService.create(reservation));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Reservation> updateReservation(@PathVariable long id, @RequestBody Reservation reservation) {
        return ResponseEntity.ok(reservationService.update(id, reservation));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
