package it.uniseats.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Un oggetto DriverManagerConnectionPool viene utilizzato per la creazione di una connessione.
 */

public class DriverManagerConnectionPool {

  private static final List<Connection> freeDbConnections;

  static {
    freeDbConnections = new LinkedList<>();
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      System.out.println("DB driver not found:" + e.getMessage());
    }
  }

  /**
   * Metodo per creare la connessione.
   *
   * @return una <b>connection</b>
   * @throws SQLException se si verifica una eccezione
   */
  private static synchronized Connection createDbConnection() throws SQLException {
    Connection newConnection;
    String ip = "localhost";
    String port = "3306";
    String db = "uniseats";
    String username = "root";
    String password = "admin";

    newConnection = DriverManager.getConnection("jdbc:mysql://" + ip + ":"
        + port + "/" + db + "?serverTimezone=UTC", username, password);

    System.out.println("Create a new DB connection");
    newConnection.setAutoCommit(true);
    return newConnection;
  }

  /**
   * Metodo per fare il get di una Connection.
   *
   * @return una <b>connection</b>
   * @throws SQLException se si verifica una eccezione
   */
  public static synchronized Connection getConnection() throws SQLException {
    Connection connection;

    if (!freeDbConnections.isEmpty()) {
      connection = freeDbConnections.get(0);
      freeDbConnections.remove(0);

      try {
        if (connection.isClosed()) {
          connection = getConnection();
        }
      } catch (SQLException e) {
        connection.close();
        connection = getConnection();
      }
    } else {
      connection = createDbConnection();
    }

    return connection;
  }

  /**
   * Metodo per aggiungere una connessione alla lista di connessioni.
   *
   * @param connection una <b>connessione</b>
   */
  public static synchronized void releaseConnection(Connection connection) {
    if (connection != null) {
      freeDbConnections.add(connection);
    }
  }
}

