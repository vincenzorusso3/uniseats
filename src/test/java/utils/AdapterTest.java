package utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.uniseats.model.beans.PrenotazioneBean;
import it.uniseats.model.beans.StudenteBean;
import it.uniseats.model.dao.PrenotazioneDao;
import it.uniseats.model.dao.StudenteDao;
import it.uniseats.utils.Adapter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import org.junit.jupiter.api.Test;

class AdapterTest {

  @Test
  void todayScheduleTest() throws ParseException, CloneNotSupportedException, SQLException {
    assertTrue(Adapter.todaySchedule());
  }

  @Test
  void listenerTest() throws ParseException, SQLException, CloneNotSupportedException {

    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
    PrenotazioneBean prenotazione =
        new PrenotazioneBean("1-11111111-111111", df.parse("25/12/2021"), true, "00", "00",
            "0512105933");
    PrenotazioneDao.doQuery("doSave", prenotazione);

    StudenteBean studente = new StudenteBean("TestNome", "TestCognome", "0512103231",
        "testnome.testcognome@studenti.unisa.it", "testnomecognome", 2, "Matematica");
    StudenteDao.doQuery("doSave", studente);

    assertTrue(Adapter.listener(prenotazione, studente));

    StudenteDao.doQuery("doDelete", studente.getMatricola());
    PrenotazioneDao.doQuery("doDelete", "1-11111111-111111");

  }

  @Test
  void listenerFailedTest() throws ParseException, SQLException, CloneNotSupportedException {

    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
    PrenotazioneBean prenotazione =
        new PrenotazioneBean("1-1212121-111111", df.parse("28/10/2021"), true, "00", "00",
            "0512105933");

    StudenteBean studente = new StudenteBean("TestNome", "TestCognome", "0512103231",
        "testnome.testcognome@studenti.unisa.it", "testnomecognome", 2, "Matematica");


    assertFalse(Adapter.listener(prenotazione, studente));




  }

  @Test
  void prenotazioneGiornoCorrenteTest()
      throws ParseException, SQLException, CloneNotSupportedException {

    PrenotazioneBean prenotazione =
        new PrenotazioneBean("1-11111111-111111", new Date(), true, "00", "00",
            "0512105933");

    PrenotazioneDao.doQuery("doSave", prenotazione);

    StudenteBean studente = new StudenteBean("TestNome", "TestCognome", "0512103231",
        "testnome.testcognome@studenti.unisa.it", "testnomecognome", 2, "Matematica");
    StudenteDao.doQuery("doSave", studente);

    assertTrue(Adapter.listener(prenotazione, studente));

    StudenteDao.doQuery("doDelete", studente.getMatricola());
    PrenotazioneDao.doQuery("doDelete", "1-11111111-111111");

  }

}