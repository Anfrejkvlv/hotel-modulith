package dev.emma.hotelmodulith.guests.internal.application;

import dev.emma.hotelmodulith.guests.GuestService;
import dev.emma.hotelmodulith.guests.dto.GuestRequest;
import dev.emma.hotelmodulith.guests.dto.GuestResponse;
import dev.emma.hotelmodulith.guests.exceptions.GuestNotFoundException;
import dev.emma.hotelmodulith.guests.internal.api.mapper.GuestMapper;
import dev.emma.hotelmodulith.guests.internal.persistence.GuestEntity;
import dev.emma.hotelmodulith.guests.internal.persistence.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;
    private final GuestMapper guestMapper;


    @Override
    public List<GuestResponse> findAll(String emailAddress) {
        var entities= StringUtils.hasText(emailAddress)
                ? guestRepository.findByEmailAddress(emailAddress)
                : guestRepository.findAll();
        return entities.stream().map(guestMapper::toGuestResponse).toList();
    }

    @Override
    public List<GuestResponse> findByIds(Collection<Long> ids) {
        return guestRepository.findByGuestIdIn(ids).stream().map(guestMapper::toGuestResponse).toList();
    }

    @Override
    public GuestResponse findById(long guestId) {
        return guestRepository.findById(guestId)
                .map(guestMapper::toGuestResponse)
                .orElseThrow(()-> new GuestNotFoundException(guestId));
    }

    @Override
    public GuestResponse create(GuestRequest request) {
        if (guestRepository.existsByPhoneNumber(request.phoneNumber())){
            throw new IllegalStateException("Un Client existe deja avec numero de telephone : "+request.phoneNumber());
        }

        GuestEntity saved= guestRepository.save(guestMapper.toEntity(request));

        return guestMapper.toGuestResponse(saved);
    }

    @Override
    public GuestResponse update(long guestId, GuestRequest request) {
        GuestEntity entity=guestRepository.findById(guestId)
                .orElseThrow(()-> new GuestNotFoundException(guestId));

        guestMapper.updateEntity(request,entity);

        return guestMapper.toGuestResponse(guestRepository.save(entity));
    }

    @Override
    public void delete(long guestId) {
        if (!guestRepository.existsById(guestId)) {
            throw new GuestNotFoundException(guestId);
        }
        guestRepository.deleteById(guestId);
    }
}
