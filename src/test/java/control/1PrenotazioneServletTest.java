package control;

import static org.junit.jupiter.api.Assertions.assertEquals;

import it.uniseats.control.gestione_prenotazione.PrenotazioneServlet;
import it.uniseats.model.beans.PrenotazioneBean;
import it.uniseats.model.dao.PrenotazioneDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.test.annotation.Rollback;

class PrenotazioneServletTest {

  private PrenotazioneServlet servlet;
  private MockHttpServletRequest request;
  private MockHttpServletResponse response;
  private MockHttpSession session;

  @BeforeEach
  public void setUp() throws SQLException, ParseException {
    servlet = new PrenotazioneServlet();
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
  @Rollback
  void prenotazioneSingolaTest() throws ServletException, IOException, SQLException,
      ParseException {

    request.addParameter("action", "prenotazioneSingola");
    request.addParameter("dateValueSingolo", "2021-02-13");
    request.getSession().setAttribute("email", "a.sabia15@studenti.unisa.it");
    servlet.doPost(request, response);
    assertEquals("/view/prenotazioni_effettuate/VisualizzaPrenotazioniView.jsp",
        response.getForwardedUrl());

  }

  @Test
  @Rollback
  void prenotazioneGruppoTest() throws ServletException, IOException, SQLException, ParseException {
    request.addParameter("action", "prenotazioneGruppo");
    request.addParameter("dateValueGruppo","2021/02/18");
    request.getSession().setAttribute("email","a.sabia15@studenti.unisa.it");
    servlet.doPost(request,response);

    assertEquals("/view/prenotazioni_effettuate/VisualizzaPrenotazioniView.jsp", response.getForwardedUrl());

  }

}
