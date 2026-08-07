package dev.emma.hotelmodulith.rooms.internal.persistence;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ROOMS")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROOM_ID")
    private long roomId;
    @Column(name = "NAME",nullable = false, length = 120)
    private String name;
    @Column(name = "ROOM_NUMBER", nullable = false, length = 20)
    private String roomNumber;
    @Column(name = "BED_INFO",  nullable = false, length = 80)
    private String bedInfo;
}
