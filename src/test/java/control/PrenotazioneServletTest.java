package control;

import static org.junit.jupiter.api.Assertions.assertEquals;

import it.uniseats.control.gestioneprenotazione.PrenotazioneServlet;
import it.uniseats.model.beans.PrenotazioneBean;
import it.uniseats.model.beans.StudenteBean;
import it.uniseats.model.dao.PrenotazioneDao;
import it.uniseats.model.dao.StudenteDao;
import it.uniseats.utils.QrCodeGenerator;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import org.junit.jupiter.api.BeforeAll;
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

    ArrayList<PrenotazioneBean> prenotazioniLaurati = (ArrayList<PrenotazioneBean>)
        PrenotazioneDao.doQuery("doFindPrenotazioni", "0156835647");

    if (!prenotazioniLaurati.isEmpty()) {
      PrenotazioneBean prenotazioni = prenotazioniLaurati.get(0);
      PrenotazioneDao.doQuery("doDelete", prenotazioni.getCodice());
    }
  }


  @BeforeAll
  public static void registra60() throws SQLException, ParseException {
    StudenteBean studenteBean = new StudenteBean();
    int matricola = 2000;


    PrenotazioneBean prenotazioneBean = new PrenotazioneBean();
    for (int i = 0; i < 60; i++) {
      matricola++;
      studenteBean.setAnno(2);
      studenteBean.setCognome("Test" + i);
      studenteBean.setNome("Prova" + i);
      studenteBean.setDipartimento("Informatica");
      studenteBean.setMatricola("051210" + (matricola));
      studenteBean.setEmail("ProvaTest" + i + "@studenti.unisa.it");
      studenteBean.setPassword("password" + i);
      StudenteDao.doQuery(StudenteDao.doSave, studenteBean);
      prenotazioneBean.setCodice(
          QrCodeGenerator.generateCode(studenteBean.getMatricola(), "2021/03/22"));
      prenotazioneBean.setCodiceAula("00");
      prenotazioneBean.setCodicePosto("00");
      prenotazioneBean.setSingolo(true);
      prenotazioneBean.setMatricolaStudente("051210" + (matricola));
      Date d = new Date("2021/03/22");

      prenotazioneBean.setData(d);
      PrenotazioneDao.doQuery(PrenotazioneDao.doSave, prenotazioneBean);
    }
  }



  @BeforeEach
  public void oneWaySetup() throws ServletException {
    ServletConfig sg = new MockServletConfig();
    servlet.init(sg);
  }

  // TC_1.4_03
  @Test
  void prenotazioneSingolaTest() throws ServletException, IOException, SQLException,
      ParseException {

    request.addParameter("action", "prenotazioneSingola");
    request.addParameter("dateValueSingolo", "2021-02-11");
    request.getSession().setAttribute("email", "a.laurati@studenti.unisa.it");
    servlet.doPost(request, response);
    assertEquals("/view/prenotazionieffettuate/VisualizzaPrenotazioniView.jsp",
        response.getForwardedUrl());

  }

  @Test
  void prenotazioneGruppoTest() throws ServletException, IOException, SQLException, ParseException {
    request.addParameter("action", "prenotazioneGruppo");
    request.addParameter("dateValueGruppo", "2021/02/18");
    request.getSession().setAttribute("email", "a.laurati@studenti.unisa.it");
    servlet.doPost(request, response);

    assertEquals("/view/prenotazionieffettuate/VisualizzaPrenotazioniView.jsp",
        response.getForwardedUrl());

  }

  //TC_1.4_02
  @Test
  void prenotazioneEsistenteTest() throws ServletException, IOException {
    request.addParameter("action", "prenotazioneGruppo");
    request.addParameter("dateValueGruppo", "2021/02/18");
    request.getSession().setAttribute("email", "a.sabia15@studenti.unisa.it");
    servlet.doPost(request, response);
    assertEquals("Hai già una prenotazione per questa data!", request.getAttribute("errore"));

  }

  //TC_1.4_01
  @Test
  void prenotazioneInvalidDateTest() throws ServletException, IOException {
    request.addParameter("action", "prenotazioneGruppo");
    request.addParameter("dateValueGruppo", "2021/01/12");
    request.getSession().setAttribute("email", "a.sabia15@studenti.unisa.it");
    servlet.doPost(request, response);
    assertEquals("La data scelta non è valida!", request.getAttribute("errore"));

  }

  @Test
  void prenotazioneAuleFull() throws ServletException, IOException, SQLException, ParseException {
    request.addParameter("action", "prenotazioneGruppo");
    request.addParameter("dateValueGruppo", "2021/03/22");
    request.getSession().setAttribute("email", "a.sabia15@studenti.unisa.it");
    servlet.doPost(request, response);
    assertEquals("Nessun posto disponibile per la data selezionata!",
        request.getAttribute("errore"));


    int matricola = 2000;
    StudenteBean studenteBean = new StudenteBean();
    for (int i = 0; i < 60; i++) {
      matricola++;
      studenteBean.setAnno(2);
      studenteBean.setCognome("Test" + i);
      studenteBean.setNome("Prova" + i);
      studenteBean.setDipartimento("Informatica");

      studenteBean.setMatricola("051210" + (matricola));
      studenteBean.setEmail("ProvaTest" + i + "@studenti.unisa.it");
      studenteBean.setPassword("password" + i);





      StudenteBean delete = (StudenteBean) StudenteDao.doQuery(StudenteDao.doRetrieveByMatricola,
          studenteBean.getMatricola());
      StudenteDao.doQuery(StudenteDao.doDelete, delete.getMatricola());
    }
  }








}
