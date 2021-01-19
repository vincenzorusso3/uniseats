import it.uniseats.model.beans.StudenteBean;
import it.uniseats.model.dao.StudenteDAO;
import it.uniseats.utils.SHA512Utils;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class StudenteDAOTest {

    @Test
    void doRetrieveByMatricolaTest() throws SQLException {
        StudenteBean bean = (StudenteBean) StudenteDAO.doQuery("doRetrieveByMatricola","0512105933");
        StudenteBean expected = new StudenteBean("Matteo", "Ercolino", "0512105933", "m.ercolino1@studenti.unisa.it", "AlessiaLove3000", 3, "Informatica");
        assertTrue(expected.getMatricola().equals(bean.getMatricola()) && expected.getNome().equals(bean.getNome()) &&
                expected.getCognome().equals(bean.getCognome()) && expected.getEmail().equals(bean.getEmail()) && SHA512Utils.getSHA512(expected.getPassword()).equals(bean.getPassword()) &&
                expected.getAnno() == bean.getAnno() && expected.getDipartimento().equals(bean.getDipartimento()));
    }

    @Test
    void doRetrieveAllTest() throws SQLException {
        ArrayList<StudenteBean> studenti = (ArrayList<StudenteBean>) StudenteDAO.doQuery("doRetrieveAll",null);
        assertNotNull(studenti);
    }

    @Test
    void doRetrieveByEmailTest() throws SQLException {
        StudenteBean bean = (StudenteBean) StudenteDAO.doQuery("doRetrieveByEmail","m.ercolino1@studenti.unisa.it");
        StudenteBean expected = new StudenteBean("Matteo", "Ercolino", "0512105933", "m.ercolino1@studenti.unisa.it", "AlessiaLove3000", 3, "Informatica");
        assertTrue(expected.getMatricola().equals(bean.getMatricola()) && expected.getNome().equals(bean.getNome()) &&
                expected.getCognome().equals(bean.getCognome()) && expected.getEmail().equals(bean.getEmail()) && SHA512Utils.getSHA512(expected.getPassword()).equals(bean.getPassword()) &&
                expected.getAnno() == bean.getAnno() && expected.getDipartimento().equals(bean.getDipartimento()));
    }

    @Test
    void doRetrieveByMatricolaFailTest() throws SQLException {
        StudenteBean bean = (StudenteBean) StudenteDAO.doQuery("doRetrieveByMatricola","hdhjs");
        assertNull(bean.getMatricola());
    }

    @Test
    void doRetrieveByEmailFailTest() throws SQLException {
        StudenteBean bean = (StudenteBean) StudenteDAO.doQuery("doRetrieveByEmail","hdhjs");
        assertNull(bean.getEmail());
    }

}

