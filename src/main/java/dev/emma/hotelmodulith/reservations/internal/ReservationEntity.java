package dev.emma.hotelmodulith.reservations.internal;

import dev.emma.hotelmodulith.guests.internal.GuestEntity;
import dev.emma.hotelmodulith.rooms.internal.RoomEntity;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_ID", nullable = false)
    private RoomEntity room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GUEST_ID", nullable = false)
    private GuestEntity guest;

    @Column(name = "RES_DATE")
    private LocalDate date;
}
