package it.uniseats.model.dao;

import it.uniseats.model.beans.PrenotazioneBean;
import it.uniseats.model.beans.StudenteBean;
import it.uniseats.utils.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class StudenteDAO {

    private static final String TABLE_NAME = "studente";
    private static final String DATASOURCE_ERROR = "[STUDENTEDAO] Errore: il DataSource non risulta essere configurato correttamente";

    public static synchronized Object doQuery(String methodName, Object parameter) throws SQLException {

        DataSource ds = DataSourceUtils.getDataSource();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        if (ds != null) {

            String querySQL;

            try {
                connection = ds.getConnection();

                switch (methodName) {

                    case "doRetrieveByMatricola":
                        querySQL = "SELECT * FROM " + TABLE_NAME + " WHERE matricola=?";
                        preparedStatement = connection.prepareStatement(querySQL);
                        return doRetrieveByMatricola(preparedStatement, (String) parameter);

                    case "doRetrieveAll":
                        querySQL = "SELECT * FROM " + TABLE_NAME;
                        preparedStatement = connection.prepareStatement(querySQL);
                        return doRetriveAll(preparedStatement);

                    case "doSave":
                        querySQL = "INSERT INTO " + TABLE_NAME + " (anno, cognome, dipartimento, email, matricola, nome, password ) VALUES (?,?,?,?,?,?,?)";
                        preparedStatement = connection.prepareStatement(querySQL);
                        return doSave(preparedStatement, (StudenteBean) parameter);

                    case "doRetrieveByEmail":
                        querySQL = "SELECT * FROM " + TABLE_NAME + " WHERE email=?";
                        preparedStatement = connection.prepareStatement(querySQL);
                        return doRetrieveByEmail(preparedStatement, (String) parameter);

                    case "doUpdate":
                        querySQL = "UPDATE " + TABLE_NAME + " SET anno=? WHERE matricola=?";
                        preparedStatement = connection.prepareStatement(querySQL);
                        return doUpdate(preparedStatement, (StudenteBean) parameter);

                    case "doDelete":
                        querySQL = "DELETE FROM " + TABLE_NAME + " WHERE matricola=?";
                        preparedStatement = connection.prepareStatement(querySQL);
                        return doDelete(preparedStatement, (String) parameter);

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

    private static synchronized StudenteBean doRetrieveByMatricola(PreparedStatement preparedStatement, String matricola) throws SQLException {

        preparedStatement.setString(1, matricola);
        ResultSet rs = preparedStatement.executeQuery();
        StudenteBean studenteBean = new StudenteBean();

        while (rs.next()) {
            studenteBean = getStudentInfo(rs);
        }

        return studenteBean;

    }

    private static synchronized ArrayList<StudenteBean> doRetriveAll(PreparedStatement preparedStatement) throws SQLException {

        ArrayList<StudenteBean> list = new ArrayList<>();
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            StudenteBean studenteBean = getStudentInfo(rs);
            list.add(studenteBean);
        }

        return list;

    }

    private static synchronized int doSave(PreparedStatement preparedStatement, StudenteBean studente) throws SQLException {

        preparedStatement.setInt(1, studente.getAnno());
        preparedStatement.setString(2, studente.getCognome());
        preparedStatement.setString(3, studente.getDipartimento());
        preparedStatement.setString(4, studente.getEmail());
        preparedStatement.setString(5, studente.getMatricola());
        preparedStatement.setString(6, studente.getNome());
        preparedStatement.setString(7, studente.getPassword());

        return preparedStatement.executeUpdate();
    }

    private static synchronized StudenteBean doRetrieveByEmail(PreparedStatement preparedStatement, String email) throws SQLException {

        preparedStatement.setString(1, email);
        ResultSet rs = preparedStatement.executeQuery();
        StudenteBean studenteBean = new StudenteBean();

        while (rs.next()) {
            studenteBean = getStudentInfo(rs);
        }

        return studenteBean;

    }

    private static synchronized int doUpdate(PreparedStatement preparedStatement, StudenteBean studenteBean) throws SQLException {

        preparedStatement.setInt(1, studenteBean.getAnno());
        preparedStatement.setString(2, studenteBean.getMatricola());

        return preparedStatement.executeUpdate();

    }


    private static synchronized boolean doDelete(PreparedStatement preparedStatement, String matricola) throws SQLException {

        preparedStatement.setString(1, matricola);

        return (preparedStatement.executeUpdate() != 0);

    }

    private static StudenteBean getStudentInfo(ResultSet rs) throws SQLException {

        StudenteBean studenteBean = new StudenteBean();

        studenteBean.setNome(rs.getString("nome"));
        studenteBean.setCognome(rs.getString("cognome"));
        studenteBean.setMatricola(rs.getString("matricola"));
        studenteBean.setEmail(rs.getString("email"));
        studenteBean.setPassword(rs.getString("password"));
        studenteBean.setAnno(rs.getInt("anno"));
        studenteBean.setDipartimento(rs.getString("diparimento"));

        return studenteBean;
    }

}
