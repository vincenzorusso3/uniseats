package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.uniseats.model.beans.PrenotazioneBean;
import it.uniseats.model.dao.PrenotazioneDAO;
import it.uniseats.utils.DateUtils;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import org.junit.jupiter.api.Test;

class PrenotazioneDAOTest {

  @Test
  void doRetrieveByCodeTest() throws SQLException, ParseException {

    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
    PrenotazioneBean expected =
        new PrenotazioneBean("5-0512105933-25/12/2021", df.parse("25/12/2021"), false, "00", "00",
            "0512105933");
    PrenotazioneDAO.doQuery(PrenotazioneDAO.doSave, expected);

    PrenotazioneBean prenotazione =
        (PrenotazioneBean) PrenotazioneDAO.doQuery("doRetrieveByCode", "5-0512105933-25/12/2021");

    assertTrue(expected.getCodice().equals(prenotazione.getCodice())
        && expected.isSingolo() == prenotazione.isSingolo()
        && expected.getCodicePosto().equals(prenotazione.getCodicePosto())
        && expected.getCodiceAula().equals(prenotazione.getCodiceAula())
        && expected.getMatricolaStudente().equals(prenotazione.getMatricolaStudente()));

    PrenotazioneDAO.doQuery(PrenotazioneDAO.doDelete, expected.getCodice());

  }

  @Test
  void doRetrieveByCodeFailTest() throws SQLException, ParseException {
    PrenotazioneBean prenotazione =
        (PrenotazioneBean) PrenotazioneDAO.doQuery("doRetrieveByCode", "dhjkah");
    assertNull(prenotazione.getCodice());
  }

  @Test
  void doFindPrenotazioniTest() throws SQLException, ParseException {
    ArrayList<PrenotazioneBean> prenotazioni =
        (ArrayList<PrenotazioneBean>) PrenotazioneDAO.doQuery("doFindPrenotazioni", "0512105933");
    assertNotNull(prenotazioni);
  }

  @Test
  void doRetrieveAllTest() throws SQLException, ParseException {
    ArrayList<PrenotazioneBean> prenotazioni =
        (ArrayList<PrenotazioneBean>) PrenotazioneDAO.doQuery("doRetrieveAll", null);
    assertNotNull(prenotazioni);
  }

  @Test
  void doDeleteTest() throws SQLException, ParseException {
    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
    PrenotazioneBean bean =
        new PrenotazioneBean("1-11111111-111111", df.parse("25/12/2021"), true, "00", "00",
            "0512105933");
    PrenotazioneDAO.doQuery("doSave", bean);

    PrenotazioneBean expected =
        (PrenotazioneBean) PrenotazioneDAO.doQuery("doRetrieveByCode", "1-11111111-111111");

    boolean i = (boolean) PrenotazioneDAO.doQuery("doDelete", "1-11111111-111111");
    assertEquals(i, true);
  }

  @Test
  void doSaveTest() throws SQLException, ParseException {
    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
    PrenotazioneBean bean =
        new PrenotazioneBean("1-11111111-111111", df.parse("25/12/2021"), true, "00", "00",
            "0512105933");
    PrenotazioneDAO.doQuery("doSave", bean);

    PrenotazioneBean expected =
        (PrenotazioneBean) PrenotazioneDAO.doQuery("doRetrieveByCode", "1-11111111-111111");

    PrenotazioneDAO.doQuery("doDelete", "1-11111111-111111");

    assertEquals(bean.getCodice(), expected.getCodice());
  }

  @Test
  void doUpdateDataTest() throws SQLException, ParseException {
    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
    PrenotazioneBean bean =
        new PrenotazioneBean("1-11111111-111111", df.parse("25/12/2021"), true, "00", "00",
            "0512105933");
    PrenotazioneDAO.doQuery("doSave", bean);
    bean.setData(df.parse("26/12/2021"));
    int i = (int) PrenotazioneDAO.doQuery("doUpdateData", bean);

    PrenotazioneDAO.doQuery("doDelete", "1-11111111-111111");

    assertEquals(i, 1);

  }

  @Test
  void doUpdateTipoTest() throws SQLException, ParseException {
    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
    PrenotazioneBean bean =
        new PrenotazioneBean("1-11111111-111111", df.parse("25/12/2021"), true, "00", "00",
            "0512105933");
    PrenotazioneDAO.doQuery("doSave", bean);
    bean.setSingolo(false);
    int i = (int) PrenotazioneDAO.doQuery("doUpdateTipo", bean);

    PrenotazioneDAO.doQuery("doDelete", "1-11111111-111111");

    assertEquals(i, 1);
  }

  @Test
  void findByDataDipartimentoTest() throws SQLException, ParseException {

    Date date = new Date();
    ArrayList<String> parameter = new ArrayList<>();

    parameter.add(DateUtils.dateToString(date));
    parameter.add("Informatica");

    LinkedList<PrenotazioneBean> lista = (LinkedList<PrenotazioneBean>) PrenotazioneDAO
        .doQuery(PrenotazioneDAO.findByDataDipartimento, parameter);

    assertNotNull(lista);

  }

}

