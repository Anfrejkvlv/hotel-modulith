package dev.emma.hotelmodulith.guests.internal.persistence;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "GUESTS")
@Getter @Setter @NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
@Builder
public class GuestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GUEST_ID")
    private long guestId;
    @Column(name = "FIRST_NAME", length = 80, nullable = false)
    private String firstName;
    @Column(name = "LAST_NAME", length = 80, nullable = false)
    private String lastName;
    @Column(name = "EMAIL_ADDRESS",  length = 150, nullable = false)
    private String emailAddress;
    @Column(name = "ADDRESS", length = 250, nullable = false)
    private String address;
    @Column(name = "COUNTRY",  length = 50, nullable = false)
    private String country;
    @Column(name = "STATE",   length = 50, nullable = false)
    private String state;
    @Column(name = "PHONE_NUMBER",  length = 20, nullable = false)
    private String phoneNumber;
}
