package dev.emma.hotelmodulith.rooms;

import dev.emma.hotelmodulith.rooms.dto.RoomRequest;
import dev.emma.hotelmodulith.rooms.dto.RoomResponse;

import java.util.List;

public interface RoomService {
    List<RoomResponse> getRooms();
    RoomResponse getById(long roomId);
    RoomResponse create(RoomRequest request);
    RoomResponse update(RoomRequest request, long roomId);
    void delete(long roomId);

}
