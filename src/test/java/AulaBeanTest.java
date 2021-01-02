import it.uniseats.model.beans.AulaBean;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AulaBeanTest {

    //inizio test costruttori

    @Test
    void testAulaEmptyConstructor() {
        AulaBean aula = new AulaBean();
        assertNotNull(aula);
    }

    @Test
    void testAulaConstructor() {
        AulaBean aula = new AulaBean("01","Informatica", 15, "F1");
        assertNotNull(aula);
    }

    //fine test costruttori

    //inizio test getter

    @Test
    void testGetCodice() {
        AulaBean aula = new AulaBean("01",null, 0, null);
        assertEquals("01", aula.getCodice());
    }

    @Test
    void testGetDipartimento() {
        AulaBean aula = new AulaBean("01","Informatica", 0, null);
        assertEquals("Informatica", aula.getDipartimento());
    }

    @Test
    void testGetNumeroPosti() {
        AulaBean aula = new AulaBean("01","Informatica", 15, null);
        assertEquals(15, aula.getnPosti());
    }

    @Test
    void testGetEdificio() {
        AulaBean aula = new AulaBean("01","Informatica", 15, "F1");
        assertEquals("F1", aula.getEdificio());
    }

    //fine test getter

    //inizio test setter

    @Test
    void testSetCodice() {
        AulaBean aula = new AulaBean();
        aula.setCodice("01");
        assertEquals("01", aula.getCodice());
    }

    @Test
    void testSetDipartimento() {
        AulaBean aula = new AulaBean();
        aula.setDipartimento("Informatica");
        assertEquals("Informatica", aula.getDipartimento());
    }

    @Test
    void testSetNumeroPosti() {
        AulaBean aula = new AulaBean();
        aula.setnPosti(15);
        assertEquals(15, aula.getnPosti());
    }

    @Test
    void testSetEdificio() {
        AulaBean aula = new AulaBean();
        aula.setEdificio("F1");
        assertEquals("F1", aula.getEdificio());
    }

}
