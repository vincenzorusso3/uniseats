package dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.uniseats.model.beans.PostoBean;
import it.uniseats.model.dao.PostoDao;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class PostoDaoTest {

  @Test
  void fail() throws SQLException {
    PostoBean bean = (PostoBean) PostoDao.doQuery("metodochenonesiste", null);
    assertNull(bean);
  }

  @Test
  void doRetrieveByCodeTest() throws SQLException {
    PostoBean bean = (PostoBean) PostoDao.doQuery("doRetrieveByCode", "A1-01");
    PostoBean expected = new PostoBean("A1-01", "A1");
    assertTrue(expected.getCodice().equals(bean.getCodice())
        && expected.getCodiceAula().equals(bean.getCodiceAula()));
  }

  @Test
  void doRetrieveAll() throws SQLException {
    ArrayList<PostoBean> beans = (ArrayList<PostoBean>) PostoDao.doQuery("doRetrieveAll", null);
    assertNotNull(beans);
  }

  @Test
  void doRetrieveByAulaCodeTest() throws SQLException {
    ArrayList<PostoBean> beans =
        (ArrayList<PostoBean>) PostoDao.doQuery(PostoDao.doRetrieveByAulaCode, "A1");
    assertNotNull(beans);
  }


}
