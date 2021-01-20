import it.uniseats.control.gestione_accesso.LoginServlet;
import it.uniseats.control.gestione_utente.RegistrazioneServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletConfig;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrazioneServletTest {

    private RegistrazioneServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;

    @BeforeEach
    public void setUp() {
        servlet = new RegistrazioneServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.setSession(session);

    }

    @BeforeEach
    public void oneWaySetup() throws ServletException {
        ServletConfig sg = new MockServletConfig();
        servlet.init(sg);
    }

    @Test
    public void registrazioneTest() throws ServletException, IOException {
        request.addParameter("action", "add");

        request.addParameter("email", "a.allidato1@studenti.unisa.it");
        request.addParameter("password", "Antonio1234-");
        request.addParameter("matricola", "0512102852");
        request.addParameter("anno","1");
        request.addParameter("dipartimento", "Informatica");
        request.addParameter("nome", "Antonio");
        request.addParameter("cognome", "Allidato");
        servlet.doPost(request, response);
        assertEquals("Registrazione effettuata con successo", request.getAttribute("message"));
    }

}
