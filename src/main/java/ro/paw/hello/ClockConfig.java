package ro.paw.hello;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Definește ceasul folosit de aplicație ca bean Spring.
 *
 * <p>Controllerul nu apelează direct {@code LocalTime.now()}, ci primește un
 * {@link Clock} prin constructor. În teste îl putem înlocui cu un ceas fix,
 * ca rezultatul să fie previzibil. Este o primă privire asupra injectării
 * dependențelor (săptămâna 3).</p>
 */
@Configuration
public class ClockConfig {

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
