package dev.emma.hotelmodulith.reservations.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    List<ReservationEntity> findByGuestId(long guestId);
    List<ReservationEntity> findByDate(LocalDate date);
    List<ReservationEntity> findByDateAndGuestId(LocalDate date, long guestId);
}
