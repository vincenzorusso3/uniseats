import it.uniseats.model.beans.StudenteBean;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class StudenteBeanTest {

    @Test
    void testStudentConstructorEmpty(){
        StudenteBean studente = new StudenteBean();
        assertNotNull(studente);
    }

    @Test
    void testGetNome(){
        StudenteBean studenteBean = new StudenteBean("Matteo", "Ercolino", "0512105933", "matteo.ercolino1@studenti.unisa.it", "ironman3000", 3, "Informatica");
        assertEquals("Matteo", studenteBean.getNome());
    }

    @Test
    void testGetCognome(){
        StudenteBean studenteBean = new StudenteBean("Matteo", "Ercolino", "0512105933", "matteo.ercolino1@studenti.unisa.it", "ironman3000", 3, "Informatica");
        assertEquals("Ercolino", studenteBean.getCognome());
    }

    @Test
    void testGetMatricola(){
        StudenteBean studenteBean = new StudenteBean("Matteo", "Ercolino", "0512105933", "matteo.ercolino1@studenti.unisa.it", "ironman3000", 3, "Informatica");
        assertEquals("0512105933", studenteBean.getMatricola());
    }

    @Test
    void testGetEmail(){
        StudenteBean studenteBean = new StudenteBean("Matteo", "Ercolino", "0512105933", "matteo.ercolino1@studenti.unisa.it", "ironman3000", 3, "Informatica");
        assertEquals("matteo.ercolino1@studenti.unisa.it", studenteBean.getEmail());
    }

    @Test
    void testGetPassword(){
        StudenteBean studenteBean = new StudenteBean("Matteo", "Ercolino", "0512105933", "matteo.ercolino1@studenti.unisa.it", "ironman3000", 3, "Informatica");
        assertEquals("ironman3000", studenteBean.getPassword());
    }

    @Test
    void testGetAnno(){
        StudenteBean studenteBean = new StudenteBean("Matteo", "Ercolino", "0512105933", "matteo.ercolino1@studenti.unisa.it", "ironman3000", 3, "Informatica");
        assertEquals(3, studenteBean.getAnno());
    }

    @Test
    void testGetDipartimento(){
        StudenteBean studenteBean = new StudenteBean("Matteo", "Ercolino", "0512105933", "matteo.ercolino1@studenti.unisa.it", "ironman3000", 3, "Informatica");
        assertEquals("Informatica", studenteBean.getDipartimento());
    }






}
