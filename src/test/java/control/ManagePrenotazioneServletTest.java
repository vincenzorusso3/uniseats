package control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import it.uniseats.control.visualizzaprenotazioni.ManagePrenotazioneServlet;
import it.uniseats.model.beans.PrenotazioneBean;
import it.uniseats.model.dao.PrenotazioneDao;
import it.uniseats.utils.DateUtils;
import it.uniseats.utils.QrCodeGenerator;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
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
      PrenotazioneDao.doQuery("doDelete","0001-0512105933");
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
            (PrenotazioneBean) PrenotazioneDao.doQuery(
                "doRetrieveByCode", "9-0512108336-21/01/2021");


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
            (PrenotazioneBean) PrenotazioneDao.doQuery(
                "doRetrieveByCode", "10-0512354364-21/01/2021");

    request.addParameter("action", "modificaPrenotazione");
    request.getSession().setAttribute("codice", prenotazioneBean.getCodice());

    request.getSession().setAttribute("data", df.parse("15/01/2021"));

    request.addParameter("tipologia", "gruppo");
    request.addParameter("codice", prenotazioneBean.getCodice());
    servlet.doPost(request, response);
    assertNotEquals(prenotazioneBean.getData(), df.parse("15/01/2021"));
  }


  @Test
  public void updateDataTestSuccesfullSingolo()
          throws ParseException, SQLException, ServletException, IOException {
    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
    PrenotazioneBean prenotazioneBean = (PrenotazioneBean) PrenotazioneDao
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


  //TC_1.3_03
  @Test
  public void updateDataTestSuccesfull()
          throws ParseException, SQLException, ServletException, IOException {

    String[] dates = DateUtils.dateToString(new Date()).split("/");
    String date = dates[2].replace("21", "2021") + "/" + dates[1] + "/" + dates[0];

    PrenotazioneBean prenotazioneBean =
            (PrenotazioneBean) PrenotazioneDao.doQuery(
                "doRetrieveByCode", "2938-0512105949-18/02/2021");

    request.addParameter("action", "modificaData");
    request.addParameter("codice", prenotazioneBean.getCodice());
    request.addParameter("data", date);

    servlet.doPost(request, response);
    assertEquals("/view/prenotazioni_effettuate/VisualizzaPrenotazioniView.jsp",
            response.getForwardedUrl());
  }

  @Test
  public void updateDataTestSuccesfull1()
      throws ParseException, SQLException, ServletException, IOException {

    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
    PrenotazioneBean prenotazioneBean =
        (PrenotazioneBean) PrenotazioneDao.doQuery(
            "doRetrieveByCode", "2932-0512105949-18/02/2021");

    prenotazioneBean.setData(df.parse("25/02/2021"));
    PrenotazioneDao.doQuery(PrenotazioneDao.doUpdateData, prenotazioneBean);

    request.addParameter("action", "modificaData");
    request.addParameter("codice", prenotazioneBean.getCodice());
    request.addParameter("data", "2021/02/28");
    servlet.doPost(request, response);
    assertEquals("/view/prenotazioni_effettuate/VisualizzaPrenotazioniView.jsp",
        response.getForwardedUrl());
  }

  @Test
  public void updateDataTestFail()
      throws ParseException, SQLException, ServletException, IOException {
    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
    PrenotazioneBean prenotazioneBean =
        (PrenotazioneBean) PrenotazioneDao.doQuery(
            "doRetrieveByCode", "2932-0512105949-18/02/2021");

    request.addParameter("action", "modificaData");
    request.addParameter("codice", prenotazioneBean.getCodice());
    request.addParameter("data", "2021/01/15");
    servlet.doPost(request, response);
    assertEquals("/view/prenotazioni_effettuate/VisualizzaPrenotazioniView.jsp",
        response.getForwardedUrl());
  }

  @Test
  public void prenotazioneNull()
      throws ParseException, SQLException, ServletException, IOException {

    PrenotazioneBean prenotazioneBean =
        (PrenotazioneBean) PrenotazioneDao.doQuery("doRetrieveByCode", "1");

    request.addParameter("action", "modificaData");
    request.addParameter("codice", prenotazioneBean.getCodice());
    request.addParameter("data", "2021/01/15");
    servlet.doPost(request, response);
    assertEquals("/view/prenotazioni_effettuate/VisualizzaPrenotazioniView.jsp",
        response.getForwardedUrl());
  }

  @Test
  public void updateDataTestFail2()
      throws ParseException, SQLException, ServletException, IOException {
    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
    PrenotazioneBean prenotazioneBean =
        (PrenotazioneBean) PrenotazioneDao.doQuery(
            "doRetrieveByCode", "2932-0512105949-18/02/2021");

    request.addParameter("action", "modificaData");
    request.addParameter("codice", prenotazioneBean.getCodice());
    request.addParameter("data", "2021/02/28");

    servlet.doPost(request, response);
    assertEquals("/view/prenotazioni_effettuate/VisualizzaPrenotazioniView.jsp",
        response.getForwardedUrl());
  }

  @Test
  public void updateDataTestFail3()
      throws ParseException, SQLException, ServletException, IOException {

    PrenotazioneBean prenotazioneBean =
        (PrenotazioneBean) PrenotazioneDao.doQuery(
            "doRetrieveByCode", "6028-0512105949-18/02/2021");

    request.addParameter("action", "modificaData");
    request.addParameter("codice", prenotazioneBean.getCodice());
    request.addParameter("data", "2021/02/28");
    servlet.doPost(request, response);
    assertEquals("/view/prenotazioni_effettuate/VisualizzaPrenotazioniView.jsp",
        response.getForwardedUrl());
  }

  @Test
  public void visualizzaPrenotazioniTest()
          throws ParseException, SQLException, ServletException, IOException {
    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
    PrenotazioneBean prenotazioneBean =
            (PrenotazioneBean) PrenotazioneDao.doQuery(
                "doRetrieveByCode", "10-0512354364-21/01/2021");

    request.addParameter("action", "visualizzaPrenotazioni");
    request.getSession().setAttribute("matricola", "0512105949");
    servlet.doPost(request, response);
    assertEquals("/view/prenotazioni_effettuate/VisualizzaPrenotazioniView.jsp",
            response.getForwardedUrl());
  }


  // TC_1.3_02
  @Test
  public void modificaDataPassata()
          throws ParseException, SQLException, ServletException, IOException {
    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
    PrenotazioneBean prenotazioneBean =
            (PrenotazioneBean) PrenotazioneDao.doQuery(
                "doRetrieveByCode", "5-4107147896-21/01/2021");

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
  public void exceptionSqlProva() {

    request.addParameter("action", "visualizzaPrenotazioni");
  }


  @Test
  public void getSinglePren()
          throws ParseException, SQLException, ServletException, IOException {
    PrenotazioneBean prenotazioneBean = new PrenotazioneBean();
    prenotazioneBean.setCodice(QrCodeGenerator.generateCode("0156835647", "2021/02/22"));
    prenotazioneBean.setCodiceAula("00");
    prenotazioneBean.setCodicePosto("00");
    prenotazioneBean.setSingolo(true);
    prenotazioneBean.setMatricolaStudente("0156835647");
    Date d = new Date("2021/02/22");

    prenotazioneBean.setData(d);
    PrenotazioneDao.doQuery(PrenotazioneDao.doSave, prenotazioneBean);

    request.addParameter("action", "getSinglePren");
    request.addParameter("cod", prenotazioneBean.getCodice());


    servlet.doPost(request, response);

    assertEquals("/view/prenotazioni_effettuate/ModificaPrenotazioniView.jsp",
            response.getForwardedUrl());

    PrenotazioneDao.doQuery(PrenotazioneDao.doDelete, prenotazioneBean.getCodice());
  }

  @Test
  public void modificaPrenotazionePrenNull() throws ServletException, IOException {
    request.addParameter("action", "modificaPrenotazione");
    request.addParameter("tipologia", "singolo");
    request.addParameter("codice", "");
    request.addParameter("data", "2021/03/17");
    servlet.doPost(request, response);
    assertEquals("Si è verificato un errore", request.getAttribute("error"));
    assertEquals("/view/prenotazioni_effettuate/VisualizzaPrenotazioniView.jsp",
        response.getForwardedUrl());


    ;
  }

  //TC_1.5_02
  @Test
  public void modificaPrenotazioneCanIupdateTrue()
      throws SQLException, ParseException, ServletException, IOException {
    PrenotazioneBean prenotazioneBean = new PrenotazioneBean();
    prenotazioneBean.setCodice(QrCodeGenerator.generateCode("0156835647", "2021/02/22"));
    prenotazioneBean.setCodiceAula("00");
    prenotazioneBean.setCodicePosto("00");
    prenotazioneBean.setSingolo(true);
    prenotazioneBean.setMatricolaStudente("0156835647");
    Date d = new Date("2021/03/29");

    prenotazioneBean.setData(d);
    PrenotazioneDao.doQuery(PrenotazioneDao.doSave, prenotazioneBean);


    request.addParameter("action", "modificaPrenotazione");
    request.addParameter("tipologia", "singolo");
    request.addParameter("codice", prenotazioneBean.getCodice());
    servlet.doPost(request, response);



    assertEquals("/view/prenotazioni_effettuate/VisualizzaPrenotazioniView.jsp",
            response.getForwardedUrl());

    PrenotazioneDao.doQuery(PrenotazioneDao.doDelete, prenotazioneBean.getCodice());



  }

  //TC_1.5_01

  @Test
  public void modificaPrenotazioneCanIupdateFalseGruppo()
      throws SQLException, ParseException, ServletException, IOException {
    PrenotazioneBean prenotazioneBean = new PrenotazioneBean();
    prenotazioneBean.setCodice(QrCodeGenerator.generateCode("0156835647", "2021/02/22"));
    prenotazioneBean.setCodiceAula("00");
    prenotazioneBean.setCodicePosto("00");
    prenotazioneBean.setSingolo(true);
    prenotazioneBean.setMatricolaStudente("0156835647");
    Date d = new Date("2021/04/18");

    prenotazioneBean.setData(d);
    PrenotazioneDao.doQuery(PrenotazioneDao.doSave, prenotazioneBean);


    request.addParameter("action", "modificaPrenotazione");
    request.addParameter("tipologia", "gruppo");
    request.addParameter("codice", prenotazioneBean.getCodice());
    servlet.doPost(request, response);



    assertEquals("/view/prenotazioni_effettuate/VisualizzaPrenotazioniView.jsp",
            response.getForwardedUrl());

    PrenotazioneDao.doQuery(PrenotazioneDao.doDelete, prenotazioneBean.getCodice());



  }

  @Test
  public void updateDataPrenotazionugualeOggiSingolo()
          throws ParseException, SQLException, ServletException, IOException {

    PrenotazioneBean prenotazioneBean = new PrenotazioneBean();
    //data di oggi
    Date dateTemp = new Date();
    String[] dates = DateUtils.dateToString(dateTemp).split("/");
    String date = dates[2].replace("21", "2021") + "/" + dates[1] + "/" + dates[0];
    System.out.println("TODAY:" + date);

    //codice della prenotazione autogenerato
    String codice = QrCodeGenerator.generateCode("0512105887", date);
    System.out.println(codice);

    //setto i parametri della prenotazione
    prenotazioneBean.setCodice(codice);
    prenotazioneBean.setMatricolaStudente("0512105887");
    prenotazioneBean.setData(new Date());
    prenotazioneBean.setCodiceAula("00");
    prenotazioneBean.setCodicePosto("00");
    prenotazioneBean.setSingolo(true);

    //aggiungo la prenotazione al DB
    PrenotazioneDao.doQuery(PrenotazioneDao.doSave, prenotazioneBean);



    System.out.println("data come parametro URL " + String.valueOf(dateTemp));
    request.addParameter("action", "modificaData");
    request.addParameter("codice", prenotazioneBean.getCodice());
    request.addParameter("data", date);
    servlet.doPost(request, response);
    assertEquals("/view/prenotazioni_effettuate/VisualizzaPrenotazioniView.jsp",
            response.getForwardedUrl());


    PrenotazioneDao.doQuery(PrenotazioneDao.doDelete, prenotazioneBean.getCodice());
  }




  @Test
  public void updateDataPrenotazionugualeOggiGruppo()
          throws ParseException, SQLException, ServletException, IOException {

    PrenotazioneBean prenotazioneBean = new PrenotazioneBean();
    //data di oggi
    Date dateTemp = new Date();
    String[] dates = DateUtils.dateToString(dateTemp).split("/");
    String date = dates[2].replace("21", "2021") + "/" + dates[1] + "/" + dates[0];
    System.out.println("TODAY:" + date);

    //codice della prenotazione autogenerato
    String codice = QrCodeGenerator.generateCode("0512105887", date);
    System.out.println(codice);

    //setto i parametri della prenotazione
    prenotazioneBean.setCodice(codice);
    prenotazioneBean.setMatricolaStudente("0512105887");
    prenotazioneBean.setData(new Date());
    prenotazioneBean.setCodiceAula("00");
    prenotazioneBean.setCodicePosto("00");
    prenotazioneBean.setSingolo(false);

    //aggiungo la prenotazione al DB
    PrenotazioneDao.doQuery(PrenotazioneDao.doSave, prenotazioneBean);



    System.out.println("data come parametro URL " + String.valueOf(dateTemp));
    request.addParameter("action", "modificaData");
    request.addParameter("codice", prenotazioneBean.getCodice());
    request.addParameter("data", date);
    servlet.doPost(request, response);
    assertEquals("/view/prenotazioni_effettuate/VisualizzaPrenotazioniView.jsp",
            response.getForwardedUrl());


    PrenotazioneDao.doQuery(PrenotazioneDao.doDelete, prenotazioneBean.getCodice());
  }

}

