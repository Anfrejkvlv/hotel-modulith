package dev.emma.hotelmodulith.notifications;

import dev.emma.hotelmodulith.reservations.ReservationCreatedEvent;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

/**
 * SERVICE : async + transactionnel.
 * Il ne se déclenche que si la transaction de creation de la reservation a commit
 */
@Service
class ReservationNotificationListener {

    @ApplicationModuleListener
    public void on(ReservationCreatedEvent event){
        System.out.println("Notification: confirmation envoyée pour la reservation "+
                event.reservationId()+ " (chambre " +event.roomId() +", client " + event.guestId() +")");

    }
}
