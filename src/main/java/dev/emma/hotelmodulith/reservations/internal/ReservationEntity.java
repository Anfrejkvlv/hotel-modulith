package dev.emma.hotelmodulith.reservations.internal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "RESERVATIONS")
@Getter @Setter @NoArgsConstructor
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVATION_ID")
    private long reservationId;

    @JoinColumn(name = "ROOM_ID", nullable = false)
    private long roomId;

    @JoinColumn(name = "GUEST_ID", nullable = false)
    private long guestId;

    @Column(name = "RES_DATE")
    private LocalDate date;
}
