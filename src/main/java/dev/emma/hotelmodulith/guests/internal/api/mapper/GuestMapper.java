package dev.emma.hotelmodulith.guests.internal.api.mapper;


import dev.emma.hotelmodulith.guests.dto.GuestRequest;
import dev.emma.hotelmodulith.guests.dto.GuestResponse;
import dev.emma.hotelmodulith.guests.internal.persistence.GuestEntity;
import org.springframework.stereotype.Component;

@Component
public class GuestMapper {

    public GuestResponse toGuestResponse(GuestEntity entity) {
        return GuestResponse.builder()
                .guestId(entity.getGuestId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .emailAddress(entity.getEmailAddress())
                .address(entity.getAddress())
                .country(entity.getCountry())
                .state(entity.getState())
                .phoneNumber(entity.getPhoneNumber())
                .build();
    }

    public GuestEntity toEntity(GuestRequest request) {
        return GuestEntity.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .emailAddress(request.emailAddress())
                .address(request.address())
                .country(request.country())
                .state(request.state())
                .phoneNumber(request.phoneNumber())
                .build();
    }

    public void updateEntity(GuestRequest request, GuestEntity entity) {
        entity.setFirstName(request.firstName());
        entity.setLastName(request.lastName());
        entity.setEmailAddress(request.emailAddress());
        entity.setAddress(request.address());
        entity.setCountry(request.country());
        entity.setState(request.state());
        entity.setPhoneNumber(request.phoneNumber());
    }
}
