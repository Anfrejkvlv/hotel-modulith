package dev.emma.hotelmodulith.guests;

import dev.emma.hotelmodulith.guests.dto.GuestRequest;
import dev.emma.hotelmodulith.guests.dto.GuestResponse;

import java.util.Collection;
import java.util.List;

public interface GuestService {
    List<GuestResponse> findAll(String emailAddress);
    List<GuestResponse> findByIds(Collection<Long> ids);
    GuestResponse findById(long id);
    GuestResponse create(GuestRequest guest);
    GuestResponse update(long id, GuestRequest guest);
    void delete(long id);
}
