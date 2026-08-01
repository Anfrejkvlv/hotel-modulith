package dev.emma.hotelmodulith.notifications;

import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.IncompleteEventPublications;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventManagementService {

    private final IncompleteEventPublications incompleteEventPublications;

    public void resubmitFailedEvents(){
        System.out.println("Relance des événements en échec...");
        incompleteEventPublications.resubmitIncompletePublications(eventPublication -> true);
    }
}
