package edu.comillas.icai.gitt.pat.spring.pf.repository;

import edu.comillas.icai.gitt.pat.spring.pf.model.Role;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class PfApplicationE2ETest {

    private static final String NAME = "Name";
    private static final String EMAIL = "name@email.com";
    private static final String PASS = "aaaaaaA1";

    @Autowired
    TestRestTemplate client;

    @Test
    public void registerTest() {
        // Given ...
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String registro = "{" +
                "\"name\":\"" + NAME + "\"," +
                "\"email\":\"" + EMAIL + "\"," +
                "\"role\":\"" + Role.USER + "\"," +
                "\"password\":\"" + PASS + "\"," +
                "\"passwordValidate\":\"" + PASS + "\"}";

        // When ...
        ResponseEntity<String> response = client.exchange(
                "http://localhost:8080/paulaphotography/user",
                HttpMethod.POST, new HttpEntity<>(registro, headers), String.class);

        // Then ...
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals("{" +
                        "\"name\":\"" + NAME + "\"," +
                        "\"email\":\"" + EMAIL + "\"," +
                        "\"role\":\"" + Role.USER + "\"}",
                response.getBody());
    }

    @Test public void loginTest() {
        // Given ...

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String credenciales = "{" +
                "\"email\":\"" + EMAIL + "\"," +
                "\"password\":\"" + PASS + "\"}";
        // When ...
        ResponseEntity<String> response = client.exchange(
                "http://localhost:8080/paulaphotography/user/session",
                HttpMethod.POST, new HttpEntity<>(credenciales, headers), String.class);
        // Then ...
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void logoutTest() {
        String url = "http://localhost:8080/paulaphotography/user/session";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "session=1");

        ResponseEntity<Void> response = client.exchange(
                url, HttpMethod.DELETE, new HttpEntity<>(headers), Void.class);

        Assertions.assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
    }

    @Test
    public void pedidoPendienteTest() {
        String url = "http://localhost:8080/paulaphotography/user/pedidoPendiente";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "session=1");

        ResponseEntity<String> response = client.exchange(
                url, HttpMethod.GET, new HttpEntity<>(headers), String.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody()).isNotEmpty();
    }

}
