package it.uniseats.model.dao;


import it.uniseats.model.beans.PostoBean;
import it.uniseats.utils.DataSourceUtils;
import it.uniseats.utils.DriverManagerConnectionPool;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Classe DAO che ci permette di effettuare operazioni sul PostoBean
 */
public class PostoDAO {

    public static final String doRetrieveByCode = "doRetrieveByCode";
    public static final String doRetrieveAll = "doRetrieveAll";

    private static final String TABLE_NAME = "posti";
    private static final String DATASOURCE_ERROR = "[POSTODAO] Errore: il DataSource non risulta essere configurato correttamente";

    /**
     * Metodo per effettuare query
     * @param methodName nome della <b>query</b>
     * @param parameter <b>parametro</b> passato alla query
     * @return un <b>object</b>
     * @throws SQLException
     */
    public static synchronized Object doQuery(String methodName, Object parameter) throws SQLException {


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

                    default:
                        return null;

                }

            } finally {

                try {
                    if (preparedStatement != null)
                        preparedStatement.close();
                } finally {
                    DriverManagerConnectionPool.releaseConnection(connection);
                }

            }

        }


    /**
     * Query che effettua una ricerca per codice di un Posto
     * @param preparedStatement <b>query SQL</b>
     * @param codice il <b>codice</b> delposto  che si intende cercare
     * @return il posto associato al codice
     * @throws SQLException
     */
    private static synchronized PostoBean doRetrieveByCode(PreparedStatement preparedStatement, String codice) throws SQLException {

        preparedStatement.setString(1, codice);
        ResultSet rs = preparedStatement.executeQuery();
        PostoBean postoBean = new PostoBean();

        while (rs.next()) {
            postoBean = getPostoInfo(rs);
        }

        return postoBean;

    }

    /**
     * Query che effettua una ricerca di tutti i posti presenti nel database
     * @param preparedStatement <b>query SQL</b>
     * @return una lista di Posti
     * @throws SQLException
     */
    private static synchronized ArrayList<PostoBean> doRetriveAll(PreparedStatement preparedStatement) throws SQLException {

        ArrayList<PostoBean> list = new ArrayList<>();
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            PostoBean postoBean = getPostoInfo(rs);
            list.add(postoBean);
        }

        return list;

    }

    /**
     * Metodo per ottenere le informazioni su un Posto
     * @param rs ResultSet
     * @return un postoBean
     * @throws SQLException
     */
    private static PostoBean getPostoInfo(ResultSet rs) throws SQLException {

        PostoBean postoBean = new PostoBean();

        postoBean.setCodice(rs.getString("codice"));
        postoBean.setCodiceAula(rs.getString("codiceAula"));

        return postoBean;
    }


}

