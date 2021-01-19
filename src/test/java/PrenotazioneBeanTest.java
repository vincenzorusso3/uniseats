import it.uniseats.model.beans.AulaBean;
import it.uniseats.model.beans.PrenotazioneBean;
import it.uniseats.model.beans.StudenteBean;
import org.junit.jupiter.api.Test;



import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PrenotazioneBeanTest{

    /**
     * Constructor Testing
     */
    @Test
    void testPrenotazioneConstructorEmpty(){
        PrenotazioneBean prenotazioneBean = new PrenotazioneBean();
        assertNotNull(prenotazioneBean);
    }
    @Test
    void testPrenotazioneConstructor() throws ParseException {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        Date date = df.parse("10/02/2021");
        PrenotazioneBean prenotazioneBean = new PrenotazioneBean("0001-0512105933",date, false, "1", "INF-01", "0512105933");
        assertNotNull(prenotazioneBean);
    }

    /**
     * Getter Methods Testing
     * @throws ParseException
     */
    @Test
    void testGetCodice() throws ParseException {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        Date date = df.parse("10/02/2021");
        PrenotazioneBean prenotazioneBean = new PrenotazioneBean("0001-0512105933",date, false, "1", "INF-01", "0512105933");
        assertEquals("0001-0512105933", prenotazioneBean.getCodice());

    }

    @Test
    void testGetData() throws ParseException {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        Date date = df.parse("10/02/2021");
        PrenotazioneBean prenotazioneBean = new PrenotazioneBean("0001-0512105933",date, false, "1", "INF-01", "0512105933");
        assertEquals(date, prenotazioneBean.getData());

    }

    @Test
    void testIsGruppo() throws ParseException {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        Date date = df.parse("10/02/2021");
        PrenotazioneBean prenotazioneBean = new PrenotazioneBean("0001-0512105933",date, false, "1", "INF-01", "0512105933");
        assertEquals(false, prenotazioneBean.isSingolo());

    }

    @Test
    void testGetMatricolaStudente() throws ParseException {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        Date date = df.parse("10/02/2021");
        PrenotazioneBean prenotazioneBean = new PrenotazioneBean("0001-0512105933",date, false, "1", "INF-01", "0512105933");
        assertEquals("0512105933", prenotazioneBean.getMatricolaStudente());

    }

    @Test
    void testGetCodiceAula() throws ParseException {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        Date date = df.parse("10/02/2021");
        PrenotazioneBean prenotazioneBean = new PrenotazioneBean("0001-0512105933",date, false, "1", "INF-01", "0512105933");
        assertEquals("INF-01", prenotazioneBean.getCodiceAula());
 }

    @Test
    void testGetCodicePosto() throws ParseException{
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        Date date = df.parse("10/02/2021");
        PrenotazioneBean prenotazioneBean = new PrenotazioneBean("0001-0512105933",date, false, "1", "INF-01", "0512105933");
        assertEquals("1", prenotazioneBean.getCodicePosto());

    }

    /**
     * Setter Methods Testing
     */
    @Test
    void testSetCodice(){
        PrenotazioneBean prenotazioneBean = new PrenotazioneBean();
        prenotazioneBean.setCodice("002");
        assertEquals("002", prenotazioneBean.getCodice());
    }

    @Test
    void testSetQrCode(){
        PrenotazioneBean prenotazioneBean = new PrenotazioneBean();
        prenotazioneBean.setCodice("0002-0512105949-19022021");
        assertEquals("0002-0512105949-19022021", prenotazioneBean.getCodice());
    }

    @Test
    void testSetData() throws ParseException {
        PrenotazioneBean prenotazioneBean = new PrenotazioneBean();
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        Date date = df.parse("19/02/2021");
        prenotazioneBean.setData(date);
        assertEquals(date, prenotazioneBean.getData());
    }

    @Test
    void testSetGruppo(){
        PrenotazioneBean prenotazioneBean = new PrenotazioneBean();
        prenotazioneBean.setSingolo(true);
        assertEquals(true, prenotazioneBean.isSingolo());
    }

    @Test
    void testSetStudente(){
        PrenotazioneBean prenotazioneBean = new PrenotazioneBean();
        prenotazioneBean.setMatricolaStudente("0512105949");
        assertEquals("0512105949", prenotazioneBean.getMatricolaStudente());
    }

    @Test
    void testSetCodiceAula(){
        PrenotazioneBean prenotazioneBean = new PrenotazioneBean();
        prenotazioneBean.setCodiceAula("INF-02");
        assertEquals("INF-02", prenotazioneBean.getCodiceAula());
    }

    @Test
    void testSetCodicePosto(){
        PrenotazioneBean prenotazioneBean = new PrenotazioneBean();
        prenotazioneBean.setCodicePosto("2");
        assertEquals("2", prenotazioneBean.getCodicePosto());
    }

}
