package dev.emma.hotelmodulith.reservations.internal.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    List<ReservationEntity> findByGuestId(long guestId);
    List<ReservationEntity> findByDate(LocalDate date);
    List<ReservationEntity> findByDateAndGuestId(LocalDate date, long guestId);
    boolean existsByRoomId(long roomId);
}
