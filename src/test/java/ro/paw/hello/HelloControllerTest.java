package ro.paw.hello;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

/**
 * Test „felie web”: pornește doar stratul MVC pentru HelloController,
 * fără server real. Cererile sunt simulate cu MockMvcTester.
 */
@WebMvcTest(HelloController.class)
class HelloControllerTest {

    @Autowired
    private MockMvcTester mvc;

    @Test
    void helloWithoutNameUsesDefault() {
        assertThat(mvc.get().uri("/hello"))
                .hasStatusOk()
                .bodyText().isEqualTo("Hello, World!");
    }

    @Test
    void helloWithName() {
        assertThat(mvc.get().uri("/hello").param("name", "Ana"))
                .hasStatusOk()
                .bodyText().isEqualTo("Hello, Ana!");
    }
}
