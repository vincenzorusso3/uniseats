package it.uniseats.prenotazioni.model;

import it.uniseats.Utils.DataSourceUtils;
import it.uniseats.prenotazioni.beans.PostoBean;
import it.uniseats.prenotazioni.beans.PrenotazioneBean;
import it.uniseats.prenotazioni.beans.StudenteBean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class PrenotazioneModel{

    private static final String TABLE_NAME = "prenotazione";
    private static final String DATASOURCE_ERROR = "[PRENOTAZIONEMODEL] Errore: il DataSource non risulta essere configurato correttamente";
    private static final DataSource ds = DataSourceUtils.getDataSource();

    public synchronized PrenotazioneBean getPrenotazione(String codice) throws SQLException {

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

    public synchronized ArrayList<PrenotazioneBean> getPrenotazioniByStudent(String matricolaStudente) throws SQLException {

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
