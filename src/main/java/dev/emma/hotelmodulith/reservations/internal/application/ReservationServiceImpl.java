package dev.emma.hotelmodulith.reservations.internal.application;

import dev.emma.hotelmodulith.reservations.Reservation;
import dev.emma.hotelmodulith.reservations.ReservationCreatedEvent;
import dev.emma.hotelmodulith.reservations.ReservationService;
import dev.emma.hotelmodulith.reservations.dto.ReservationRequest;
import dev.emma.hotelmodulith.reservations.dto.ReservationResponse;
import dev.emma.hotelmodulith.reservations.exceptions.ReservationNotFoundException;
import dev.emma.hotelmodulith.reservations.internal.api.mapper.ReservationMapper;
import dev.emma.hotelmodulith.reservations.internal.persistence.ReservationEntity;
import dev.emma.hotelmodulith.reservations.internal.persistence.ReservationRepository;
import dev.emma.hotelmodulith.rooms.exceptions.RoomNotFoundExceptions;
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
    private final ReservationMapper mapper;

    /**
     * @return
     */
    @Override
    public List<ReservationResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    /**
     * @param date
     * @return
     */
    @Override
    public List<ReservationResponse> findByDate(LocalDate date) {
        return repository.findByDate(date)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    /**
     * @param guestId
     * @return
     */
    @Override
    public List<ReservationResponse> findByGuestId(long guestId) {
        return repository.findByGuestId(guestId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    /**
     * @param date
     * @param guestId
     * @return
     */
    @Override
    public List<ReservationResponse> findByDateAndGuestId(LocalDate date, long guestId) {

        return repository.findByDateAndGuestId(date,guestId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public ReservationResponse findById(long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(()-> new ReservationNotFoundException(id));
    }

    /**
     * @param reservation
     * @return
     */
    @Override
    public ReservationResponse create(ReservationRequest request) {

        if (repository.existsByRoomId(request.roomId())){
            throw new IllegalStateException("Une reservation existe deja pour cette chambre : "+request.roomId());
        }

        ReservationEntity saved = repository.save(mapper.toEntity(request));

        publisher.publishEvent(new ReservationCreatedEvent(saved.getReservationId(),saved.getRoomId(),saved.getGuestId(),saved.getDate()));

        return mapper.toResponse(saved);
    }

    /**
     * @param id
     * @param reservation
     * @return
     */
    @Override
    public ReservationResponse update(long id, ReservationRequest request) {
        ReservationEntity entity=repository.findById(id)
                .orElseThrow(()-> new ReservationNotFoundException(id));
        mapper.updateEntity(request,entity);
        return mapper.toResponse(entity);
    }

    /**
     * @param id
     */
    @Override
    public void delete(long id) {
        if (!repository.existsById(id)){
            throw new ReservationNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
