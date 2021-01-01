package it.uniseats.model.dao;

import it.uniseats.model.beans.AulaBean;
import it.uniseats.model.beans.PostoBean;
import it.uniseats.model.beans.StudenteBean;
import it.uniseats.utils.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PostoDAO {
    private static final String TABLE_NAME = "posti";
    private static final String DATASOURCE_ERROR = "[POSTODAO] Errore: il DataSource non risulta essere configurato correttamente";
    private static final DataSource ds = DataSourceUtils.getDataSource();

    public synchronized PostoBean doRetrieveByCode(String codice) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE codice=?";

        if (ds != null) {

            try {
                connection = ds.getConnection();
                preparedStatement = connection.prepareStatement(selectSQL);
                preparedStatement.setString(1, codice);
                ResultSet rs = preparedStatement.executeQuery();

                PostoBean postoBean = new PostoBean();

                while (rs.next()) {
                    postoBean = getPostoInfo(rs);
                }

                return postoBean;

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

    public synchronized ArrayList<PostoBean> doRetriveAll() throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String selectSQL = "SELECT * FROM " + TABLE_NAME;

        ArrayList<PostoBean> list = new ArrayList<>();

        if (ds != null) {

            try {
                connection = ds.getConnection();
                preparedStatement = connection.prepareStatement(selectSQL);
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {

                    PostoBean postoBean = getPostoInfo(rs);
                    list.add(postoBean);
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

    public synchronized int doSave(PostoBean posto) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;


        String insertSql="INSERT INTO "+TABLE_NAME+" (codice, codiceAula) VALUES (?,?)";

        if (ds != null) {

            try {
                connection = ds.getConnection();

                AulaBean aula = posto.getAula();
                //TODO getAula();

                preparedStatement.setString(1, posto.getCodice());
                //preparedStatement.setString(2, posto.getAula());



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



    //TODO doDelete()
    //TODO queries

    private PostoBean getPostoInfo(ResultSet rs) throws SQLException {

        PostoBean postoBean = new PostoBean();

        postoBean.setCodice(rs.getString("codice"));
        //TODO setAula

        return postoBean;
    }



}

