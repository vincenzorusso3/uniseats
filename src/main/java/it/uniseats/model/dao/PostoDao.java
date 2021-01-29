package it.uniseats.model.dao;


import it.uniseats.model.beans.PostoBean;
import it.uniseats.utils.DriverManagerConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Classe <code>PostoDao</code> che ci permette di
 * effettuare operazioni sulla tabella posti del database
 * E' possibile leggere i posti presenti nel databse.
 */
public class PostoDao {

  public static final String doRetrieveByCode = "doRetrieveByCode";
  public static final String doRetrieveAll = "doRetrieveAll";
  public static final String doRetrieveByAulaCode = "doRetrieveByAulaCode";

  private static final String TABLE_NAME = "posti";
  private static final String DATASOURCE_ERROR =
      "[POSTODAO] Errore: il DataSource non risulta essere configurato correttamente";

  /**
   * Metodo per effettuare le operazioni nel database.
   *
   * @param methodName nome della <b>operazione</b>
   * @param parameter  <b>parametro</b> passato alla operazione
   * @return un <b>object</b>
   * @throws SQLException se si verifica una eccezione
   */
  public static synchronized Object doQuery(String methodName, Object parameter)
      throws SQLException {


    Connection connection = null;
    PreparedStatement preparedStatement = null;
    String querySQL;

    try {
      connection = DriverManagerConnectionPool.getConnection();

      switch (methodName) {

        case doRetrieveByCode:
          querySQL = "SELECT * FROM " + TABLE_NAME + " WHERE codice=?";
          preparedStatement = connection.prepareStatement(querySQL);
          return doRetrieveByCode(preparedStatement, (String) parameter);

        case doRetrieveAll:
          querySQL = "SELECT * FROM " + TABLE_NAME;
          preparedStatement = connection.prepareStatement(querySQL);
          return doRetriveAll(preparedStatement);

        case doRetrieveByAulaCode:
          querySQL = "SELECT * FROM UniSeats.posti WHERE codiceAula = ?;";
          preparedStatement = connection.prepareStatement(querySQL);
          return doRetrieveByAulaCode(preparedStatement, (String) parameter);

        default:
          return null;

      }

    } finally {

      if (preparedStatement != null) {
        preparedStatement.close();
      }

      DriverManagerConnectionPool.releaseConnection(connection);

    }

  }

  /**
   * Metodo per la ricerca dei posti di un aula.
   *
   * @param preparedStatement <b>doRetrieveByAulaCode</b>, nome della operazione
   * @param parameter <d>codice</d> dell'aula che contiene il posto
   * @return un object
   * @throws SQLException se si verifica una eccezione
   */
  private static Object doRetrieveByAulaCode(PreparedStatement preparedStatement, String parameter)
      throws SQLException {

    preparedStatement.setString(1, parameter);
    ResultSet rs = preparedStatement.executeQuery();
    ArrayList<PostoBean> posti = new ArrayList<>();

    while (rs.next()) {
      PostoBean postoBean = getPostoInfo(rs);
      posti.add(postoBean);
    }

    return posti;

  }


  /**
   * Metodo che effettua una ricerca per codice di un Posto.
   *
   * @param preparedStatement <b>doRetrieveByCode</b>, nome della operazione
   * @param codice <b>chiave primaria</b> del posto nel database che si intende cercare
   * @return il posto associato al codice
   * @throws SQLException se si verifica una eccezione
   */
  private static synchronized PostoBean doRetrieveByCode(PreparedStatement preparedStatement,
                                                         String codice) throws SQLException {

    preparedStatement.setString(1, codice);
    ResultSet rs = preparedStatement.executeQuery();
    PostoBean postoBean = new PostoBean();

    while (rs.next()) {
      postoBean = getPostoInfo(rs);
    }

    return postoBean;

  }

  /**
   * Metodo che effettua una ricerca di tutti i posti presenti nel database.
   *
   * @param preparedStatement <b>doRetrieveAll</b>, nome della operazione
   * @return una lista di Posti
   * @throws SQLException se si verifica una eccezione
   */
  private static synchronized ArrayList<PostoBean> doRetriveAll(PreparedStatement preparedStatement)
      throws SQLException {

    ArrayList<PostoBean> list = new ArrayList<>();
    ResultSet rs = preparedStatement.executeQuery();

    while (rs.next()) {
      PostoBean postoBean = getPostoInfo(rs);
      list.add(postoBean);
    }

    return list;

  }

  /**
   * Metodo per ottenere le informazioni su un Posto.
   *
   * @param rs ResultSet
   * @return un postoBean
   * @throws SQLException se si verifica una eccezione
   */
  private static PostoBean getPostoInfo(ResultSet rs) throws SQLException {

    PostoBean postoBean = new PostoBean();

    postoBean.setCodice(rs.getString("codice"));
    postoBean.setCodiceAula(rs.getString("codiceAula"));

    return postoBean;
  }


}

