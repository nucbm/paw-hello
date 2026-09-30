package ro.paw.hello;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Verifică faptul că întregul context Spring pornește fără erori
 * (toate bean-urile se pot crea și injecta).
 */
@SpringBootTest
class HelloApplicationTests {

    @Test
    void contextLoads() {
    }
}
