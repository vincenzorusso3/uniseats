package it.uniseats.model.dao;


import it.uniseats.model.beans.PrenotazioneBean;
import it.uniseats.utils.DateUtils;
import it.uniseats.utils.DriverManagerConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Classe <code>PrenotazioneDAO</code> che ci permette di
 * effettuare operazioni sulla tabella prenotazioni del database
 * Le operazioni che è possibile effettuare sono le operazioni CRUD
 * E' possibile creare una prenotazione, leggere le prenotazioni presenti nel databse,
 * cancellare una prenotazione e modificare una prenotazione.
 */
public class PrenotazioneDAO {

  public static final String doRetrieveByCode = "doRetrieveByCode";
  public static final String doFindPrenotazioni = "doFindPrenotazioni";
  public static final String doRetrieveAll = "doRetrieveAll";
  public static final String doSave = "doSave";
  public static final String doUpdateData = "doUpdateData";
  public static final String doUpdateTipo = "doUpdateTipo";
  public static final String doDelete = "doDelete";
  public static final String findByDataDipartimento = "findByDataDipartimento";
  public static final String doUpdateAulaPosto = "doUpdateAulaPosto";
  public static final Long day1 = 86400000L;


  private static final String TABLE_NAME = "prenotazione";
  private static final String DATASOURCE_ERROR =
      "[PRENOTAZIONEDAO] Errore: il DataSource non risulta essere configurato correttamente";

  /**
   * Metodo per effettuare le operazioni CRUD.
   *
   * @param methodName nome delLa <b>operazione</b>
   * @param parameter  <b>parametro</b> passato alla operazione
   * @return un <b>object</b>
   * @throws SQLException se si verifica una eccezione
   */
  public static synchronized Object doQuery(String methodName, Object parameter)
      throws SQLException, ParseException {

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

        case doFindPrenotazioni:
          querySQL = "SELECT codice, dataPrenotazione, codiceAula, codicePosto, tipologia FROM "
              + TABLE_NAME + " WHERE matricolaStudente=?";
          preparedStatement = connection.prepareStatement(querySQL);
          return doFindPrenotazioni(preparedStatement, (String) parameter);

        case doRetrieveAll:
          querySQL = "SELECT * FROM " + TABLE_NAME;
          preparedStatement = connection.prepareStatement(querySQL);
          return doRetrieveAll(preparedStatement);

        case doSave:
          querySQL = "INSERT INTO " + TABLE_NAME
              +
              " (codice, dataPrenotazione, tipologia, codicePosto,"
              + "codiceAula, matricolaStudente) VALUES (?,?,?,?,?,?)";
          preparedStatement = connection.prepareStatement(querySQL);
          return doSave(preparedStatement, (PrenotazioneBean) parameter);

        case doUpdateData:
          querySQL = "UPDATE " + TABLE_NAME
              + " SET dataPrenotazione=?, codiceAula='00', codicePosto='00'  WHERE codice=?";
          preparedStatement = connection.prepareStatement(querySQL);
          return doUpdateData(preparedStatement, (PrenotazioneBean) parameter);

        case doUpdateTipo:
          querySQL = "UPDATE " + TABLE_NAME + " SET tipologia=?  WHERE codice=?";
          preparedStatement = connection.prepareStatement(querySQL);
          return doUpdateTipo(preparedStatement, (PrenotazioneBean) parameter);

        case doUpdateAulaPosto:
          querySQL =
              "UPDATE " + TABLE_NAME + " SET codiceAula = ?, codicePosto = ? WHERE codice = ?";
          preparedStatement = connection.prepareStatement(querySQL);
          return doUpdateAulaPosto(preparedStatement, (PrenotazioneBean) parameter);

        case doDelete:
          querySQL = "DELETE FROM " + TABLE_NAME + " WHERE codice=?";
          preparedStatement = connection.prepareStatement(querySQL);
          return doDelete(preparedStatement, (String) parameter);

        case findByDataDipartimento:
          querySQL = "SELECT * FROM " + TABLE_NAME
              + " WHERE dataPrenotazione = ? "
              + "AND exists ( "
              + "SELECT * FROM studente AS s "
              + "WHERE s.dipartimento = ? AND matricolaStudente = s.matricola "
              + ")";
          preparedStatement = connection.prepareStatement(querySQL);
          return findByDataDipartimento(preparedStatement, (ArrayList<String>) parameter);

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
   * Metodo per modificare una prenotazione
   * assegnando ad una prenotazione il codice aula e il codice posto.
   *
   * @param preparedStatement <b>doUpdateAulaPosto</b>, che è il nome della operazione
   * @param parameter <b>oggetto Prenotazione</b> che deve essere modificato.
   * @return object la prenotazione aggiornata
   * @throws SQLException se si verifica una eccezione
   */
  private static synchronized Object doUpdateAulaPosto(PreparedStatement preparedStatement,
                                                       PrenotazioneBean parameter)
      throws SQLException {

    String aula = parameter.getCodiceAula();
    String posto = parameter.getCodicePosto();
    String codice = parameter.getCodice();

    preparedStatement.setString(1, aula);
    preparedStatement.setString(2, posto);
    preparedStatement.setString(3, codice);

    return preparedStatement.executeUpdate();


  }

  /**
   * Metodo per la ricerca di prenotazioni da parte di studenti
   * di uno stesso dipartimento in una stessa data.
   *
   * @param preparedStatement <b>findByDataDipartimento</b>, nome della operazione
   * @param parameter <b>Arraylist contenente data e dipartimento</b>
   * @return una collezione di prenotazioni
   * @throws ParseException se si verifica una eccezione
   * @throws SQLException se si verifica una eccezione
   *
   *
   */

  private static synchronized Collection<PrenotazioneBean> findByDataDipartimento(
      PreparedStatement preparedStatement, ArrayList<String> parameter)
      throws ParseException, SQLException {

    Collection<PrenotazioneBean> prenotazioni = new LinkedList<PrenotazioneBean>();

    if (parameter != null) {

      java.util.Date data = DateUtils.parseDate(parameter.get(0));
      String dip = parameter.get(1);

      java.sql.Date date = new java.sql.Date(data.getTime());
      date.setTime(data.getTime() + day1);

      preparedStatement.setDate(1, date);
      preparedStatement.setString(2, dip);

      ResultSet rs = preparedStatement.executeQuery();

      while (rs.next()) {
        prenotazioni.add(getPrenotazioneInfo(rs));
      }

      return prenotazioni;

    } else {
      return null;
    }

  }

  /**
   * Metodo per effettuare una ricerca di prenotazioni data la matricola di uno studente.
   *
   * @param preparedStatement <b>doFindPrenotazioni</b>, nome della operazione
   * @param matricola   <b>matricola</b> dello studente
   * @return  <b>lista delle prenotazioni</b> di un dato studente
   * @throws SQLException se si verifica una eccezione
   *
   */
  private static synchronized ArrayList<PrenotazioneBean> doFindPrenotazioni(
      PreparedStatement preparedStatement, String matricola) throws SQLException {

    ArrayList<PrenotazioneBean> prenotazioni = new ArrayList<>();

    preparedStatement.setString(1, matricola);

    ResultSet rs = preparedStatement.executeQuery();

    while (rs.next()) {
      PrenotazioneBean bean = new PrenotazioneBean();
      bean.setCodice(rs.getString("codice"));
      bean.setData(rs.getDate("dataPrenotazione"));
      bean.setCodiceAula(rs.getString("codiceAula"));
      bean.setCodicePosto(rs.getString("codicePosto"));
      bean.setSingolo(rs.getBoolean("tipologia"));

      prenotazioni.add(bean);

    }

    return prenotazioni;

  }

  /**
   * Metodo per cercare una prenotazione per codice.
   *
   * @param preparedStatement <b>doRetrieveByCode</b>, nome della operazione
   * @param codice  <b>chiave primaria</b> della prenotazione da ricercare
   * @return <b>prenotazione</b> con un dato codice
   * @throws SQLException se si verifica un'eccezione
   */
  private static synchronized PrenotazioneBean doRetrieveByCode(PreparedStatement preparedStatement,
                                                                String codice) throws SQLException {

    preparedStatement.setString(1, codice);
    ResultSet rs = preparedStatement.executeQuery();
    PrenotazioneBean prenotazioneBean = new PrenotazioneBean();

    while (rs.next()) {
      prenotazioneBean = getPrenotazioneInfo(rs);
    }

    return prenotazioneBean;

  }

  /**
   * Metodo per cercare tutte le prenotazioni.
   *
   * @param preparedStatement <b>doRetrieveAll</b>, nome della operazione
   * @return lista di tutte le <b>prenotazioni</b> presenti nel database
   * @throws SQLException se si verifica una eccezione
   */
  private static synchronized ArrayList<PrenotazioneBean> doRetrieveAll(
      PreparedStatement preparedStatement) throws SQLException {

    ArrayList<PrenotazioneBean> list = new ArrayList<>();

    ResultSet rs = preparedStatement.executeQuery();

    while (rs.next()) {
      PrenotazioneBean prenotazioneBean = getPrenotazioneInfo(rs);
      list.add(prenotazioneBean);
    }

    return list;

  }

  /**
   * Metodo per il salvataggio di una prenotazione nel database.
   *
   * @param preparedStatement <b>doSave</b>, nome della operazione
   * @param prenotazioneBean  <b>prenotazione</b> da salvare
   * @return <b>1</b> = successo, <b>0</b>=fallimento
   * @throws SQLException se si verifica una eccezione
   */
  private static synchronized int doSave(PreparedStatement preparedStatement,
                                         PrenotazioneBean prenotazioneBean) throws SQLException {

    java.sql.Date date = new java.sql.Date(prenotazioneBean.getData().getTime());
    date.setTime(prenotazioneBean.getData().getTime() + day1);

    preparedStatement.setString(1, prenotazioneBean.getCodice());
    preparedStatement.setDate(2, date);
    preparedStatement.setBoolean(3, prenotazioneBean.isSingolo());
    preparedStatement.setString(4, prenotazioneBean.getCodicePosto());
    preparedStatement.setString(5, prenotazioneBean.getCodiceAula());
    preparedStatement.setString(6, prenotazioneBean.getMatricolaStudente());

    return preparedStatement.executeUpdate();

  }

  /**
   * Metodo per modificare la data di una prenotazione.
   *
   * @param preparedStatement <b>doUpdateData</b>, nome della operazione
   * @param prenotazioneBean  <b>prenotazione</b> da modificare
   * @return <b>1</b> = successo, <b>0</b>=fallimento
   * @throws SQLException se si verifica una eccezione
   */

  private static synchronized int doUpdateData(PreparedStatement preparedStatement,
                                               PrenotazioneBean prenotazioneBean)
      throws SQLException {

    java.sql.Date date = new java.sql.Date(prenotazioneBean.getData().getTime());
    date.setTime(prenotazioneBean.getData().getTime() + day1);

    preparedStatement.setDate(1, date);
    preparedStatement.setString(2, prenotazioneBean.getCodice());

    return preparedStatement.executeUpdate();

  }

  /**
   * Metodo per modificare tipo di una prenotazione.
   *
   * @param preparedStatement <b>doUpdateTipo</b>, nome della operazione
   * @param prenotazioneBean  <b>prenotazione</b> da modificare
   * @return <b>1</b> = successo, <b>0</b>=fallimento
   * @throws SQLException se si verifica una eccezione
   */
  private static synchronized int doUpdateTipo(PreparedStatement preparedStatement,
                                               PrenotazioneBean prenotazioneBean)
      throws SQLException {

    preparedStatement.setBoolean(1, prenotazioneBean.isSingolo());
    preparedStatement.setString(2, prenotazioneBean.getCodice());

    return preparedStatement.executeUpdate();

  }


  /**
   * Metodo per eliminare una prenotazionoe.
   *
   * @param preparedStatement <b>doDelete</b>, nome della operazione
   * @param codice <b>chiave primaria</b> dellaa prenotazione da eliminare
   * @return <b>true</b>= successo, <b>false</b>=fallimento
   * @throws SQLException se si verifica una eccezione
   */
  private static synchronized boolean doDelete(PreparedStatement preparedStatement, String codice)
      throws SQLException {

    preparedStatement.setString(1, codice);
    return (preparedStatement.executeUpdate() != 0);

  }


  /**
   * Metodo per info di una prenotazione.
   *
   * @param rs <b>Result set</b>
   * @return <b>prenotazione</b>
   * @throws SQLException se si verifica una eccezione
   */
  private static PrenotazioneBean getPrenotazioneInfo(ResultSet rs) throws SQLException {

    PrenotazioneBean prenotazioneBean = new PrenotazioneBean();

    prenotazioneBean.setCodice(rs.getString("codice"));
    prenotazioneBean.setData(rs.getDate("dataPrenotazione"));
    prenotazioneBean.setSingolo(rs.getBoolean("tipologia"));
    prenotazioneBean.setMatricolaStudente(rs.getString("matricolaStudente"));
    prenotazioneBean.setCodicePosto(rs.getString("codicePosto"));
    prenotazioneBean.setCodiceAula(rs.getString("codiceAula"));

    return prenotazioneBean;

  }

}
