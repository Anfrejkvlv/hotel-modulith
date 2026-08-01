package dev.emma.hotelmodulith.rooms.internal;

import dev.emma.hotelmodulith.rooms.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface RoomRepository extends CrudRepository<RoomEntity, Long> {
}
