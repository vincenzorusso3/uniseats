package it.uniseats.model.dao;

import it.uniseats.model.beans.AulaBean;

import it.uniseats.utils.DriverManagerConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe <code>AulaDAO</code> che ci permette di effettuare operazioni su AulaBean
 */
public class AulaDAO {

    public static final String doRetrieveByCode = "doRetrieveByCode";
    public static final String doRetrieveAll = "doRetrieveAll";
    public static final String getDipartimenti = "getDipartimenti";

    private static final String TABLE_NAME = "aula";
    private static final String DATASOURCE_ERROR = "[AULADAO] Errore: il DataSource non risulta essere configurato correttamente";

    /**
     * Metodo per effettuare le query
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

                    case getDipartimenti:
                        querySQL = "SELECT DISTINCT dipartimento FROM " + TABLE_NAME + " WHERE dipartimento!=Temp";
                        preparedStatement = connection.prepareStatement(querySQL);
                        return getDipartimenti(preparedStatement);





                    case doRetrieveAll:
                        if (parameter == null)
                            querySQL = "SELECT * FROM " + TABLE_NAME;
                        else
                            querySQL = "SELECT * FROM " + TABLE_NAME + " WHERE dipartimento=?";
                        preparedStatement = connection.prepareStatement(querySQL);
                        return doRetriveAll(preparedStatement, (String) parameter);

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
     * Query per effettuare una ricerca per codice di un AulaBean
     * @param preparedStatement <b>query SQL</b>
     * @param codice <b>codice</b> dell'aula che si vuole cercare
     * @return l'<b>aula</b> associata a quel codice
     * @throws SQLException
     */
    private static synchronized AulaBean doRetrieveByCode(PreparedStatement preparedStatement, String codice) throws SQLException {

        preparedStatement.setString(1, codice);
        ResultSet rs = preparedStatement.executeQuery();

        AulaBean aulaBean = new AulaBean();

        while (rs.next()) {
            aulaBean = getAulaInfo(rs);
        }

        return aulaBean;

    }

    /**
     * Metodo per cercare tutte le aule di un dato dipartimento
     * @param preparedStatement <b>query SQL</b>
     * @param dipartimento quale aule continene un dato <b>dipartimento</b>
     * @return tutte le <b>aule</b> di un dato dipartimento
     * @throws SQLException
     */
    private static synchronized ArrayList<AulaBean> doRetriveAll(PreparedStatement preparedStatement, String dipartimento) throws SQLException {

        if (dipartimento != null)
            preparedStatement.setString(1, dipartimento);

        ArrayList<AulaBean> list = new ArrayList<>();
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            AulaBean aulaBean = getAulaInfo(rs);
            list.add(aulaBean);
        }

        return list;

    }

    /**
     * Metodo per le info di un'aula
     * @param rs ResultSet
     * @return <b>aulaBean</b>
     * @throws SQLException
     */
    private static AulaBean getAulaInfo(ResultSet rs) throws SQLException {

        AulaBean aulaBean = new AulaBean();

        aulaBean.setCodice(rs.getString("codice"));
        aulaBean.setDipartimento(rs.getString("dipartimento"));
        aulaBean.setnPosti(rs.getInt("numPosti"));
        aulaBean.setEdificio(rs.getString("edificio"));

        return aulaBean;
    }

    /**
     * Metodo per cercare tutti i dipartimenti
     * @param preparedStatement <b>query SQL</b>
     * @return lista di tutti i  <b>dipartimenti</b>
     * @throws SQLException
     */
    private static synchronized ArrayList<String> getDipartimenti(PreparedStatement preparedStatement) throws SQLException {


        ArrayList<String> list = new ArrayList<>();
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            String dipartimento =rs.getString("dipartimento") ;
            list.add(dipartimento);
        }

        return list;

    }






}
