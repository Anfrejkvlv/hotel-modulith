package dev.emma.hotelmodulith.guests.internal;

import dev.emma.hotelmodulith.guests.Guest;
import dev.emma.hotelmodulith.guests.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;


    @Override
    public List<Guest> findAll(String emailAddress) {
        var entities= StringUtils.hasText(emailAddress)
                ? guestRepository.findByEmailAddress(emailAddress)
                : guestRepository.findAll();
        return entities.stream().map(this::toApi).toList();
    }

    @Override
    public List<Guest> findByIds(Collection<Long> ids) {
        return guestRepository.findByGuestIdIn(ids).stream().map(this::toApi).toList();
    }

    @Override
    public Guest findById(long id) {
        return guestRepository.findById(id)
                .map(this::toApi)
                .orElseThrow(()-> new IllegalArgumentException("Client introuvable : "+ id));
    }

    @Override
    public Guest create(Guest guest) {
        GuestEntity entity= new GuestEntity();
        copy(guest, entity);
        return toApi(guestRepository.save(entity));
    }

    @Override
    public Guest update(long id, Guest guest) {
        GuestEntity entity=guestRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Client introuvable : "+ id));
        copy(guest, entity);
        return toApi(guestRepository.save(entity));
    }

    @Override
    public void delete(long id) {
        guestRepository.deleteById(id);
    }

    private void copy(Guest guest, GuestEntity entity) {
        entity.setFirstName(guest.getFirstName());
        entity.setLastName(guest.getLastName());
        entity.setEmailAddress(guest.getEmailAddress());
        entity.setPhoneNumber(guest.getPhoneNumber());
        entity.setAddress(guest.getAddress());
        entity.setState(guest.getState());
        entity.setCountry(guest.getCountry());
    }

    private Guest toApi(GuestEntity guestEntity) {
        return Guest.builder()
                .guestId(guestEntity.getGuestId())
                .emailAddress(guestEntity.getEmailAddress())
                .firstName(guestEntity.getFirstName())
                .lastName(guestEntity.getLastName())
                .address(guestEntity.getAddress())
                .state(guestEntity.getState())
                .country(guestEntity.getCountry())
                .phoneNumber(guestEntity.getPhoneNumber()).build();
    }
}
