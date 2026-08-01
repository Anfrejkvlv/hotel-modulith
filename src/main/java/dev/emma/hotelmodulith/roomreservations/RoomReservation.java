package dev.emma.hotelmodulith.roomreservations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomReservation {
    private long guestId;
    private long roomId;
    private long reservationId;
    private String firstName;
    private String lastName;
    private String name;
    private String roomNumber;
    private String bedInfo;
    private LocalDate date;
}
