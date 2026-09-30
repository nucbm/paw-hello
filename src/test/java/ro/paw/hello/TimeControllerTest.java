package ro.paw.hello;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

/**
 * Testează GET /time cu un ceas fix, ca rezultatul să nu depindă
 * de momentul în care rulează testul.
 */
@WebMvcTest(TimeController.class)
class TimeControllerTest {

    // 30 septembrie 2026, 08:32:05 UTC = 11:32:05 la București (UTC+3, ora de vară)
    private static final Instant NOW = Instant.parse("2026-09-30T08:32:05Z");

    @TestConfiguration
    static class FixedClockConfig {
        @Bean
        Clock clock() {
            return Clock.fixed(NOW, ZoneId.of("Europe/Bucharest"));
        }
    }

    @Autowired
    private MockMvcTester mvc;

    @Test
    void timeInServerZone() {
        assertThat(mvc.get().uri("/time"))
                .hasStatusOk()
                .bodyText().isEqualTo("11:32:05");
    }

    @Test
    void timeInRequestedZone() {
        assertThat(mvc.get().uri("/time").param("zone", "Asia/Tokyo"))
                .hasStatusOk()
                .bodyText().isEqualTo("17:32:05");
    }

    @Test
    void invalidZoneReturnsBadRequest() {
        assertThat(mvc.get().uri("/time").param("zone", "Mars/Base"))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyText().startsWith("Fus orar invalid");
    }
}
