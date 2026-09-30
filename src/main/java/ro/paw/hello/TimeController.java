package ro.paw.hello;

import java.time.Clock;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Soluția temei: GET /time returnează ora curentă.
 *
 * <ul>
 *   <li>{@code GET /time} → ora în fusul orar al serverului, de ex. {@code 11:32:05}</li>
 *   <li>{@code GET /time?zone=Asia/Tokyo} → ora în fusul orar cerut</li>
 *   <li>un fus orar invalid → 400 Bad Request cu un mesaj explicativ</li>
 * </ul>
 */
@RestController
public class TimeController {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final Clock clock;

    // Un singur constructor: Spring injectează automat bean-ul Clock din ClockConfig.
    public TimeController(Clock clock) {
        this.clock = clock;
    }

    @GetMapping("/time")
    public String time(@RequestParam(required = false) String zone) {
        ZoneId zoneId = (zone == null || zone.isBlank()) ? clock.getZone() : ZoneId.of(zone);
        return LocalTime.now(clock.withZone(zoneId)).format(FORMAT);
    }

    // ZoneId.of aruncă DateTimeException pentru un nume necunoscut (ex. "Mars/Base").
    // Fără acest handler, clientul ar primi 500 Internal Server Error.
    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<String> invalidZone(DateTimeException ex) {
        return ResponseEntity.badRequest().body("Fus orar invalid: " + ex.getMessage());
    }
}
