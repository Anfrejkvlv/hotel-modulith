package dev.emma.hotelmodulith.guests.dto;

import lombok.Builder;

@Builder
public record GuestRequest(
        String firstName,
        String lastName,
        String emailAddress,
        String address,
        String country,
        String state,
        String phoneNumber
) {}
