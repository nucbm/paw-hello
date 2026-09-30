# hello – Laboratorul 1 (PAW)

Primul proiect Spring Boot.

| Endpoint | Răspuns |
|---|---|
| `GET /` | pagină cu linkuri de test (`static/index.html`) |
| `GET /hello` | `Hello, World!` |
| `GET /hello?name=Ana` | `Hello, Ana!` |
| `GET /time` | ora curentă a serverului, de ex. `11:32:05` |
| `GET /time?zone=Asia/Tokyo` | ora în fusul orar cerut |
| `GET /time?zone=Mars/Base` | `400 Bad Request` – fus orar invalid |

**Versiuni:** Java 21 · Spring Boot 4.1.1 · Maven 3.9.11

## Deschidere în IntelliJ IDEA

1. **File → Open…** și alegeți directorul `hello` (cel care conține `pom.xml`).
   Dacă vă întreabă, alegeți *Open as Project* și *Trust Project*.
2. Așteptați ca IntelliJ să descarce dependențele Maven (bara de progres din dreapta jos).
3. Verificați JDK-ul: **File → Project Structure → Project → SDK = 21** (sau 25).
4. Deschideți `src/main/java/ro/paw/hello/HelloApplication.java` și apăsați ▶ lângă `main`.
5. În browser: <http://localhost:8080>.

Testele se rulează cu clic dreapta pe directorul `src/test/java` → **Run 'All Tests'**.

## Din linia de comandă

```bash
./mvnw spring-boot:run        # Linux / macOS
mvnw.cmd spring-boot:run      # Windows

./mvnw test                   # rulează testele
./mvnw package                # produce target/hello-0.0.1-SNAPSHOT.jar
java -jar target/hello-0.0.1-SNAPSHOT.jar
```

La prima rulare, `mvnw` descarcă Maven 3.9.11 în `~/.m2/wrapper`. Nu e nevoie ca Maven să fie instalat.

```bash
curl "localhost:8080/hello?name=Ana"
curl "localhost:8080/time?zone=Europe/London"
```

## Structura proiectului

```
hello/
├── pom.xml                        starter-ele webmvc și webmvc-test, plugin Spring Boot
├── mvnw, mvnw.cmd, .mvn/          Maven Wrapper
└── src/
    ├── main/java/ro/paw/hello/
    │   ├── HelloApplication.java  @SpringBootApplication + main()
    │   ├── HelloController.java   GET /hello
    │   ├── TimeController.java    GET /time  ← soluția temei
    │   └── ClockConfig.java       bean-ul Clock folosit de TimeController
    ├── main/resources/
    │   ├── application.properties server.port
    │   └── static/index.html      pagina de start
    └── test/java/ro/paw/hello/
        ├── HelloApplicationTests.java   contextul pornește
        ├── HelloControllerTest.java     GET /hello
        └── TimeControllerTest.java      GET /time cu ceas fix
```

## Observații pentru studenți

- **De ce `Clock` și nu `LocalTime.now()`?** Ora curentă se schimbă în fiecare secundă, deci un test
  pe `LocalTime.now()` nu poate verifica o valoare exactă. Controllerul primește un `Clock` prin
  constructor: în aplicație este ceasul sistemului, în test un ceas fix. Este primul exemplu de
  injectare a dependențelor, pe care îl aprofundăm în săptămâna 3.
- **`spring-boot-starter-webmvc`** este numele din Spring Boot 4 pentru ce în Spring Boot 3 se numea
  `spring-boot-starter-web`. Tutorialele mai vechi îl folosesc pe cel din urmă.
- **Portul 8080 ocupat?** Schimbați `server.port` în `application.properties`.
