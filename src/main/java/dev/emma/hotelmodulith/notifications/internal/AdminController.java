package dev.emma.hotelmodulith.notifications.internal;

import dev.emma.hotelmodulith.notifications.EventManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/admin")
@RequiredArgsConstructor
public class AdminController {

    private final EventManagementService  eventManagementService;

    @PostMapping("/retry/events")
    public ResponseEntity<String> retryFailedEvents() {
        eventManagementService.resubmitFailedEvents();
        return ResponseEntity.ok("Relance des événements incomplets terminée.");
    }
}
