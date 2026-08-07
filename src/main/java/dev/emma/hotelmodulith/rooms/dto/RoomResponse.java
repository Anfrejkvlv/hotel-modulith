package dev.emma.hotelmodulith.rooms.dto;

import lombok.Builder;

@Builder
public record RoomResponse (
    long roomId,
    String name,
    String roomNumber,
    String bedInfo
){}
