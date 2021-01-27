package control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import it.uniseats.control.gestione_utente.RegistrazioneServlet;
import it.uniseats.model.beans.StudenteBean;
import it.uniseats.model.dao.StudenteDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
  void actionNull() throws ServletException, IOException {

    servlet.doPost(request, response);

    assertEquals("/view/profilo_utente/RegistrazioneView.jsp", response.getForwardedUrl());
  }

  // TC_1.1_01
  @Test
  void registrazioneEmpty() throws ServletException, IOException {

    request.addParameter("action", "add");
    request.addParameter("email", "");
    request.addParameter("password", "");
    request.addParameter("anno", "0");
    request.addParameter("dipartimento", "");
    request.addParameter("nome", "");
    request.addParameter("cognome", "");
    servlet.doPost(request, response);

    assertEquals("Registrazione fallita. Si prega di riprovare", request.getAttribute("message"));
  }

  //TC_1.1_02
  @Test
  void registrazioneMatricolaVuota() throws ServletException, IOException {
    request.addParameter("action", "add");
    request.addParameter("email", "a.sabia16@studenti.unisa.it");
    request.addParameter("password", "A3wdwr4-");
    request.addParameter("anno", "1");
    request.addParameter("dipartimento", "Informatica");
    request.addParameter("nome", "Carlo");
    request.addParameter("cognome", "Cattanio");

    servlet.doPost(request, response);

    assertEquals("Registrazione fallita. Si prega di riprovare", request.getAttribute("message"));
  }



  //TC_1.1_03
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

  //TC_1.1_04
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

  //TC_1.1_05
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


  @Test
  void TestDipartimenti() throws ServletException, IOException {
    request.addParameter("action", "getDipartimenti");
    servlet.doPost(request, response);
    ArrayList<String> listDip=
        (ArrayList<String>) request.getSession().getAttribute("dipartimenti");
    assertNotNull(listDip);

  }


}
