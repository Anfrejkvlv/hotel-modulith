package dev.emma.hotelmodulith.roomreservations.internal;

import dev.emma.hotelmodulith.guests.Guest;
import dev.emma.hotelmodulith.guests.GuestService;
import dev.emma.hotelmodulith.reservations.Reservation;
import dev.emma.hotelmodulith.reservations.ReservationService;
import dev.emma.hotelmodulith.roomreservations.RoomReservation;
import dev.emma.hotelmodulith.rooms.Room;
import dev.emma.hotelmodulith.rooms.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/roomReservations")
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
public class RoomReservationController {

    private final GuestService guestService;
    private final RoomService roomService;
    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<Collection<RoomReservation>> getRoomReservations(@RequestParam(required = false) String date){
        LocalDate requestedDate = StringUtils.hasLength(date) ?  LocalDate.parse(date) : LocalDate.now();

        List<Room> rooms=roomService.getRooms();

        Map<Long,RoomReservation> roomReservations=new HashMap<>();

        rooms.forEach(room->{
            RoomReservation rr=RoomReservation.builder()
                    .roomId(room.getRoomId())
                    .roomNumber(room.getRoomNumber())
                    .name(room.getName())
                    .bedInfo(room.getBedInfo())
                    .date(requestedDate).build();
            roomReservations.put(room.getRoomId(), rr);
        });

        List<Reservation> reservations= reservationService.findAll();

        Set<Long> guestIds=reservations
                .stream()
                .map(Reservation::getGuestId)
                .collect(Collectors.toSet());
        Map<Long, Guest> guestById=guestService.findByIds(guestIds)
                .stream()
                .collect(Collectors.toMap(Guest::getGuestId,g->g));

        reservations.forEach(reservation->{

            RoomReservation rr=roomReservations.get(reservation.getRoomId());

            if(rr==null) return;

            rr.setReservationId(reservation.getReservationId());
            rr.setGuestId(reservation.getGuestId());
            rr.setDate(reservation.getDate());

            Guest guest=guestById.get(reservation.getGuestId());

            if(guest!=null){
                rr.setFirstName(guest.getFirstName());
                rr.setLastName(guest.getLastName());
            }
        });
        return ResponseEntity.ok(roomReservations.values());
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<Room>> getRooms(){
        return ResponseEntity.ok(roomService.getRooms());
    }

    @GetMapping("/reservations")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Reservation>> getReservations(
            @RequestParam(required = false) Long guestId,
            @RequestParam(required = false) LocalDate date) {
        if (date !=null && guestId != null) return ResponseEntity.ok(reservationService.findByDateAndGuestId(date, guestId));
        if (date !=null) return ResponseEntity.ok(reservationService.findByDate(date));
        if (guestId != null) return ResponseEntity.ok(reservationService.findByGuestId(guestId));
        return ResponseEntity.ok(reservationService.findAll());
    }

    @GetMapping("/guests")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Guest>> getGuests(){
        return ResponseEntity.ok(guestService.findAll(null));
    }

    @PostMapping("/reservations")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation){
        return  ResponseEntity.ok(reservationService.create(reservation));
    }

    @PostMapping("/rooms")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Room> createRoom(@RequestBody Room room){
        return  ResponseEntity.ok(roomService.create(room));
    }

    @PostMapping("/guests")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Guest> createGuest(@RequestBody Guest guest){
        return  ResponseEntity.ok(guestService.create(guest));
    }

    @GetMapping("/reservations/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Reservation> getReservation(@PathVariable long id) {
        return ResponseEntity.ok(reservationService.findById(id));
    }

    @GetMapping("/rooms/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Room> getRoom(@PathVariable long id) {
        return ResponseEntity.ok(roomService.getById(id));
    }

    @GetMapping("/guests/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Guest> getGuest(@PathVariable long id) {

        return ResponseEntity.ok(guestService.findById(id));
    }

    @PutMapping("/reservations/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Reservation> updateReservation(@PathVariable long id, @RequestBody Reservation reservation) {
        return ResponseEntity.ok(reservationService.update(id, reservation));
    }


    @PutMapping("/rooms/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Room> updateRoom(@PathVariable long id, @RequestBody Room room) {
        return ResponseEntity.ok(roomService.update(room, id));
    }

    @PutMapping("/guests/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Guest> update(@PathVariable long id, @RequestBody Guest guest) {
        return ResponseEntity.ok(guestService.update(id, guest));
    }

    @DeleteMapping("/guests/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteGuest(@PathVariable long id) {
        guestService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteRoom(@PathVariable long id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
