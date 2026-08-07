package dev.emma.hotelmodulith.roomreservations.internal;

import dev.emma.hotelmodulith.guests.GuestService;
import dev.emma.hotelmodulith.guests.dto.GuestRequest;
import dev.emma.hotelmodulith.guests.dto.GuestResponse;
import dev.emma.hotelmodulith.reservations.ReservationService;
import dev.emma.hotelmodulith.reservations.dto.ReservationRequest;
import dev.emma.hotelmodulith.reservations.dto.ReservationResponse;
import dev.emma.hotelmodulith.roomreservations.RoomReservation;
import dev.emma.hotelmodulith.rooms.RoomService;
import dev.emma.hotelmodulith.rooms.dto.RoomRequest;
import dev.emma.hotelmodulith.rooms.dto.RoomResponse;
import jakarta.validation.Valid;
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

        List<RoomResponse> rooms=roomService.getRooms();

        Map<Long,RoomReservation> roomReservations=new HashMap<>();

        rooms.forEach(room->{
            RoomReservation rr=RoomReservation.builder()
                    .roomId(room.roomId())
                    .roomNumber(room.roomNumber())
                    .name(room.name())
                    .bedInfo(room.bedInfo())
                    .date(requestedDate).build();
            roomReservations.put(room.roomId(), rr);
        });

        List<ReservationResponse> reservations= reservationService.findAll();

        Set<Long> guestIds=reservations
                .stream()
                .map(ReservationResponse::guestId)
                .collect(Collectors.toSet());
        Map<Long, GuestResponse> guestById=guestService.findByIds(guestIds)
                .stream()
                .collect(Collectors.toMap(GuestResponse::guestId,g->g));

        reservations.forEach(reservation->{

            RoomReservation rr=roomReservations.get(reservation.roomId());

            if(rr==null) return;

            rr.setReservationId(reservation.reservationId());
            rr.setGuestId(reservation.guestId());
            rr.setDate(reservation.date());

            GuestResponse guest=guestById.get(reservation.guestId());

            if(guest!=null){
                rr.setFirstName(guest.firstName());
                rr.setLastName(guest.lastName());
            }
        });
        return ResponseEntity.ok(roomReservations.values());
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<RoomResponse>> getRooms(){
        return ResponseEntity.ok(roomService.getRooms());
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservations(
            @RequestParam(required = false) Long guestId,
            @RequestParam(required = false) LocalDate date) {
        if (date !=null && guestId != null) return ResponseEntity.ok(reservationService.findByDateAndGuestId(date, guestId));
        if (date !=null) return ResponseEntity.ok(reservationService.findByDate(date));
        if (guestId != null) return ResponseEntity.ok(reservationService.findByGuestId(guestId));
        return ResponseEntity.ok(reservationService.findAll());
    }

    @GetMapping("/guests")
    public ResponseEntity<List<GuestResponse>> getGuests(){
        return ResponseEntity.ok(guestService.findAll(null));
    }

    @PostMapping("/reservations")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody @Valid ReservationRequest request){
        return  ResponseEntity.ok(reservationService.create(request));
    }

    @PostMapping("/rooms")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RoomResponse> createRoom(@RequestBody RoomRequest request){
        return  ResponseEntity.ok(roomService.create(request));
    }

    @PostMapping("/guests")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GuestResponse> createGuest(@RequestBody GuestRequest request){
        return  ResponseEntity.ok(guestService.create(request));
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<ReservationResponse> getReservation(@PathVariable long id) {
        return ResponseEntity.ok(reservationService.findById(id));
    }

    @GetMapping("/rooms/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RoomResponse> getRoom(@PathVariable long id) {
        return ResponseEntity.ok(roomService.getById(id));
    }

    @GetMapping("/guests/{id}")
    public ResponseEntity<GuestResponse> getGuest(@PathVariable long id) {

        return ResponseEntity.ok(guestService.findById(id));
    }

    @PutMapping("/reservations/{id}")
    public ResponseEntity<ReservationResponse> updateReservation(@PathVariable long id, @RequestBody @Valid ReservationRequest request) {
        return ResponseEntity.ok(reservationService.update(id, request));
    }


    @PutMapping("/rooms/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable long id, @RequestBody RoomRequest request) {
        return ResponseEntity.ok(roomService.update(request, id));
    }

    @PutMapping("/guests/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GuestResponse> update(@PathVariable long id, @RequestBody GuestRequest request) {
        return ResponseEntity.ok(guestService.update(id, request));
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
