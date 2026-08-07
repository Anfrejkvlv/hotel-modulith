package dev.emma.hotelmodulith.guests.dto;

import lombok.Builder;

@Builder
public record GuestResponse(
        long guestId,
        String firstName,
        String lastName,
        String emailAddress,
        String address,
        String country,
        String state,
        String phoneNumber
) {}
