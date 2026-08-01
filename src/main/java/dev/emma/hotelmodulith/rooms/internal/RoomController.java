package dev.emma.hotelmodulith.rooms.internal;

import dev.emma.hotelmodulith.rooms.Room;
import dev.emma.hotelmodulith.rooms.RoomService;
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
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Room>> getRooms() {
        return ResponseEntity.ok(roomService.getRooms());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Room> getRoom(@PathVariable long id) {
        return ResponseEntity.ok(roomService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Room> addRoom(@RequestBody Room room) {
        return ResponseEntity.ok(roomService.create(room));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Room> updateRoom(@PathVariable long id, @RequestBody Room room) {
        return ResponseEntity.ok(roomService.update(room, id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoom(@PathVariable long id) {
        roomService.delete(id);
    }
}
