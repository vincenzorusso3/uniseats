package it.uniseats.model.dao;


import it.uniseats.model.beans.PostoBean;
import it.uniseats.model.beans.PrenotazioneBean;
import it.uniseats.model.beans.StudenteBean;
import it.uniseats.utils.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class PrenotazioneDAO {

    private static final String TABLE_NAME = "prenotazione";
    private static final String DATASOURCE_ERROR = "[PRENOTAZIONEDAO] Errore: il DataSource non risulta essere configurato correttamente";
    private static final DataSource ds = DataSourceUtils.getDataSource();

    public synchronized PrenotazioneBean doRetrieveByCode(String codice) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE codice=?";

        if (ds != null) {

            try {
                connection = ds.getConnection();
                preparedStatement = connection.prepareStatement(selectSQL);
                preparedStatement.setString(1, codice);
                ResultSet rs = preparedStatement.executeQuery();

                PrenotazioneBean prenotazioneBean = new PrenotazioneBean();

                while (rs.next()) {
                    prenotazioneBean = getPrenotazioneInfo(rs);
                }

                return prenotazioneBean;

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

    public synchronized ArrayList<PrenotazioneBean> doRetrieveAll(String matricolaStudente) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String selectSQL = "SELECT * FROM " + TABLE_NAME + "WHERE matricolaStudente=?";

        ArrayList<PrenotazioneBean> list = new ArrayList<>();

        if (ds != null) {

            try {
                connection = ds.getConnection();
                preparedStatement = connection.prepareStatement(selectSQL);
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {

                    PrenotazioneBean prenotazioneBean = getPrenotazioneInfo(rs);
                    list.add(prenotazioneBean);
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

    public synchronized int doSave(PrenotazioneBean prenotazioneBean) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;


        String insertSql="INSERT INTO "+TABLE_NAME+" (codice, matricolaStudente, dataPrenotazione, tipologia, qrCode) VALUES (?,?,?,?,?)";

        if (ds != null) {

            try {
                connection = ds.getConnection();
                preparedStatement.setString(1, prenotazioneBean.getCodice());
                preparedStatement.setString(2, prenotazioneBean.getStudente().getMatricola());
                preparedStatement.setDate(3, (Date) prenotazioneBean.getData());
                preparedStatement.setBoolean(4, prenotazioneBean.isGruppo());
                preparedStatement.setString(5, prenotazioneBean.getQrCode());



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

    public synchronized int doUpdateData(PrenotazioneBean prenotazioneBean) throws SQLException{

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String updateSQL = "UPDATE" + TABLE_NAME + "SET dataPrenotazione=?  WHERE codice=?";

        if(ds!=null){
            try{
                connection = ds.getConnection();

                preparedStatement.setDate(1, (Date) prenotazioneBean.getData());

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
    public synchronized int doUpdateTipo(PrenotazioneBean prenotazioneBean) throws SQLException{

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String updateSQL = "UPDATE" + TABLE_NAME + "SET tipologia=?  WHERE codice=?";

        if(ds!=null){
            try{
                connection = ds.getConnection();

                preparedStatement.setBoolean(1, prenotazioneBean.isGruppo());

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
    //Queries












    private PrenotazioneBean getPrenotazioneInfo(ResultSet rs) throws SQLException {

        PrenotazioneBean prenotazioneBean = new PrenotazioneBean();

        PostoBean posto = prenotazioneBean.getPosto();
        //TODO getPosto()

        StudenteBean studente = prenotazioneBean.getStudente();
        //TODO getStudente()

        prenotazioneBean.setQrCode(rs.getString("code"));
        prenotazioneBean.setData(rs.getDate("data"));
        prenotazioneBean.setGruppo(rs.getBoolean("gruppo"));
       // prenotazioneBean.setStudente(rs.getString("studente"));
       // prenotazioneBean.setPosto(rs.getInt("posto"));


        return prenotazioneBean;
    }

}
