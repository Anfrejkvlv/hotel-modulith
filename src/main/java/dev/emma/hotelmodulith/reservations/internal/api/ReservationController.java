package dev.emma.hotelmodulith.reservations.internal.api;

import dev.emma.hotelmodulith.reservations.ReservationService;
import dev.emma.hotelmodulith.reservations.dto.ReservationRequest;
import dev.emma.hotelmodulith.reservations.dto.ReservationResponse;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<ReservationResponse>> getReservations(
            @RequestParam(required = false) Long guestId,
            @RequestParam(required = false) LocalDate date) {
        if (date !=null && guestId != null) return ResponseEntity.ok(reservationService.findByDateAndGuestId(date, guestId));
        if (date !=null) return ResponseEntity.ok(reservationService.findByDate(date));
        if (guestId != null) return ResponseEntity.ok(reservationService.findByGuestId(guestId));
        return ResponseEntity.ok(reservationService.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody @Valid ReservationRequest request) {
        return ResponseEntity.ok(reservationService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponse> updateReservation(@PathVariable long id, @Valid @RequestBody ReservationRequest request) {
        return ResponseEntity.ok(reservationService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
