package dev.emma.hotelmodulith.guests.internal;

import dev.emma.hotelmodulith.guests.Guest;
import dev.emma.hotelmodulith.guests.GuestService;
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
    public ResponseEntity<List<Guest>> getGuests(@RequestParam(required = false) String emailAddress) {
        return ResponseEntity.ok(guestService.findAll(emailAddress));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Guest> create(@RequestBody Guest guest) {
        return ResponseEntity.ok(guestService.create(guest));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Guest> getGuest(@PathVariable long id) {
        return ResponseEntity.ok(guestService.findById(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Guest> update(@PathVariable long id, @RequestBody Guest guest) {
        return ResponseEntity.ok(guestService.update(id, guest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable long id) {

        guestService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
