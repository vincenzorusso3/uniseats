package it.uniseats.model.dao;

import it.uniseats.utils.DataSourceUtils;
import it.uniseats.model.beans.StudenteBean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudenteDAO {

    private static final String TABLE_NAME = "studente";
    private static final String DATASOURCE_ERROR = "[STUDENTEMODEL] Errore: il DataSource non risulta essere configurato correttamente";
    private static final DataSource ds = DataSourceUtils.getDataSource();

    public synchronized StudenteBean getSingleStudent(String matricola) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE matricola=?";

        if (ds != null) {

            try {
                connection = ds.getConnection();
                preparedStatement = connection.prepareStatement(selectSQL);
                preparedStatement.setString(1, matricola);
                ResultSet rs = preparedStatement.executeQuery();

                StudenteBean studenteBean = new StudenteBean();

                while (rs.next()) {
                    studenteBean = getStudentInfo(rs);
                }

                return studenteBean;

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

    public synchronized ArrayList<StudenteBean> getStudentsList() throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String selectSQL = "SELECT * FROM " + TABLE_NAME;

        ArrayList<StudenteBean> list = new ArrayList<>();

        if (ds != null) {

            try {
                connection = ds.getConnection();
                preparedStatement = connection.prepareStatement(selectSQL);
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {

                    StudenteBean studenteBean = getStudentInfo(rs);
                    list.add(studenteBean);
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


    private StudenteBean getStudentInfo(ResultSet rs) throws SQLException {

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
