import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.uniseats.model.beans.PostoBean;
import it.uniseats.model.dao.PostoDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class PostoDAOTest {

  @Test
  void doRetrieveByCodeTest() throws SQLException {
    PostoBean bean = (PostoBean) PostoDAO.doQuery("doRetrieveByCode", "A1-01");
    PostoBean expected = new PostoBean("A1-01", "A1");
    assertTrue(expected.getCodice().equals(bean.getCodice()) &&
        expected.getCodiceAula().equals(bean.getCodiceAula()));
  }

  @Test
  void doRetrieveAll() throws SQLException {
    ArrayList<PostoBean> beans = (ArrayList<PostoBean>) PostoDAO.doQuery("doRetrieveAll", null);
    assertNotNull(beans);
  }

  @Test
  void doRetrieveByAulaCodeTest() throws SQLException {
    ArrayList<PostoBean> beans = (ArrayList<PostoBean>) PostoDAO.doQuery(PostoDAO.doRetrieveByAulaCode, "A1");
    assertNotNull(beans);
  }



}
