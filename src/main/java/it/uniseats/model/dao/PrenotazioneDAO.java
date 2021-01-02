package it.uniseats.model.dao;


import it.uniseats.model.beans.PostoBean;
import it.uniseats.model.beans.PrenotazioneBean;
import it.uniseats.model.beans.StudenteBean;
import it.uniseats.utils.DataSourceUtils;

import javax.sql.DataSource;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class PrenotazioneDAO {

    private static final String TABLE_NAME = "prenotazione";
    private static final String DATASOURCE_ERROR = "[PRENOTAZIONEDAO] Errore: il DataSource non risulta essere configurato correttamente";

    public static synchronized Object doQuery(String methodName, Object parameter) throws SQLException {

        DataSource ds = DataSourceUtils.getDataSource();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        if (ds != null) {

            String querySQL;

            try {
                connection = ds.getConnection();

                switch (methodName) {

                    case "doRetrieveByCode":
                        querySQL = "SELECT * FROM " + TABLE_NAME + " WHERE codice=?";
                        preparedStatement = connection.prepareStatement(querySQL);
                        return doRetrieveByCode(preparedStatement, (String) parameter);

                    case "doRetrieveAll":
                        querySQL = "SELECT * FROM " + TABLE_NAME + " WHERE matricolaStudente=?";
                        preparedStatement = connection.prepareStatement(querySQL);
                        return doRetrieveAll(preparedStatement, (String) parameter);

                    case "doSave":
                        querySQL = "INSERT INTO " + TABLE_NAME + " (codice, matricolaStudente, dataPrenotazione, tipologia, qrCode) VALUES (?,?,?,?,?)";
                        preparedStatement = connection.prepareStatement(querySQL);
                        return doSave(preparedStatement, (PrenotazioneBean) parameter);

                    case "doUpdateData":
                        querySQL = "UPDATE " + TABLE_NAME + " SET dataPrenotazione=?  WHERE codice=?";
                        preparedStatement = connection.prepareStatement(querySQL);
                        return doUpdateData(preparedStatement, (PrenotazioneBean) parameter);

                    case "doUpdateTipo":
                        querySQL = "UPDATE " + TABLE_NAME + " SET tipologia=?  WHERE codice=?";
                        preparedStatement = connection.prepareStatement(querySQL);
                        return doUpdateTipo(preparedStatement, (PrenotazioneBean) parameter);

                    case "doDelete":
                        querySQL = "DELETE FROM " + TABLE_NAME + " WHERE codice=?";
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

    private static synchronized PrenotazioneBean doRetrieveByCode(PreparedStatement preparedStatement, String codice) throws SQLException {

        preparedStatement.setString(1, codice);
        ResultSet rs = preparedStatement.executeQuery();
        PrenotazioneBean prenotazioneBean = new PrenotazioneBean();

        while (rs.next()) {
            prenotazioneBean = getPrenotazioneInfo(rs);
        }

        return prenotazioneBean;

    }

    private static synchronized ArrayList<PrenotazioneBean> doRetrieveAll(PreparedStatement preparedStatement, String matricolaStudente) throws SQLException {

        ArrayList<PrenotazioneBean> list = new ArrayList<>();
        preparedStatement.setString(1, matricolaStudente);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            PrenotazioneBean prenotazioneBean = getPrenotazioneInfo(rs);
            list.add(prenotazioneBean);
        }

        return list;

    }

    private static synchronized int doSave(PreparedStatement preparedStatement, PrenotazioneBean prenotazioneBean) throws SQLException {

        preparedStatement.setString(1, prenotazioneBean.getCodice());
        preparedStatement.setString(2, prenotazioneBean.getMatricolaStudente());
        preparedStatement.setDate(3, (Date) prenotazioneBean.getData());
        preparedStatement.setBoolean(4, prenotazioneBean.isGruppo());
        preparedStatement.setString(5, prenotazioneBean.getQrCode());
        preparedStatement.setString(6, prenotazioneBean.getCodiceAula());
        preparedStatement.setString(7,prenotazioneBean.getCodicePosto());
        preparedStatement.setString(8,prenotazioneBean.getMatricolaStudente());

        return preparedStatement.executeUpdate();

    }

    private static synchronized int doUpdateData(PreparedStatement preparedStatement, PrenotazioneBean prenotazioneBean) throws SQLException {

        preparedStatement.setDate(1, (Date) prenotazioneBean.getData());
        preparedStatement.setString(2, prenotazioneBean.getCodice());

        return preparedStatement.executeUpdate();

    }

    private static synchronized int doUpdateTipo(PreparedStatement preparedStatement, PrenotazioneBean prenotazioneBean) throws SQLException {

        preparedStatement.setBoolean(1, prenotazioneBean.isGruppo());
        return preparedStatement.executeUpdate();

    }


    private static synchronized boolean doDelete(PreparedStatement preparedStatement, String codice) throws SQLException {

        preparedStatement.setString(1, codice);
        return (preparedStatement.executeUpdate() != 0);

    }
    //Queries


    private static PrenotazioneBean getPrenotazioneInfo(ResultSet rs) throws SQLException {

        PrenotazioneBean prenotazioneBean = new PrenotazioneBean();

        prenotazioneBean.setCodice(rs.getString("codice"));
        prenotazioneBean.setQrCode(rs.getString("qrCode"));
        prenotazioneBean.setData(rs.getDate("data"));
        prenotazioneBean.setGruppo(rs.getBoolean("gruppo"));
        prenotazioneBean.setMatricolaStudente(rs.getString("matricolaStudente"));
        prenotazioneBean.setCodicePosto(rs.getString("codicePosto"));
        prenotazioneBean.setCodiceAula(rs.getString("codiceAula"));



        return prenotazioneBean;
    }

}
