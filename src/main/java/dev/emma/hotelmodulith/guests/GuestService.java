package dev.emma.hotelmodulith.guests;

import java.util.Collection;
import java.util.List;

public interface GuestService {
    List<Guest> findAll(String emailAddress);
    List<Guest> findByIds(Collection<Long> ids);
    Guest findById(long id);
    Guest create(Guest guest);
    Guest update(long id, Guest guest);
    void delete(long id);
}
