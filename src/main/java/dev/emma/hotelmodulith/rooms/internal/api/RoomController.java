package dev.emma.hotelmodulith.rooms.internal.api;

import dev.emma.hotelmodulith.rooms.RoomService;
import dev.emma.hotelmodulith.rooms.dto.RoomRequest;
import dev.emma.hotelmodulith.rooms.dto.RoomResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/rooms")
@RequiredArgsConstructor
class RoomController {
    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomResponse>> getRooms() {
        return ResponseEntity.ok(roomService.getRooms());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RoomResponse> getRoom(@PathVariable long id) {
        return ResponseEntity.ok(roomService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RoomResponse> addRoom(@RequestBody @Valid RoomRequest request) {
        RoomResponse created=roomService.create(request);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable long id, @RequestBody @Valid RoomRequest request) {
        return ResponseEntity.ok(roomService.update(request, id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoom(@PathVariable long id) {
        roomService.delete(id);
    }
}
