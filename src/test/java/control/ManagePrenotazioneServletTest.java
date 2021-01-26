package control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import it.uniseats.control.gestione_prenotazione.PrenotazioneServlet;
import it.uniseats.control.visualizzaPrenotazioni.ManagePrenotazioneServlet;
import it.uniseats.model.beans.PrenotazioneBean;
import it.uniseats.model.dao.PrenotazioneDAO;
import it.uniseats.utils.DateUtils;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletConfig;


class ManagePrenotazioneServletTest {

  private ManagePrenotazioneServlet servlet;
  private MockHttpServletRequest request;
  private MockHttpServletResponse response;
  private MockHttpSession session;

  /*
  @AfterAll
  public static void deletePrenotazione() throws SQLException {
      PrenotazioneDAO.doQuery("doDelete","0001-0512105933");
  }
  */

  @BeforeEach
  public void setUp() {
    servlet = new ManagePrenotazioneServlet();
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
  public void actionNull() throws ParseException, SQLException, ServletException, IOException {
    servlet.doPost(request, response);
    assertEquals("/view/prenotazioni_effettuate/VisualizzaPrenotazioniView.jsp",
        response.getForwardedUrl());
  }

  @Test
  public void deletePrenotazioneSuccesfull()
      throws ParseException, SQLException, ServletException, IOException {

    PrenotazioneBean prenotazioneBean =
        (PrenotazioneBean) PrenotazioneDAO.doQuery("doRetrieveByCode", "9-0512108336-21/01/2021");


    request.addParameter("action", "eliminaPrenotazione");
    request.addParameter("codice", prenotazioneBean.getCodice());


    servlet.doPost(request, response);
    assertEquals("/view/prenotazioni_effettuate/VisualizzaPrenotazioniView.jsp",
        response.getForwardedUrl());
  }

  //TC_1.3_01
  @Test
  public void updateDataFailedTestDataPrecedente()
      throws ParseException, SQLException, ServletException, IOException {
    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
    PrenotazioneBean prenotazioneBean =
        (PrenotazioneBean) PrenotazioneDAO.doQuery("doRetrieveByCode", "10-0512354364-21/01/2021");

    request.addParameter("action", "modificaPrenotazione");
    request.getSession().setAttribute("codice", prenotazioneBean.getCodice());

    request.getSession().setAttribute("data", df.parse("15/01/2021"));

    request.addParameter("tipologia", "gruppo");
    request.addParameter("codice", prenotazioneBean.getCodice());
    servlet.doPost(request, response);
    assertNotEquals(prenotazioneBean.getData(), df.parse("15/01/2021"));
  }

  //TC_1.3_02
  @Test
  public void updateDataTestSuccesfullSingolo()
      throws ParseException, SQLException, ServletException, IOException {
    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
    PrenotazioneBean prenotazioneBean = (PrenotazioneBean) PrenotazioneDAO
        .doQuery("doRetrieveByCode", "10-0512354364-21/01/2021");

    request.addParameter("action", "modificaData");
    request.addParameter("codice", prenotazioneBean.getCodice());

    String[] s = DateUtils.dateToString(new Date()).replace("21", "2021").split("/");
    String data = s[2] + "/" + s[1] + "/" + s[0];
    System.out.println(data);

    request.addParameter("data", data);
    request.addParameter("tipologia", "singolo");
    request.addParameter("codice", prenotazioneBean.getCodice());
    servlet.doPost(request, response);
    assertEquals("/view/prenotazioni_effettuate/VisualizzaPrenotazioniView.jsp",
        response.getForwardedUrl());
  }


  //TC_1.3_02
  @Test
  public void updateDataTestSuccesfull()
      throws ParseException, SQLException, ServletException, IOException {
    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
    PrenotazioneBean prenotazioneBean =
        (PrenotazioneBean) PrenotazioneDAO.doQuery("doRetrieveByCode", "10-0512354364-21/01/2021");

    request.addParameter("action", "modificaData");
    request.addParameter("codice", prenotazioneBean.getCodice());

    request.addParameter("data", "2021/02/25");
    request.addParameter("tipologia", "gruppo");
    request.addParameter("codice", prenotazioneBean.getCodice());
    servlet.doPost(request, response);
    assertEquals("/view/prenotazioni_effettuate/VisualizzaPrenotazioniView.jsp",
        response.getForwardedUrl());
  }

  @Test
  public void visualizzaPrenotazioniTest()
      throws ParseException, SQLException, ServletException, IOException {
    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
    PrenotazioneBean prenotazioneBean =
        (PrenotazioneBean) PrenotazioneDAO.doQuery("doRetrieveByCode", "10-0512354364-21/01/2021");

    request.addParameter("action", "visualizzaPrenotazioni");
    request.getSession().setAttribute("matricola", "0512105949");
    servlet.doPost(request, response);
    assertEquals("/view/prenotazioni_effettuate/VisualizzaPrenotazioniView.jsp",
        response.getForwardedUrl());
  }

  @Test
  public void modificaDataPassata()
      throws ParseException, SQLException, ServletException, IOException {
    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
    PrenotazioneBean prenotazioneBean =
        (PrenotazioneBean) PrenotazioneDAO.doQuery("doRetrieveByCode", "5-4107147896-21/01/2021");

    request.addParameter("action", "modificaData");
    request.addParameter("codice", prenotazioneBean.getCodice());

    request.addParameter("data", "2021/02/25");
    request.addParameter("tipologia", "gruppo");
    request.addParameter("codice", prenotazioneBean.getCodice());
    servlet.doPost(request, response);
    assertEquals("Non è più possibile modificare la prenotazione",
        request.getAttribute("error"));
  }


}

