import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataSourceUtilsTest {

    public static DataSource getDataSource() {

        DataSource ds = null;

        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");

            ds = (DataSource) envCtx.lookup("jdbc/uniseatsTest");

        } catch (NamingException e) {
            System.out.println("Error:" + e.getMessage());
        }

        return ds;
    }

}
