package dev.emma.hotelmodulith.guests.exceptions;

public class GuestNotFoundException extends RuntimeException {
    public GuestNotFoundException(long guestId) {
        super("Client Introuvable : "+guestId);
    }
}
