import static org.junit.jupiter.api.Assertions.assertEquals;

import it.uniseats.control.gestione_utente.RegistrazioneServlet;
import it.uniseats.model.dao.StudenteDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletConfig;

class RegistrazioneServletTest {

  private RegistrazioneServlet servlet;
  private MockHttpServletRequest request;
  private MockHttpServletResponse response;
  private MockHttpSession session;

  @BeforeEach
  void setUp() {
    servlet = new RegistrazioneServlet();
    request = new MockHttpServletRequest();
    response = new MockHttpServletResponse();
    request.setSession(session);

  }

  @BeforeEach
  void oneWaySetup() throws ServletException {
    ServletConfig sg = new MockServletConfig();
    servlet.init(sg);
  }

  @Test
  void registrazioneFailTest() throws ServletException, IOException, SQLException {
    request.addParameter("action", "add");

    request.addParameter("email", "");
    request.addParameter("password", "Antonio1234-");
    request.addParameter("matricola", "0512102852");
    request.addParameter("anno", "1");
    request.addParameter("dipartimento", "Informatica");
    request.addParameter("nome", "Antonio");
    request.addParameter("cognome", "Allidato");

    servlet.doPost(request, response);

    assertEquals("Esiste già un account con questa e-mail", request.getAttribute("message"));
  }



}
