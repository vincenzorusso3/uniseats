package dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.uniseats.model.beans.AulaBean;
import it.uniseats.model.dao.AulaDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

/**
 * Classe di testing per AulaDAO.
 */
class AulaDAOTest {

  @Test
  void doRetrieveByCodeTest() throws SQLException {
    AulaBean bean = (AulaBean) AulaDAO.doQuery("doRetrieveByCode", "A1");
    AulaBean expected = new AulaBean("A1", "Scienze Aziendali", 20, "D1");
    assertNotNull(bean);
    assertTrue(expected.getCodice().equals(bean.getCodice())
        && expected.getDipartimento().equals(bean.getDipartimento())
        && expected.getnPosti() == bean.getnPosti()
        && expected.getEdificio().equals(bean.getEdificio()));
  }

  @Test
  void doRetrieveAll() throws SQLException {
    ArrayList<AulaBean> beans = (ArrayList<AulaBean>) AulaDAO.doQuery("doRetrieveAll", null);
    assertNotNull(beans);
  }

  @Test
  void doRetrieveAllDip() throws SQLException {
    ArrayList<AulaBean> beans = (ArrayList<AulaBean>) AulaDAO.doQuery("doRetrieveAll", "Informatica");
    assertNotNull(beans);
  }

  @Test
  void fail() throws SQLException {
    ArrayList<AulaBean> beans = (ArrayList<AulaBean>) AulaDAO.doQuery("metodonontrovato", null);
    assertNull(beans);
  }

  @Test
  void getDipartimenti() throws SQLException {
    ArrayList<String> dipartimenti = (ArrayList<String>) AulaDAO.doQuery("getDipartimenti","Infromatica" );
    assertNotNull(dipartimenti);
  }

}
