package dev.emma.hotelmodulith.rooms.internal.api.mapper;

import dev.emma.hotelmodulith.rooms.dto.RoomRequest;
import dev.emma.hotelmodulith.rooms.dto.RoomResponse;
import dev.emma.hotelmodulith.rooms.internal.persistence.RoomEntity;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {

    public RoomResponse toResponse(RoomEntity entity) {
        return RoomResponse.builder()
                .roomId(entity.getRoomId())
                .name(entity.getName())
                .roomNumber(entity.getRoomNumber())
                .bedInfo(entity.getBedInfo())
                .build();
    }

    public RoomEntity toEntity(RoomRequest request) {
        return RoomEntity.builder()
                .name(request.name())
                .roomNumber(request.roomNumber())
                .bedInfo(request.bedInfo())
                .build();
    }

    public void updateEntity(RoomRequest request, RoomEntity entity) {
        entity.setName(request.name());
        entity.setRoomNumber(request.roomNumber());
        entity.setBedInfo(request.bedInfo());
    }
}

