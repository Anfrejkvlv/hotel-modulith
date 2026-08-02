package dev.emma.hotelmodulith.reservations.internal;

import dev.emma.hotelmodulith.reservations.Reservation;
import dev.emma.hotelmodulith.reservations.ReservationCreatedEvent;
import dev.emma.hotelmodulith.reservations.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository repository;
    private final ApplicationEventPublisher publisher;

    /**
     * @return
     */
    @Override
    public List<Reservation> findAll() {
        return repository.findAll().stream().map(this::toApi).toList();
    }

    /**
     * @param date
     * @return
     */
    @Override
    public List<Reservation> findByDate(LocalDate date) {
        return repository.findByDate(date)
                .stream()
                .map(this::toApi)
                .toList();
    }

    /**
     * @param guestId
     * @return
     */
    @Override
    public List<Reservation> findByGuestId(long guestId) {
        return repository.findByGuestId(guestId)
                .stream()
                .map(this::toApi)
                .toList();
    }

    /**
     * @param date
     * @param guestId
     * @return
     */
    @Override
    public List<Reservation> findByDateAndGuestId(LocalDate date, long guestId) {

        return repository.findByDateAndGuestId(date,guestId)
                .stream()
                .map(this::toApi)
                .toList();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Reservation findById(long id) {
        return repository.findById(id).map(this::toApi).orElseThrow(()-> new IllegalArgumentException("Reservation Introuvable : "+id));
    }

    /**
     * @param reservation
     * @return
     */
    @Override
    public Reservation create(Reservation reservation) {
        ReservationEntity entity = new ReservationEntity();
        copy(reservation, entity);
        Reservation result=toApi(repository.save(entity));

        publisher.publishEvent(new ReservationCreatedEvent(result.getReservationId(),result.getRoomId(),result.getGuestId(),result.getDate()));

        return result;
    }

    /**
     * @param id
     * @param reservation
     * @return
     */
    @Override
    public Reservation update(long id, Reservation reservation) {
        ReservationEntity entity=repository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Reservation Introuvable : "+id));
        copy(reservation, entity);
        return toApi(repository.save(entity));
    }

    /**
     * @param id
     */
    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

    private Reservation toApi(ReservationEntity entity) {
        return Reservation.builder()
                .reservationId(entity.getReservationId())
                .guestId(entity.getGuestId())
                .roomId(entity.getRoomId())
                .date(entity.getDate()).build();
    }

    private void copy(Reservation reservation, ReservationEntity entity) {
        entity.setGuestId(reservation.getGuestId());
        entity.setRoomId(reservation.getRoomId());
        entity.setDate(reservation.getDate());

    }
}
