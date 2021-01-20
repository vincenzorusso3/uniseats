import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.uniseats.model.beans.PrenotazioneBean;
import it.uniseats.model.dao.PrenotazioneDAO;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;
import org.junit.jupiter.api.Test;

class PrenotazioneDAOTest {

  @Test
  void doRetrieveByCodeTest() throws SQLException, ParseException {
    PrenotazioneBean prenotazione =
        (PrenotazioneBean) PrenotazioneDAO.doQuery("doRetrieveByCode", "5-0512105933-21/01/2021");
    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
    PrenotazioneBean expected =
        new PrenotazioneBean("5-0512105933-21/01/2021", df.parse("21/01/2021"), true, "00", "00",
            "0512105933");
    assertTrue(expected.getCodice().equals(prenotazione.getCodice())
        && expected.isSingolo() == prenotazione.isSingolo()
        && expected.getCodicePosto().equals(prenotazione.getCodicePosto())
        && expected.getCodiceAula().equals(prenotazione.getCodiceAula())
        && expected.getMatricolaStudente().equals(prenotazione.getMatricolaStudente()));
  }

  @Test
  void doRetrieveByCodeFailTest() throws SQLException, ParseException {
    PrenotazioneBean prenotazione =
        (PrenotazioneBean) PrenotazioneDAO.doQuery("doRetrieveByCode", "dhjkah");
    assertNull(prenotazione.getCodice());
  }

  @Test
  void doFindPrenotazioniTest() throws SQLException, ParseException {
    LinkedList<PrenotazioneBean> prenotazioni =
        (LinkedList<PrenotazioneBean>) PrenotazioneDAO.doQuery("doFindPrenotazioni", "0512105933");
    assertNotNull(prenotazioni);
  }

  @Test
  void doRetrieveAllTest() throws SQLException, ParseException {
    ArrayList<PrenotazioneBean> prenotazioni =
        (ArrayList<PrenotazioneBean>) PrenotazioneDAO.doQuery("doRetrieveAll", null);
    assertNotNull(prenotazioni);
  }

}