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

import it.uniseats.utils.QrCodeGenerator;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
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

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

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
  public void updateDataFailedTestNuovaDataPrecedente()
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


  //TC_1.3_04
  @Test
  public void updateDataTestSuccesfull()
          throws ParseException, SQLException, ServletException, IOException {
    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
    PrenotazioneBean prenotazioneBean =
            (PrenotazioneBean) PrenotazioneDAO.doQuery("doRetrieveByCode", "2524-0512105949-23/02/2021");

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
  // TC_1.3_03
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


  @Test
  public void ExceptionSQLprova() {

      request.addParameter("action", "visualizzaPrenotazioni");
  }


  @Test
  public void getSinglePren()
          throws ParseException, SQLException, ServletException, IOException {
    PrenotazioneBean prenotazioneBean=new PrenotazioneBean();
    prenotazioneBean.setCodice(QrCodeGenerator.generateCode("0156835647","2021/02/22"));
    prenotazioneBean.setCodiceAula("00");
    prenotazioneBean.setCodicePosto("00");
    prenotazioneBean.setSingolo(true);
    prenotazioneBean.setMatricolaStudente("0156835647");
    Date d=new Date("2021/02/22");

    prenotazioneBean.setData(d);
    PrenotazioneDAO.doQuery(PrenotazioneDAO.doSave,prenotazioneBean);

    request.addParameter("action", "getSinglePren");
    request.addParameter("cod", prenotazioneBean.getCodice());


    servlet.doPost(request, response);

    assertEquals("/view/prenotazioni_effettuate/ModificaPrenotazioniView.jsp",
            response.getForwardedUrl());

    PrenotazioneDAO.doQuery(PrenotazioneDAO.doDelete,prenotazioneBean.getCodice());
  }

  @Test
  public void ModificaPrenotazionePrenNull() throws ServletException, IOException {
    request.addParameter("action", "modificaPrenotazione");
    request.addParameter("tipologia","singolo");
    request.addParameter("codice","");
    request.addParameter("data","2021/03/17");
    servlet.doPost(request,response);
    assertEquals("Si è verificato un errore",request.getAttribute("error"));
    assertEquals("/view/prenotazioni_effettuate/VisualizzaPrenotazioniView.jsp",response.getForwardedUrl());


    ;
  }

// TC_1.5
  @Test
  public void ModificaPrenotazioneCanIUpdateTrue() throws SQLException, ParseException, ServletException, IOException{
    PrenotazioneBean prenotazioneBean=new PrenotazioneBean();
    prenotazioneBean.setCodice(QrCodeGenerator.generateCode("0156835647","2021/02/22"));
    prenotazioneBean.setCodiceAula("00");
    prenotazioneBean.setCodicePosto("00");
    prenotazioneBean.setSingolo(true);
    prenotazioneBean.setMatricolaStudente("0156835647");
    Date d=new Date("2021/03/29");

    prenotazioneBean.setData(d);
    PrenotazioneDAO.doQuery(PrenotazioneDAO.doSave,prenotazioneBean);


    request.addParameter("action", "modificaPrenotazione");
    request.addParameter("tipologia","singolo");
    request.addParameter("codice",prenotazioneBean.getCodice());
    servlet.doPost(request,response);



    assertEquals("/view/prenotazioni_effettuate/VisualizzaPrenotazioniView.jsp",
            response.getForwardedUrl());

    PrenotazioneDAO.doQuery(PrenotazioneDAO.doDelete,prenotazioneBean.getCodice());



  }

  //TC_1.5_

  @Test
  public void ModificaPrenotazioneCanIUpdateFalseGruppo() throws SQLException, ParseException, ServletException, IOException{
    PrenotazioneBean prenotazioneBean=new PrenotazioneBean();
    prenotazioneBean.setCodice(QrCodeGenerator.generateCode("0156835647","2021/02/22"));
    prenotazioneBean.setCodiceAula("00");
    prenotazioneBean.setCodicePosto("00");
    prenotazioneBean.setSingolo(true);
    prenotazioneBean.setMatricolaStudente("0156835647");
    Date d=new Date("2021/04/18");

    prenotazioneBean.setData(d);
    PrenotazioneDAO.doQuery(PrenotazioneDAO.doSave,prenotazioneBean);


    request.addParameter("action", "modificaPrenotazione");
    request.addParameter("tipologia","gruppo");
    request.addParameter("codice",prenotazioneBean.getCodice());
    servlet.doPost(request,response);



    assertEquals("/view/prenotazioni_effettuate/VisualizzaPrenotazioniView.jsp",
            response.getForwardedUrl());

    PrenotazioneDAO.doQuery(PrenotazioneDAO.doDelete,prenotazioneBean.getCodice());



  }




}

