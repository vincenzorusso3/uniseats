package it.uniseats.model.dao;

import it.uniseats.model.beans.AulaBean;
import it.uniseats.model.beans.PostoBean;
import it.uniseats.model.beans.StudenteBean;
import it.uniseats.utils.DataSourceUtils;

import javax.sql.DataSource;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AulaDAO {
    private static final String TABLE_NAME = "aula";
    private static final String DATASOURCE_ERROR = "[AULADAO] Errore: il DataSource non risulta essere configurato correttamente";
    private static final DataSource ds = DataSourceUtils.getDataSource();

    public synchronized AulaBean doRetrieveByCode(String codice) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE codice=?";

        if (ds != null) {

            try {
                connection = ds.getConnection();
                preparedStatement = connection.prepareStatement(selectSQL);
                preparedStatement.setString(1, codice);
                ResultSet rs = preparedStatement.executeQuery();

                AulaBean aulaBean = new AulaBean();

                while (rs.next()) {
                    aulaBean = getAulaInfo(rs);
                }

                return aulaBean;

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

    public synchronized ArrayList<AulaBean> doRetriveAll() throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String selectSQL = "SELECT * FROM " + TABLE_NAME;

        ArrayList<AulaBean> list = new ArrayList<>();

        if (ds != null) {

            try {
                connection = ds.getConnection();
                preparedStatement = connection.prepareStatement(selectSQL);
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {

                    AulaBean aulaBean = getAulaInfo(rs);
                    list.add(aulaBean);
                }

                return list;

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

    public synchronized int doSave(AulaBean aula) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;


        String insertSql="INSERT INTO "+TABLE_NAME+" (codice, dipartimento, numPosti, edificio) VALUES (?,?,?,?)";

        if (ds != null) {

            try {
                connection = ds.getConnection();

                preparedStatement.setString(1, aula.getCodice());
                preparedStatement.setString(2, aula.getDipartimento());
                preparedStatement.setInt(3,aula.getnPosti());
                preparedStatement.setString(4,aula.getEdificio());




                preparedStatement.executeUpdate();


            }
            finally {

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
            return -1;
        }
        return 1;
    }

    public synchronized boolean doDelete(String codice) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int result = 0;

        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE codice=?";

        if (ds != null) {

            try {
                connection = ds.getConnection();
                preparedStatement = connection.prepareStatement(deleteSQL);
                preparedStatement.setString(1, codice);

                result = preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                JOptionPane.showMessageDialog(null, "Non puoi eliminare questa aula", "Exception", JOptionPane.INFORMATION_MESSAGE);
            } finally {

                try {
                    if (preparedStatement != null)
                        preparedStatement.close();
                } finally {
                    if (connection != null)
                        connection.close();
                }

            }
        }

        else {
            System.out.println(DATASOURCE_ERROR);
        }
        return (result !=0);
    }
    //TODO queries

    private AulaBean getAulaInfo(ResultSet rs) throws SQLException {

        AulaBean aulaBean = new AulaBean();

        aulaBean.setCodice(rs.getString("codice"));
        aulaBean.setDipartimento(rs.getString("dipartimento"));
        aulaBean.setnPosti(rs.getInt("numPosti"));
        aulaBean.setEdificio(rs.getString("edificio"));

        return aulaBean;
    }

}
