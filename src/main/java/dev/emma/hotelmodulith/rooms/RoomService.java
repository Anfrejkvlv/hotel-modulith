package dev.emma.hotelmodulith.rooms;

import java.util.List;

public interface RoomService {
    List<Room> getRooms();
    Room getById(long roomId);
    Room create(Room room);
    Room update(Room room, long roomId);
    void delete(long roomId);

}
