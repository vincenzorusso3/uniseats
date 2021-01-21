package control;

import static org.junit.jupiter.api.Assertions.assertEquals;

import it.uniseats.control.gestione_prenotazione.PrenotazioneServlet;
import it.uniseats.model.beans.PrenotazioneBean;
import it.uniseats.model.dao.PrenotazioneDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletConfig;

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
  void prenotazioneSingolaTest() throws ServletException, IOException, SQLException,
      ParseException {
    request.addParameter("action", "prenotazioneSingola");
    request.addParameter("dateValueSingolo","2021/02/13");
    request.getSession().setAttribute("email","a.sabia15@studenti.unisa.it");
    servlet.doPost(request,response);
    ArrayList<PrenotazioneBean> beans = (ArrayList<PrenotazioneBean>) PrenotazioneDAO.doQuery(PrenotazioneDAO.doFindPrenotazioni,"0512105949");
    String codice = "";
    for(PrenotazioneBean b : beans){
      if(b.getData().equals("2021-02-13")){
        codice = b.getCodice();
      }
    }
    System.out.println(codice);
    PrenotazioneDAO.doQuery(PrenotazioneDAO.doDelete,codice);
    assertEquals("/view/prenotazioni_effettuate/VisualizzaPrenotazioniView.jsp", response.getForwardedUrl());
 }

  @Test
  void prenotazioneGruppoTest() throws ServletException, IOException, SQLException, ParseException {
    request.addParameter("action", "prenotazioneGruppo");
    request.addParameter("dateValueGruppo","2021/02/18");
    request.getSession().setAttribute("email","a.sabia15@studenti.unisa.it");
    servlet.doPost(request,response);
    LinkedList<PrenotazioneBean> beans = (LinkedList<PrenotazioneBean>)PrenotazioneDAO.doQuery(PrenotazioneDAO.doFindPrenotazioni,"0512105949");
    String codice = "";
    for(PrenotazioneBean b : beans){
      if(b.getData().equals("2021-02-13")){
        codice = b.getCodice();
      }
    }
    System.out.println(codice);
    PrenotazioneDAO.doQuery(PrenotazioneDAO.doDelete,codice);

    assertEquals("/view/prenotazioni_effettuate/VisualizzaPrenotazioniView.jsp", response.getForwardedUrl());




  }



}
