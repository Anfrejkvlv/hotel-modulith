package dev.emma.hotelmodulith.rooms.internal.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<RoomEntity, Long> {
    boolean existsByRoomNumber(String roomNumber);
}
