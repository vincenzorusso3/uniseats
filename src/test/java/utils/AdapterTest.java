package utils;

import static it.uniseats.utils.Adapter.todaySchedule;
import static org.junit.jupiter.api.Assertions.*;

import it.uniseats.model.beans.PrenotazioneBean;
import it.uniseats.model.beans.StudenteBean;
import it.uniseats.model.dao.PrenotazioneDAO;
import it.uniseats.model.dao.StudenteDAO;
import it.uniseats.utils.Adapter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
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
    PrenotazioneDAO.doQuery("doSave", prenotazione);

    StudenteBean studente = new StudenteBean("TestNome", "TestCognome", "0512103231",
        "testnome.testcognome@studenti.unisa.it", "testnomecognome", 2, "Matematica");
    StudenteDAO.doQuery("doSave", studente);

    assertTrue(Adapter.listener(prenotazione, studente));

    StudenteDAO.doQuery("doDelete", studente.getMatricola());

  }

}