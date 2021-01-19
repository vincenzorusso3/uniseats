package it.uniseats.model.dao;

import it.uniseats.model.ClassModel;
import it.uniseats.model.beans.StudenteBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudenteModelDM implements ClassModel<StudenteBean> {

    @Override
    public StudenteBean doRetrieveByKey(String user) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Studente bean = new StudenteBean();

        String selectSQL = "SELECT * FROM studente WHERE username=?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, user);

            System.out.println("doRetrieveByKey:" + preparedStatement.toString());
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                bean.setUsername(rs.getString("username"));
                bean.setPassword(rs.getString("password"));
                bean.setGrado(rs.getString("grado"));
            }
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return bean;
    }
}
