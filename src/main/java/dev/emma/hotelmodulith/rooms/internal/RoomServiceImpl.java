package dev.emma.hotelmodulith.rooms.internal;

import dev.emma.hotelmodulith.rooms.Room;
import dev.emma.hotelmodulith.rooms.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public List<Room> getRooms() {
        return StreamSupport
                .stream(roomRepository.findAll().spliterator(), false)
                .map(this::toApi).toList();
    }

    @Override
    public Room getById(long roomId) {
        return roomRepository.findById(roomId)
                .map(this::toApi)
                .orElseThrow(()->new IllegalArgumentException("Chambre introuvable : "+roomId));
    }

    @Override
    public Room create(Room room) {
        RoomEntity entity = new RoomEntity();
        copy(room, entity);
        return toApi(roomRepository.save(entity));
    }

    @Override
    public Room update(Room room, long roomId) {
        RoomEntity entity = roomRepository.findById(roomId)
                .orElseThrow(()-> new IllegalArgumentException("Chambre Introuvable : "+roomId));
        copy(room, entity);
        return toApi(roomRepository.save(entity));
    }

    @Override
    public void delete(long roomId) {
        roomRepository.deleteById(roomId);
    }

    private Room toApi(RoomEntity entity) {
        return Room.builder()
                .roomId(entity.getRoomId())
                .name(entity.getName())
                .roomNumber(entity.getRoomNumber())
                .bedInfo(entity.getBedInfo()).build();
    }

    private void copy(Room room, RoomEntity entity) {
        entity.setRoomNumber(room.getRoomNumber());
        entity.setName(room.getName());
        entity.setBedInfo(room.getBedInfo());
    }
}
