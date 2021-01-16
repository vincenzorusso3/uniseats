package it.uniseats.control.gestione_prenotazione;

import it.uniseats.model.beans.AulaBean;
import it.uniseats.model.beans.PrenotazioneBean;
import it.uniseats.model.beans.StudenteBean;
import it.uniseats.model.dao.AulaDAO;
import it.uniseats.model.dao.PrenotazioneDAO;
import it.uniseats.model.dao.StudenteDAO;
import it.uniseats.utils.DateUtils;
import it.uniseats.utils.QrCodeGenerator;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

@WebServlet(name = "PrenotazioneServlet", value = "/PrenotazioneServlet")
public class PrenotazioneServlet extends HttpServlet {

    private final String DB_ERROR = "Impossibile procedere con la prenotazione, riprova più tardi!";
    private final String AULE_FULL = "Nessun posto disponibile per la data selezionata!";
    private final String HAS_PRENOTATION = "Hai già una prenotazione per questa data!";
    private final String INVALID_DATE = "La data scelta non è valida!";
    
    private final String JSP_PATH = "/view/prenotazione/NuovaPrenotazioneView.jsp";

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
        }else{
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(JSP_PATH);
            dispatcher.forward(request, response);
        }
    }

    private void prenotazione(HttpServletRequest request, HttpServletResponse response, boolean isPrenotazioneSingola) throws ParseException, SQLException, ServletException, IOException {
        String date;

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(JSP_PATH);

        if (isPrenotazioneSingola)
            date = request.getParameter("dateValueSingolo");
        else
            date = request.getParameter("dateValueGruppo");

        if (date != null) {

            if (isDateValid(date,isPrenotazioneSingola)) {

                StudenteBean user = getUser(request);
                String matricola = user.getMatricola();

                if (checkPrenotazioni(matricola,date)) {

                    if (checkPostiAule(user.getDipartimento())) {

                        //Utente NON ha prenotazioni e c'e' almeno un posto libero
                        String qrCode = QrCodeGenerator.generateCode(matricola);

                        PrenotazioneBean prenotazione = new PrenotazioneBean(qrCode, new Date(), isPrenotazioneSingola, "", "", matricola);
                        Integer result = (Integer) PrenotazioneDAO.doQuery("doSave", prenotazione);

                        if (result != null && result > 0) {
                            //TODO Intelligenza Artificiale

                            dispatcher = getServletContext().getRequestDispatcher("/view/prenotazione/VisualizzaPrenotazioniView.jsp");

                        } else {
                            request.setAttribute("errore", DB_ERROR);
                        }

                    } else {
                        request.setAttribute("errore", AULE_FULL);
                    }

                } else {
                    request.setAttribute("errore", HAS_PRENOTATION);
                }

            } else {
                request.setAttribute("errore", INVALID_DATE);
            }

        } else {
            request.setAttribute("errore", INVALID_DATE);
        }

        dispatcher.forward(request, response);

    }

    private boolean isDateValid(String date, boolean isPrenotazioneSingola) throws ParseException {

        Date today = new Date();
        Date selectedDay = DateUtils.parseDate(date);

        if (selectedDay.compareTo(today) > 0)
            return true;

        if (selectedDay.compareTo(today) == 0)
            return isPrenotazioneSingola;

        return false;
    }

    private StudenteBean getUser(HttpServletRequest request) throws SQLException {

        HttpSession session = request.getSession(true);
        String email = (String)session.getAttribute("email");

        return (StudenteBean) StudenteDAO.doQuery("doRetrieveByEmail",email);

    }

    private boolean checkPrenotazioni(String matricola, String date) throws SQLException, ParseException {

        Date selectedDay = DateUtils.parseDate(date);

        ArrayList<PrenotazioneBean> resultList = (ArrayList<PrenotazioneBean>) PrenotazioneDAO.doQuery("doFindPrenotazioni",matricola);

        if (resultList != null) {

            for (PrenotazioneBean p : resultList) {
                if (p.getData().compareTo(selectedDay) == 0)
                    return false;
            }

            return true;

        } else {
            return false;
        }

    }

    private boolean checkPostiAule(String dipartimento) throws SQLException {

        ArrayList<AulaBean> aule = (ArrayList<AulaBean>) AulaDAO.doQuery("doRetrieveAll", dipartimento);

        if (aule != null) {

            int totPosti = 0;

            for (AulaBean aula : aule) {
                totPosti += aula.getnPosti();
            }

            return totPosti != 0;

        }

        return false;

    }
}
