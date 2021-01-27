package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.uniseats.model.beans.PrenotazioneBean;
import it.uniseats.model.beans.StudenteBean;
import it.uniseats.model.dao.PrenotazioneDAO;
import it.uniseats.model.dao.StudenteDAO;
import it.uniseats.utils.SHA512Utils;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class StudenteDAOTest {

  @Test
  void fail() throws SQLException{
    ArrayList<StudenteBean> beans = (ArrayList<StudenteBean>) StudenteDAO
        .doQuery("metodonontrovato", null);
    assertNull(beans);
  }

  @Test
  void doRetrieveByMatricolaTest() throws SQLException {
    StudenteBean bean = (StudenteBean) StudenteDAO.doQuery("doRetrieveByMatricola", "0512105933");
    StudenteBean expected =
        new StudenteBean("Matteo", "Ercolino", "0512105933", "m.ercolino1@studenti.unisa.it",
            "AlessiaLove3000", 3, "Informatica");
    assertTrue(expected.getMatricola().equals(bean.getMatricola())
        && expected.getNome().equals(bean.getNome())
        && expected.getCognome().equals(bean.getCognome())
        && expected.getEmail().equals(bean.getEmail())
        && SHA512Utils.getSHA512(expected.getPassword()).equals(bean.getPassword())
        && expected.getAnno() == bean.getAnno()
        && expected.getDipartimento().equals(bean.getDipartimento()));
  }

  @Test
  void doRetrieveAllTest() throws SQLException {
    ArrayList<StudenteBean> studenti =
        (ArrayList<StudenteBean>) StudenteDAO.doQuery("doRetrieveAll", null);
    assertNotNull(studenti);
  }

  @Test
  void doRetrieveByEmailTest() throws SQLException {
    StudenteBean bean =
        (StudenteBean) StudenteDAO.doQuery("doRetrieveByEmail", "m.ercolino1@studenti.unisa.it");
    StudenteBean expected =
        new StudenteBean("Matteo", "Ercolino", "0512105933", "m.ercolino1@studenti.unisa.it",
            "AlessiaLove3000", 3, "Informatica");
    assertTrue(expected.getMatricola().equals(bean.getMatricola())
        && expected.getNome().equals(bean.getNome())
        && expected.getCognome().equals(bean.getCognome())
        && expected.getEmail().equals(bean.getEmail())
        && SHA512Utils.getSHA512(expected.getPassword()).equals(bean.getPassword())
        && expected.getAnno() == bean.getAnno()
        && expected.getDipartimento().equals(bean.getDipartimento()));
  }

  @Test
  void doRetrieveByMatricolaFailTest() throws SQLException {
    StudenteBean bean = (StudenteBean) StudenteDAO.doQuery("doRetrieveByMatricola", "hdhjs");
    assertNull(bean.getMatricola());
  }

  @Test
  void doRetrieveByEmailFailTest() throws SQLException {
    StudenteBean bean = (StudenteBean) StudenteDAO.doQuery("doRetrieveByEmail", "hdhjs");
    assertNull(bean.getEmail());
  }
  @Test
  void doSaveTest() throws SQLException {
    StudenteBean bean = new StudenteBean("TestNome", "TestCognome", "0512103231",
        "testnome.testcognome@studenti.unisa.it", "testnomecognome", 2, "Matematica");
    int i = (int) StudenteDAO.doQuery("doSave", bean);

    StudenteDAO.doQuery("doDelete", bean.getMatricola());

    assertEquals(i,1);
  }

  @Test
  void doDelete() throws SQLException {
    StudenteBean bean = new StudenteBean("TestNome", "TestCognome", "0512103231",
        "testnome.testcognome@studenti.unisa.it", "testnomecognome", 2, "Matematica");
    StudenteDAO.doQuery("doSave", bean);

    boolean i = (boolean)  StudenteDAO.doQuery("doDelete", bean.getMatricola());

    assertTrue(i);
  }

  @Test
  void doDeleteFailTest() throws SQLException {
    boolean i = (boolean) StudenteDAO.doQuery("doDelete", "00102124256");

    assertFalse(i);
  }

  @Test
  void doUpdateTest() throws SQLException {
    StudenteBean bean = new StudenteBean("TestNome", "TestCognome", "0512103231",
        "testnome.testcognome@studenti.unisa.it", "testnomecognome", 2, "Matematica");
    StudenteDAO.doQuery("doSave", bean);
    StudenteBean newBean = (StudenteBean) StudenteDAO.doQuery("doRetrieveByMatricola", "0512103231");
    newBean.setAnno(3);
    int i  = (int) StudenteDAO.doQuery("doUpdate", newBean);
    StudenteDAO.doQuery("doDelete", newBean.getMatricola());
    assertEquals(i, 1);



  }

}

