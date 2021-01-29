package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.uniseats.model.beans.PrenotazioneBean;
import it.uniseats.model.dao.PrenotazioneDao;
import it.uniseats.utils.DateUtils;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import org.junit.jupiter.api.Test;

class PrenotazioneDaoTest {

  @Test
  void fail() throws SQLException, ParseException {
    ArrayList<PrenotazioneBean> beans = (ArrayList<PrenotazioneBean>)
        PrenotazioneDao.doQuery("metodonontrovato", null);
    assertNull(beans);
  }

  @Test
  void doRetrieveByCodeTest() throws SQLException, ParseException {

    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
    PrenotazioneBean expected =
        new PrenotazioneBean("5-0512105933-25/12/2021", df.parse("25/12/2021"), false, "00", "00",
            "0512105933");
    PrenotazioneDao.doQuery(PrenotazioneDao.doSave, expected);

    PrenotazioneBean prenotazione =
        (PrenotazioneBean) PrenotazioneDao.doQuery("doRetrieveByCode", "5-0512105933-25/12/2021");

    assertTrue(expected.getCodice().equals(prenotazione.getCodice())
        && expected.isSingolo() == prenotazione.isSingolo()
        && expected.getCodicePosto().equals(prenotazione.getCodicePosto())
        && expected.getCodiceAula().equals(prenotazione.getCodiceAula())
        && expected.getMatricolaStudente().equals(prenotazione.getMatricolaStudente()));

    PrenotazioneDao.doQuery(PrenotazioneDao.doDelete, expected.getCodice());

  }

  @Test
  void doRetrieveByCodeFailTest() throws SQLException, ParseException {
    PrenotazioneBean prenotazione =
        (PrenotazioneBean) PrenotazioneDao.doQuery("doRetrieveByCode", "dhjkah");
    assertNull(prenotazione.getCodice());
  }

  @Test
  void doFindPrenotazioniTest() throws SQLException, ParseException {
    ArrayList<PrenotazioneBean> prenotazioni =
        (ArrayList<PrenotazioneBean>) PrenotazioneDao.doQuery("doFindPrenotazioni", "0512105933");
    assertNotNull(prenotazioni);
  }

  @Test
  void doRetrieveAllTest() throws SQLException, ParseException {
    ArrayList<PrenotazioneBean> prenotazioni =
        (ArrayList<PrenotazioneBean>) PrenotazioneDao.doQuery("doRetrieveAll", null);
    assertNotNull(prenotazioni);
  }

  @Test
  void doDeleteTest() throws SQLException, ParseException {
    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
    PrenotazioneBean bean =
        new PrenotazioneBean("1-11111111-111111", df.parse("25/12/2021"), true, "00", "00",
            "0512105933");
    PrenotazioneDao.doQuery("doSave", bean);

    PrenotazioneBean expected =
        (PrenotazioneBean) PrenotazioneDao.doQuery("doRetrieveByCode", "1-11111111-111111");

    boolean i = (boolean) PrenotazioneDao.doQuery("doDelete", "1-11111111-111111");
    assertEquals(i, true);
  }

  @Test
  void doSaveTest() throws SQLException, ParseException {
    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
    PrenotazioneBean bean =
        new PrenotazioneBean("1-11111111-111111", df.parse("25/12/2021"), true, "00", "00",
            "0512105933");
    PrenotazioneDao.doQuery("doSave", bean);

    PrenotazioneBean expected =
        (PrenotazioneBean) PrenotazioneDao.doQuery("doRetrieveByCode", "1-11111111-111111");

    PrenotazioneDao.doQuery("doDelete", "1-11111111-111111");

    assertEquals(bean.getCodice(), expected.getCodice());
  }

  @Test
  void doUpdateDataTest() throws SQLException, ParseException {
    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
    PrenotazioneBean bean =
        new PrenotazioneBean("1-11111111-111111", df.parse("25/12/2021"), true, "00", "00",
            "0512105933");
    PrenotazioneDao.doQuery("doSave", bean);
    bean.setData(df.parse("26/12/2021"));
    int i = (int) PrenotazioneDao.doQuery("doUpdateData", bean);

    PrenotazioneDao.doQuery("doDelete", "1-11111111-111111");

    assertEquals(i, 1);

  }

  @Test
  void doUpdateAulaPosto() throws SQLException, ParseException {

    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
    PrenotazioneBean bean =
        new PrenotazioneBean("1-11111111-111111", df.parse("25/12/2021"), true, "00", "00",
            "0512105933");
    PrenotazioneDao.doQuery("doSave", bean);
    bean.setData(df.parse("26/12/2021"));
    int i = (int) PrenotazioneDao.doQuery("doUpdateAulaPosto", bean);

    PrenotazioneDao.doQuery("doDelete", "1-11111111-111111");

    assertEquals(i, 1);

  }

  @Test
  void doUpdateTipoTest() throws SQLException, ParseException {
    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
    PrenotazioneBean bean =
        new PrenotazioneBean("1-11111111-111111", df.parse("25/12/2021"), true, "00", "00",
            "0512105933");
    PrenotazioneDao.doQuery("doSave", bean);
    bean.setSingolo(false);
    int i = (int) PrenotazioneDao.doQuery("doUpdateTipo", bean);

    PrenotazioneDao.doQuery("doDelete", "1-11111111-111111");

    assertEquals(i, 1);
  }

  @Test
  void findByDataDipartimentoTest() throws SQLException, ParseException {

    Date date = new Date();
    ArrayList<String> parameter = new ArrayList<>();

    parameter.add(DateUtils.dateToString(date));
    parameter.add("Informatica");

    LinkedList<PrenotazioneBean> lista = (LinkedList<PrenotazioneBean>) PrenotazioneDao
        .doQuery(PrenotazioneDao.findByDataDipartimento, parameter);

    assertNotNull(lista);

  }


  @Test
  void findByDataDipartimentoNullTest() throws SQLException, ParseException {

    Date date = new Date();
    ArrayList<String> parameter = null;

    LinkedList<PrenotazioneBean> lista = (LinkedList<PrenotazioneBean>) PrenotazioneDao
        .doQuery(PrenotazioneDao.findByDataDipartimento, parameter);

    assertEquals(null, lista);

  }

}

