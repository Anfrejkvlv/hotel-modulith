package dev.emma.hotelmodulith.guests.internal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
interface GuestRepository extends CrudRepository<GuestEntity, Long> {
    List<GuestEntity> findAll();
    List<GuestEntity> findByEmailAddress(String emailAddress);
    List<GuestEntity> findByGuestIdIn(Collection<Long> guestIds);
}