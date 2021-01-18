import it.uniseats.model.beans.PostoBean;
import it.uniseats.utils.DataSourceUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class PostoDAOTest {

    public static final String doRetrieveByCode = "doRetrieveByCode";
    public static final String doRetrieveAll = "doRetrieveAll";

    private static final String TABLE_NAME = "posti";
    private static final String DATASOURCE_ERROR = "[POSTODAO] Errore: il DataSource non risulta essere configurato correttamente";

    @Test
    public void doQuerydoRetrieveByCodeTest() throws SQLException {
        PostoBean posto = (PostoBean) doQuery("doRetrieveByCode", "A1-01");
        assertEquals(posto.getCodice(),"A1-01");
    }
    @Test
    public void doQuerydoRetrieveAllTest() throws SQLException {
        ArrayList<PostoBean> posto = (ArrayList<PostoBean>) doQuery("doRetrieveByCode", "A1-01");
        assertEquals(posto.get(0).getCodice(), "A1-01");
    }


    @Test
    public static synchronized Object doQuery(String methodName, Object parameter) throws SQLException {

        DataSource ds = DataSourceUtilsTest.getDataSource();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        if (ds != null) {

            String querySQL;
            try {
                connection = ds.getConnection();

                switch (methodName) {

                    case doRetrieveByCode:
                        querySQL = "SELECT * FROM " + TABLE_NAME + " WHERE codice=?";
                        preparedStatement = connection.prepareStatement(querySQL);
                        return doRetrieveByCode(preparedStatement, (String) parameter);

                    case doRetrieveAll:
                        querySQL = "SELECT * FROM " + TABLE_NAME;
                        preparedStatement = connection.prepareStatement(querySQL);
                        return doRetriveAll(preparedStatement);

                    default:
                        return null;

                }

            } finally {

                try {
                    if (preparedStatement != null)
                        preparedStatement.close();
                } finally {
                    if (connection != null)
                        connection.close();
                }

            }

        } else {
            System.out.println(DATASOURCE_ERROR);
            return null;
        }

    }
    @Test
    private static synchronized PostoBean doRetrieveByCode(PreparedStatement preparedStatement, String codice) throws SQLException {

        preparedStatement.setString(1, codice);
        ResultSet rs = preparedStatement.executeQuery();
        PostoBean postoBean = new PostoBean();

        while (rs.next()) {
            postoBean = getPostoInfo(rs);
        }

        return postoBean;

    }

    @Test
    private static synchronized ArrayList<PostoBean> doRetriveAll(PreparedStatement preparedStatement) throws SQLException {

        ArrayList<PostoBean> list = new ArrayList<>();
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            PostoBean postoBean = getPostoInfo(rs);
            list.add(postoBean);
        }

        return list;

    }


    private static PostoBean getPostoInfo(ResultSet rs) throws SQLException {

        PostoBean postoBean = new PostoBean();

        postoBean.setCodice(rs.getString("codice"));
        postoBean.setCodiceAula(rs.getString("codiceAula"));

        return postoBean;
    }


}

