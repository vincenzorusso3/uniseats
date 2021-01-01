package it.uniseats.model.dao;

import it.uniseats.utils.DataSourceUtils;
import it.uniseats.model.beans.StudenteBean;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class StudenteDAO {

    private static final String TABLE_NAME = "studente";
    private static final String DATASOURCE_ERROR = "[STUDENTEDAO] Errore: il DataSource non risulta essere configurato correttamente";
    private static final DataSource ds = DataSourceUtils.getDataSource();

    public synchronized StudenteBean doRetrieveByMatricola(String matricola) throws SQLException {

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

    public synchronized ArrayList<StudenteBean> doRetriveAll() throws SQLException {

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

    public synchronized int doSave(StudenteBean studente) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;


        String insertSql="INSERT INTO "+TABLE_NAME+" (anno, cognome, dipartimento, email, matricola, nome, password ) VALUES (?,?,?,?,?,?,?)";

        if (ds != null) {

            try {
                connection = ds.getConnection();
                preparedStatement.setInt(1, studente.getAnno());
                preparedStatement.setString(2, studente.getCognome());
                preparedStatement.setString(3, studente.getDipartimento());
                preparedStatement.setString(4, studente.getEmail());
                preparedStatement.setString(5, studente.getMatricola());
                preparedStatement.setString(6, studente.getNome());
                preparedStatement.setString(7, studente.getPassword());


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

    public synchronized int doUpdate(StudenteBean studenteBean) throws SQLException{

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String updateSQL = "UPDATE" + TABLE_NAME + "SET anno=? WHERE matricola=?";

        if(ds!=null){
            try{
                connection = ds.getConnection();

                preparedStatement.setInt(1, studenteBean.getAnno());

                preparedStatement.executeUpdate();
            }
            finally{
                try{
                    if(preparedStatement != null)
                        preparedStatement.close();
                } finally {
                    if(connection != null)
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
