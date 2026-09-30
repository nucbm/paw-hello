package ro.paw.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punctul de pornire al aplicației.
 *
 * <p>{@code @SpringBootApplication} activează auto-configurarea și scanarea
 * componentelor din pachetul {@code ro.paw.hello} și din subpachetele lui.
 * La pornire, Spring Boot găsește starter-ul webmvc pe classpath și pornește
 * un Tomcat embedded pe portul din application.properties.</p>
 */
@SpringBootApplication
public class HelloApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloApplication.class, args);
    }
}
