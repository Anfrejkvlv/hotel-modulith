@ApplicationModule(
        allowedDependencies = {"guests", "rooms", "reservations"}
)

package dev.emma.hotelmodulith.roomreservations;

import org.springframework.modulith.ApplicationModule;