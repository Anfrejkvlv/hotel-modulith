package dev.emma.hotelmodulith.rooms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RoomRequest (
        @NotBlank @Size(max = 120) String name,
        @NotBlank @Size(max = 20) String roomNumber,
        @NotBlank @Size(max = 80) String bedInfo
){
}
