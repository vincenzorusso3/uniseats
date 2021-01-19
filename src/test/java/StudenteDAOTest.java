import it.uniseats.model.beans.StudenteBean;
import it.uniseats.model.dao.StudenteDAO;
import it.uniseats.utils.SHA512Utils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;

public class StudenteDAOTest {

    @Test
    public void doRetrieveByMatricolaTest() throws SQLException {
        StudenteBean bean = (StudenteBean) StudenteDAO.doQuery("doRetrieveByMatricola","0512105933");
        StudenteBean expected = new StudenteBean("Matteo", "Ercolino", "0512105933", "m.ercolino1@studenti.unisa.it", "AlessiaLove3000", 3, "Informatica");
        assertTrue(expected.getMatricola().equals(bean.getMatricola()) && expected.getNome().equals(bean.getNome()) &&
                expected.getCognome().equals(bean.getCognome()) && expected.getEmail().equals(bean.getEmail()) && SHA512Utils.getSHA512(expected.getPassword()).equals(bean.getPassword()) &&
                expected.getAnno() == bean.getAnno() && expected.getDipartimento().equals(bean.getDipartimento()));
    }

    @Test
    public void doRetrieveAllTest() throws SQLException {
        ArrayList<StudenteBean> studenti = (ArrayList<StudenteBean>) StudenteDAO.doQuery("doRetrieveAll",null);
        assertNotNull(studenti);
    }



}

