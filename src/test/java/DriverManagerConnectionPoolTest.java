import it.uniseats.utils.DriverManagerConnectionPool;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DriverManagerConnectionPoolTest {

    @Test
    void getConnectionTest() throws SQLException {

        Connection connection = DriverManagerConnectionPool.getConnection();
        assertNotNull(connection);
        DriverManagerConnectionPool.releaseConnection(connection);

    }

}