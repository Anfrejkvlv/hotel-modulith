package dev.emma.hotelmodulith.reservations.internal.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "RESERVATIONS")
@Getter @Setter @NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
@Builder
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVATION_ID")
    private long reservationId;

    @JoinColumn(name = "ROOM_ID", nullable = false)
    private long roomId;

    @JoinColumn(name = "GUEST_ID", nullable = false)
    private long guestId;

    @Column(name = "RES_DATE",  nullable = false)
    private LocalDate date;
}
