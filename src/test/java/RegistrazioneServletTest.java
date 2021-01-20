import static org.junit.jupiter.api.Assertions.assertEquals;

import it.uniseats.control.gestione_utente.RegistrazioneServlet;
import it.uniseats.model.beans.StudenteBean;
import it.uniseats.model.dao.StudenteDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

class RegistrazioneServletTest {

  private RegistrazioneServlet servlet;
  private MockHttpServletRequest request;
  private MockHttpServletResponse response;

  StudenteBean bean = new StudenteBean("Benedetto", "Simone", "0512108890",
      "b.simone@studenti.unisa.it", "bensmn_", 3, "Informatica");



  @BeforeEach
  void setUp() throws SQLException {
    servlet = new RegistrazioneServlet();
    request = new MockHttpServletRequest();
    response = new MockHttpServletResponse();

    StudenteDAO.doQuery("doDelete", bean.getMatricola());
    StudenteDAO.doQuery("doDelete", "0512164137");

  }

  @BeforeEach
  void oneWaySetup() throws ServletException {
    ServletConfig sg = new MockServletConfig();
    servlet.init(sg);
  }

  @Test
  void registrazioneFailTest() throws ServletException, IOException, SQLException {

    StudenteDAO.doQuery("doSave", bean);

    request.addParameter("action", "add");
    request.addParameter("email", "b.simone@studenti.unisa.it");
    request.addParameter("password", "Antonio1234-");
    request.addParameter("matricola", "0512102852");
    request.addParameter("anno", "1");
    request.addParameter("dipartimento", "Informatica");
    request.addParameter("nome", "Antonio");
    request.addParameter("cognome", "Allidato");

    servlet.doPost(request, response);
    assertEquals("Esiste già un account con questa e-mail", request.getAttribute("message"));




  }

  //TC_1.1_03
  @Test
  void registrazioneTestSuccessfull() throws ServletException, IOException {
    request.addParameter("action", "add");
    request.addParameter("email", "c.cattanio1@studenti.unisa.it");
    request.addParameter("password", "A3wdwr4-");
    request.addParameter("matricola", "0512164137");
    request.addParameter("anno", "1");
    request.addParameter("dipartimento", "Informatica");
    request.addParameter("nome", "Carlo");
    request.addParameter("cognome", "Cattanio");

    servlet.doPost(request, response);
    assertEquals("Registrazione effettuata con successo", request.getAttribute("message"));
  }
  //TC_1.1_01
  @Test
  void registrazioneTestFailedEmailEsistente() throws ServletException, IOException {
    request.addParameter("action", "add");
    request.addParameter("email", "a.sabia15@studenti.unisa.it");
    request.addParameter("password", "A3wdwr4-");
    request.addParameter("matricola", "0512164137");
    request.addParameter("anno", "1");
    request.addParameter("dipartimento", "Informatica");
    request.addParameter("nome", "Carlo");
    request.addParameter("cognome", "Cattanio");

    servlet.doPost(request, response);
    assertEquals("Esiste già un account con questa e-mail", request.getAttribute("message"));

  }

  //TC_1.2_01
  @Test
  void registrazioneTestFailedMatricolaEsistente() throws ServletException, IOException {
    request.addParameter("action", "add");
    request.addParameter("email", "c.cattanio1@studenti.unisa.it");
    request.addParameter("password", "A3wdwr4-");
    request.addParameter("matricola", "0512105949");
    request.addParameter("anno", "1");
    request.addParameter("dipartimento", "Informatica");
    request.addParameter("nome", "Carlo");
    request.addParameter("cognome", "Cattanio");

    servlet.doPost(request, response);
    assertEquals("Esiste già un account con questa Matricola", request.getAttribute("message"));

  }

}
