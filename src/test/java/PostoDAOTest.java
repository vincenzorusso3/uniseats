import it.uniseats.model.beans.PostoBean;
import it.uniseats.model.dao.PostoDAO;
import it.uniseats.utils.SHA512Utils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class PostoDAOTest {

    @Test
    void doRetrieveByCodeTest() throws SQLException {
        PostoBean bean = (PostoBean) PostoDAO.doQuery("doRetrieveByCode","A1-01");
        PostoBean expected = new PostoBean("A1-01", "A1");
        assertTrue(expected.getCodice().equals(bean.getCodice()) && expected.getCodiceAula().equals(bean.getCodiceAula()));
     }

     @Test
    void doRetrieveAll() throws SQLException {
         ArrayList <PostoBean> beans = (ArrayList<PostoBean>) PostoDAO.doQuery("doRetrieveAll", null);
         assertNotNull(beans);
     }
}
