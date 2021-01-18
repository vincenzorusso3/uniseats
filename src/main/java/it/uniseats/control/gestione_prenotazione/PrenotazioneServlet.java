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

/**
 * Servlet per la creazione di una nuova prenotazione.
 */

@WebServlet(name = "PrenotazioneServlet", value = "/PrenotazioneServlet")
public class PrenotazioneServlet extends HttpServlet {

    private final String DB_ERROR = "Impossibile procedere con la prenotazione, riprova più tardi!";
    private final String AULE_FULL = "Nessun posto disponibile per la data selezionata!";
    private final String HAS_PRENOTATION = "Hai già una prenotazione per questa data!";
    private final String INVALID_DATE = "La data scelta non è valida!";
    
    private final String JSP_PATH = "/view/prenotazione/NuovaPrenotazioneView.jsp";

    @Override
    /**
     * Metodo per effettuare richieste doGet
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    /**
     * Metodo per effettuare richieste doPost
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
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

    /**
     * Metodo per creare una nuova prenotazione
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param isPrenotazioneSingola per verificare se la prenotazione è singola
     * @throws ParseException
     * @throws SQLException
     * @throws ServletException
     * @throws IOException
     */
    private void prenotazione(HttpServletRequest request, HttpServletResponse response, boolean isPrenotazioneSingola) throws ParseException, SQLException, ServletException, IOException {
        String date;

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(JSP_PATH);

        if (isPrenotazioneSingola)
            date = request.getParameter("dateValueSingolo");
        else
            date = request.getParameter("dateValueGruppo");

        if (date != null) {

            //controllo la validità della data selezionata
            if (isDateValid(date,isPrenotazioneSingola)) {

                StudenteBean user = getUser(request);
                String matricola = user.getMatricola();

                //controllo l'assenza di prenotazioni già effettuate per la data inserita
                if (checkPrenotazioni(matricola,date)) {

                        //contollo la disponibilità di posti nelle aule
                    if (checkPostiAule(user.getDipartimento())) {

                        //lo studente NON ha prenotazioni e c'e' almeno un posto libero
                        String qrCode = QrCodeGenerator.generateCode(matricola);

                        PrenotazioneBean prenotazione = new PrenotazioneBean(qrCode, new Date(), isPrenotazioneSingola, "", "", matricola);
                        Integer result = (Integer) PrenotazioneDAO.doQuery(PrenotazioneDAO.doSave, prenotazione);

                        //se la prenotazione è stata salvata nel database con successo, viene inoltrata al modulo IA oper l'assegnazione del posto a sedere
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

    /**
     * Controllo che la data inserita per la prenotazione
     * @param date la <b>data</b> selezionata per la prenotazione
     * @param isPrenotazioneSingola la <b>tipologia</b> di prenotazione
     * @return <b>true</b> se date != CALENDAR.Today;
     * @return <b>true</b> se la tipologia della prenotazione è singola, false se la tipologia della prenotazione è in gruppo
     * @return <b>false</b> altrimenti
     * @throws ParseException
     */
    private boolean isDateValid(String date, boolean isPrenotazioneSingola) throws ParseException {

        Date today = new Date();
        Date selectedDay = DateUtils.parseDate(date);

        if (selectedDay.compareTo(today) > 0)
            return true;

        if (selectedDay.compareTo(today) == 0)
            return isPrenotazioneSingola;

        return false;
    }

    /**
     * Restituisce lo studente loggato
     * @param request HttpServletRequest
     * @return lo <b>studente</b> loggato
     * @throws SQLException
     */
    private StudenteBean getUser(HttpServletRequest request) throws SQLException {

        HttpSession session = request.getSession(true);
        String email = (String)session.getAttribute("email");

        return (StudenteBean) StudenteDAO.doQuery(StudenteDAO.doRetrieveByEmail,email);

    }

    /**
     *  Controllo che lo studente non abbia già effettuato una prenotazione per la stessa data
     * @param matricola la <b>matricola</b> dello studente
     * @param date la <b>data di prenotazione</b> selezionata
     * @return <b>true</b> se non esistono prenotazioni per la data selezionata
     * @return  <b>false</b> altrimenti
     * @throws SQLException
     * @throws ParseException
     */
    private boolean checkPrenotazioni(String matricola, String date) throws SQLException, ParseException {

        Date selectedDay = DateUtils.parseDate(date);

        ArrayList<PrenotazioneBean> resultList = (ArrayList<PrenotazioneBean>) PrenotazioneDAO.doQuery(PrenotazioneDAO.doFindPrenotazioni,matricola);

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

    /**
     * Controllo la disponibilità di posti a sedere nelle aule
     * @param dipartimento il dipartimento per la quale si effettua il controllo delle aule ( corrisponde al dipartimento dello studente)
     * @return <b>true</b> se ci sono posti disponibili
     * @return <b>false</b> altrimenti
     * @throws SQLException
     */
    private boolean checkPostiAule(String dipartimento) throws SQLException {

        ArrayList<AulaBean> aule = (ArrayList<AulaBean>) AulaDAO.doQuery(AulaDAO.doRetrieveAll, dipartimento);

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
