package dev.emma.hotelmodulith.reservations.internal.api.mapper;

import dev.emma.hotelmodulith.reservations.dto.ReservationRequest;
import dev.emma.hotelmodulith.reservations.dto.ReservationResponse;
import dev.emma.hotelmodulith.reservations.internal.persistence.ReservationEntity;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public ReservationResponse toResponse(ReservationEntity entity) {
        return ReservationResponse.builder()
                .reservationId(entity.getReservationId())
                .guestId(entity.getGuestId())
                .roomId(entity.getRoomId())
                .date(entity.getDate())
                .build();
    }

    public ReservationEntity toEntity(ReservationRequest request) {
        return ReservationEntity.builder()
                .roomId(request.roomId())
                .guestId(request.guestId())
                .date(request.date())
                .build();
    }

    public void updateEntity(ReservationRequest request, ReservationEntity entity) {
        entity.setRoomId(request.roomId());
        entity.setGuestId(request.guestId());
        entity.setDate(request.date());
    }
}
