package dev.emma.hotelmodulith.guests.internal.api;

import dev.emma.hotelmodulith.guests.GuestService;
import dev.emma.hotelmodulith.guests.dto.GuestRequest;
import dev.emma.hotelmodulith.guests.dto.GuestResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/guests")
@RequiredArgsConstructor
class GuestController {

    private final GuestService guestService;

    @GetMapping
    public ResponseEntity<List<GuestResponse>> getGuests(@RequestParam(required = false) String emailAddress) {
        return ResponseEntity.ok(guestService.findAll(emailAddress));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GuestResponse> create(@Valid @RequestBody GuestRequest request) {
        return ResponseEntity.ok(guestService.create(request));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GuestResponse> getGuest(@PathVariable long id) {
        return ResponseEntity.ok(guestService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GuestResponse> update(@PathVariable long id, @Valid @RequestBody GuestRequest request) {
        return ResponseEntity.ok(guestService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable long id) {

        guestService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
