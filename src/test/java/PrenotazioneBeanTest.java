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
        StudenteBean studenteBean = new StudenteBean("Matteo", "Ercolino", "0512105933", "matteo.ercolino1@studenti.unisa.it", "ironman3000", 3, "Informatica");
        PrenotazioneBean prenotazioneBean = new PrenotazioneBean("0001","0001-0512105933",date, false, studenteBean);
        assertNotNull(prenotazioneBean);
    }

    /**
     * Getter Methods Testing
     * @throws ParseException
     */
    @Test
    void testGetCodice() throws ParseException {
        StudenteBean studenteBean = new StudenteBean("Matteo", "Ercolino", "0512105933", "matteo.ercolino1@studenti.unisa.it", "ironman3000", 3, "Informatica");
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        Date date = df.parse("10/02/2021");
        PrenotazioneBean prenotazioneBean = new PrenotazioneBean("0001", "0001-0512105933-10022021", date, false, studenteBean );
        assertEquals("0001", prenotazioneBean.getCodice());

    }


    @Test
    void getQrCode() throws ParseException {
        StudenteBean studenteBean = new StudenteBean("Matteo", "Ercolino", "0512105933", "matteo.ercolino1@studenti.unisa.it", "ironman3000", 3, "Informatica");
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        Date date = df.parse("10/02/2021");
        PrenotazioneBean prenotazioneBean = new PrenotazioneBean("0001", "0001-0512105933-10022021", date, false, studenteBean );
        assertEquals("0001-0512105933-10022021", prenotazioneBean.getQrCode());

    }

    @Test
    void getData() throws ParseException {
        StudenteBean studenteBean = new StudenteBean("Matteo", "Ercolino", "0512105933", "matteo.ercolino1@studenti.unisa.it", "ironman3000", 3, "Informatica");
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        Date date = df.parse("10/02/2021");
        PrenotazioneBean prenotazioneBean = new PrenotazioneBean("0001", "0001-0512105933-10022021", date, false, studenteBean );
        assertEquals(date, prenotazioneBean.getData());

    }

    @Test
    void isGruppo() throws ParseException {
        StudenteBean studenteBean = new StudenteBean("Matteo", "Ercolino", "0512105933", "matteo.ercolino1@studenti.unisa.it", "ironman3000", 3, "Informatica");
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        Date date = df.parse("10/02/2021");
        PrenotazioneBean prenotazioneBean = new PrenotazioneBean("0001", "0001-0512105933-10022021", date, false, studenteBean );
        assertEquals(false, prenotazioneBean.isGruppo());

    }

    @Test
    void getStudente() throws ParseException {
        StudenteBean studenteBean = new StudenteBean("Matteo", "Ercolino", "0512105933", "matteo.ercolino1@studenti.unisa.it", "ironman3000", 3, "Informatica");
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        Date date = df.parse("10/02/2021");
        PrenotazioneBean prenotazioneBean = new PrenotazioneBean("0001", "0001-0512105933-10022021", date, false, studenteBean );
        assertEquals(studenteBean, prenotazioneBean.getStudente());

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
        prenotazioneBean.setQrCode("0002-0512105949-19022021");
        assertEquals("0002-0512105949-19022021", prenotazioneBean.getQrCode());
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
        prenotazioneBean.setGruppo(true);
        assertEquals(true, prenotazioneBean.isGruppo());
    }

    @Test
    void testSetStudente(){
        PrenotazioneBean prenotazioneBean = new PrenotazioneBean();
        StudenteBean studenteBean = new StudenteBean("Alessia", "Sabia","0512105949", "a.sabia15@studenti.unisa.it", "rocket9696", 3, "Informatica" );
        prenotazioneBean.setStudente(studenteBean);
        assertEquals(studenteBean, prenotazioneBean.getStudente());
    }






}
