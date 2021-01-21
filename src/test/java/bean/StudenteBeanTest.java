package bean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import it.uniseats.model.beans.StudenteBean;
import org.junit.jupiter.api.Test;

class StudenteBeanTest {

  /**
   * Constructors Testing.
   */

  @Test
  void testStudentConstructorEmpty() {
    StudenteBean studenteBean = new StudenteBean();
    assertNotNull(studenteBean);
  }

  @Test
  void testStudentConstructor() {
    StudenteBean studenteBean =
        new StudenteBean("Matteo", "Ercolino", "0512105933", "matteo.ercolino1@studenti.unisa.it",
            "ironman3000", 3, "Informatica");
    assertNotNull(studenteBean);
  }


  /**
   * Getter Methos Testing.
   */

  @Test
  void testGetNome() {
    StudenteBean studenteBean =
        new StudenteBean("Matteo", "Ercolino", "0512105933", "matteo.ercolino1@studenti.unisa.it",
            "ironman3000", 3, "Informatica");
    assertEquals("Matteo", studenteBean.getNome());
  }

  @Test
  void testGetCognome() {
    StudenteBean studenteBean =
        new StudenteBean("Matteo", "Ercolino", "0512105933", "matteo.ercolino1@studenti.unisa.it",
            "ironman3000", 3, "Informatica");
    assertEquals("Ercolino", studenteBean.getCognome());
  }

  @Test
  void testGetMatricola() {
    StudenteBean studenteBean =
        new StudenteBean("Matteo", "Ercolino", "0512105933", "matteo.ercolino1@studenti.unisa.it",
            "ironman3000", 3, "Informatica");
    assertEquals("0512105933", studenteBean.getMatricola());
  }

  @Test
  void testGetEmail() {
    StudenteBean studenteBean =
        new StudenteBean("Matteo", "Ercolino", "0512105933", "matteo.ercolino1@studenti.unisa.it",
            "ironman3000", 3, "Informatica");
    assertEquals("matteo.ercolino1@studenti.unisa.it", studenteBean.getEmail());
  }

  @Test
  void testGetPassword() {
    StudenteBean studenteBean =
        new StudenteBean("Matteo", "Ercolino", "0512105933", "matteo.ercolino1@studenti.unisa.it",
            "ironman3000", 3, "Informatica");
    assertEquals("ironman3000", studenteBean.getPassword());
  }

  @Test
  void testGetAnno() {
    StudenteBean studenteBean =
        new StudenteBean("Matteo", "Ercolino", "0512105933", "matteo.ercolino1@studenti.unisa.it",
            "ironman3000", 3, "Informatica");
    assertEquals(3, studenteBean.getAnno());
  }

  @Test
  void testGetDipartimento() {
    StudenteBean studenteBean =
        new StudenteBean("Matteo", "Ercolino", "0512105933", "matteo.ercolino1@studenti.unisa.it",
            "ironman3000", 3, "Informatica");
    assertEquals("Informatica", studenteBean.getDipartimento());
  }

  /**
   * Setter Methods Testing.
   */

  @Test
  void testSetNome() {
    StudenteBean studente = new StudenteBean();
    studente.setNome("Matteo");
    assertEquals("Matteo", studente.getNome());
  }

  @Test
  void testSetCognome() {
    StudenteBean studente = new StudenteBean();
    studente.setCognome("Ercolino");
    assertEquals("Ercolino", studente.getCognome());
  }

  @Test
  void testSetMatricola() {
    StudenteBean studente = new StudenteBean();
    studente.setMatricola("0512105933");
    assertEquals("0512105933", studente.getMatricola());
  }

  @Test
  void testSetEmail() {
    StudenteBean studente = new StudenteBean();
    studente.setEmail("matteo.ercolino1@studenti.unisa.it");
    assertEquals("matteo.ercolino1@studenti.unisa.it", studente.getEmail());
  }

  @Test
  void testSetPassword() {
    StudenteBean studente = new StudenteBean();
    studente.setPassword("ironman3000");
    assertEquals("ironman3000", studente.getPassword());
  }

  @Test
  void testSetAnno() {
    StudenteBean studente = new StudenteBean();
    studente.setAnno(3);
    assertEquals(3, studente.getAnno());
  }

  @Test
  void testSetDipartimento() {
    StudenteBean studente = new StudenteBean();
    studente.setDipartimento("Informatica");
    assertEquals("Informatica", studente.getDipartimento());
  }


}
