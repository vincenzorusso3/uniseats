package utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import it.uniseats.utils.DriverManagerConnectionPool;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class DriverManagerConnectionPoolTest {

  @Test
  void getConnectionTest() throws SQLException {
    Connection connection = DriverManagerConnectionPool.getConnection();
    assertNotNull(connection);
    DriverManagerConnectionPool.releaseConnection(connection);
  }

}