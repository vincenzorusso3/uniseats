import it.uniseats.model.beans.StudenteBean;
import it.uniseats.model.dao.StudenteDAO;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudenteDAOTest {

    @Test
    public void doQueryTest1() throws SQLException {
        StudenteBean bean = (StudenteBean) StudenteDAO.doQuery("doRetrieveByMatricola","0512105949");
        assertEquals(bean.getMatricola(), "0512105949");
    }



}

