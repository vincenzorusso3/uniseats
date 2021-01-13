package it.uniseats.control.gestione_prenotazione;

import it.uniseats.model.beans.PrenotazioneBean;
import it.uniseats.model.beans.StudenteBean;
import it.uniseats.model.dao.PrenotazioneDAO;
import it.uniseats.model.dao.StudenteDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

@WebServlet(name = "PrenotazioneServlet", value = "/PrenotazioneServlet")
public class PrenotazioneServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {

            switch (action){

                case "prenotazioneSingola":
                    try {
                        prenotazione(request, response, true);
                    } catch (ParseException | SQLException e) {
                        e.printStackTrace();
                    }
                    break;

                case "prenotazioneGruppo":
                    try {
                        prenotazione(request, response, false);
                    } catch (ParseException | SQLException e) {
                        e.printStackTrace();
                    }
                    break;
            }

        }
    }

    private void prenotazione(HttpServletRequest request, HttpServletResponse response, boolean isPrenotazioneSingola) throws ParseException, SQLException {
        String date;

        if (isPrenotazioneSingola)
            date = request.getParameter("dateValueSingolo");
        else
            date = request.getParameter("dateValueGruppo");

        if (date != null){

            if (isDateValid(date,isPrenotazioneSingola)){

                StudenteBean user = getUser(request);
                String matricola = user.getMatricola();

                if (checkPrenotazioni(matricola,date)){

                    //Utente NON ha prenotazioni

                    //TODO Intelligenza Artificiale
                }else{
                    //Utente ha già una prenotazione per quella data
                }
            }else{
                //La data selezionata non è valida
            }
        }else{
            //La data selezionata non è valida
        }

    }

    private boolean isDateValid(String date, boolean isPrenotazioneSingola) throws ParseException {

        Date today = new Date();
        Date selectedDay = parseDate(date);

        if (selectedDay.compareTo(today) > 0) {
            return true;
        }

        if (selectedDay.compareTo(today) == 0){
            return isPrenotazioneSingola;
        }

        return false;
    }

    private StudenteBean getUser(HttpServletRequest request) throws SQLException {

        HttpSession session = request.getSession(true);
        String email = (String)session.getAttribute("email");

        return (StudenteBean) StudenteDAO.doQuery("doRetrieveByEmail",email);

    }

    private boolean checkPrenotazioni(String matricola, String date) throws SQLException, ParseException {

        Date selectedDay = parseDate(date);

        ArrayList<PrenotazioneBean> resultList = (ArrayList<PrenotazioneBean>) PrenotazioneDAO.doQuery("doFindPrenotazioni",matricola);

        if (resultList != null) {

            for (PrenotazioneBean p : resultList){
                if (p.getData().compareTo(selectedDay) == 0)
                    return false;
            }

            return true;

        } else {
            return false;
        }

    }

    private Date parseDate(String date) throws ParseException {

        date = date.replace("-","/");
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);

        return df.parse(date);
    }
}
