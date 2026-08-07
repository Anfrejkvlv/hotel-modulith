package dev.emma.hotelmodulith.rooms.internal.application;

import dev.emma.hotelmodulith.rooms.RoomService;
import dev.emma.hotelmodulith.rooms.dto.RoomRequest;
import dev.emma.hotelmodulith.rooms.dto.RoomResponse;
import dev.emma.hotelmodulith.rooms.exceptions.RoomNotFoundExceptions;
import dev.emma.hotelmodulith.rooms.internal.api.mapper.RoomMapper;
import dev.emma.hotelmodulith.rooms.internal.persistence.RoomEntity;
import dev.emma.hotelmodulith.rooms.internal.persistence.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Override
    public List<RoomResponse> getRooms() {
        return StreamSupport
                .stream(roomRepository.findAll().spliterator(), false)
                .map(roomMapper::toResponse).toList();
    }

    @Override
    public RoomResponse getById(long roomId) {
        return roomRepository.findById(roomId)
                .map(roomMapper::toResponse)
                .orElseThrow(()-> new RoomNotFoundExceptions(roomId));
    }

    @Override
    public RoomResponse create(RoomRequest request) {

        if (roomRepository.existsByRoomNumber(request.roomNumber())){
            throw new IllegalStateException("Numéro de chambre utilisé : "+request.roomNumber());
        }

        RoomEntity saved = roomRepository.save(roomMapper.toEntity(request));

        return roomMapper.toResponse(saved);
    }

    @Override
    public RoomResponse update(RoomRequest request, long roomId) {
        RoomEntity entity = roomRepository.findById(roomId)
                .orElseThrow(()-> new RoomNotFoundExceptions(roomId));
        roomMapper.updateEntity(request, entity);

        return roomMapper.toResponse(roomRepository.save(entity));
    }

    @Override
    public void delete(long roomId) {
        if (!roomRepository.existsById(roomId)) {
            throw new RoomNotFoundExceptions(roomId);
        }
        roomRepository.deleteById(roomId);
    }
}
