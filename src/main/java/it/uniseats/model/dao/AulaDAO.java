package it.uniseats.model.dao;

import it.uniseats.model.beans.AulaBean;

import it.uniseats.utils.DataSourceUtils;

import javax.sql.DataSource;
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

        DataSource ds = DataSourceUtils.getDataSource();
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
                    if (connection != null)
                        connection.close();
                }

            }

        } else {
            System.out.println(DATASOURCE_ERROR);
            return null;
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

}
