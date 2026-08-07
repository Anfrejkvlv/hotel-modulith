package dev.emma.hotelmodulith.rooms.exceptions;

public class RoomNotFoundExceptions extends RuntimeException {
    public RoomNotFoundExceptions(Long roomId) {

        super("Chambre introuvable: " + roomId);
    }
}
