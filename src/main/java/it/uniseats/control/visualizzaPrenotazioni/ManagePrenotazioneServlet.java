package it.uniseats.control.visualizzaPrenotazioni;


import it.uniseats.model.beans.PrenotazioneBean;
import it.uniseats.model.dao.PrenotazioneDAO;
import it.uniseats.utils.DateUtils;
import sun.util.resources.ext.CalendarData_it;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Servlet per gestire le prenotazioni effettuate in termini di modifica o eliminazione di una prenotazione
 */
@WebServlet(name="ManagePrenotazioneServlet")
public class ManagePrenotazioneServlet extends HttpServlet {

    private final String JSP_PATH = "/view/prenotazione/VisualizzaPrenotazioniView.jsp";

    /**
     * Metodo per effettuare richieste doPost
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action != null) {

            switch (action) {

                case "visualizzaPrenotazioni":
                    try {
                        visualizzaPrenotazioni(request, response);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    break;

                case "modificaPrenotazione":
                    try {
                        modificaPrenotazione(request, response);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    break;

                case "modificaData":
                    try {
                        modificaData(request, response);
                    } catch (SQLException | ParseException throwables) {
                        throwables.printStackTrace();
                    }
                    break;

            }

        } else {
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(JSP_PATH);
            dispatcher.forward(request, response);
        }

    }

    /**
     * Metodo per modificare la data di una prenotazione
     *
     * @param request  HttpServletRequest
     * @param response HttpSErvletResponse
     * @throws ParseException
     * @throws SQLException
     * @throws ServletException
     * @throws IOException
     */
    private void modificaData(HttpServletRequest request, HttpServletResponse response) throws ParseException, SQLException, ServletException, IOException {

        String codice = request.getParameter("codice");
        String dateTemp = request.getParameter("data");

        Date dataPrenotazione = DateUtils.parseDate(dateTemp);

        PrenotazioneBean prenotazioneBean = (PrenotazioneBean) PrenotazioneDAO.doQuery(PrenotazioneDAO.doRetrieveByCode, codice);

        if (prenotazioneBean != null) {

            //controllo che la data inserita sia diversa dalla data attuale della prenotazione
            if (prenotazioneBean.getData().equals(dataPrenotazione)) {
                PrenotazioneDAO.doQuery(PrenotazioneDAO.doUpdateData, prenotazioneBean);
            }

        } else {
            request.setAttribute("message", "Si è verificato un errore");
        }

        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(JSP_PATH);
        dispatcher.forward(request, response);

    }

    /**
     * Metodo per modificare la tipologia della prenotazione
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws SQLException
     * @throws ServletException
     * @throws IOException
     */
    private void modificaPrenotazione(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {

        PrenotazioneBean prenotazioneBean = (PrenotazioneBean) PrenotazioneDAO.doQuery(PrenotazioneDAO.doRetrieveByCode, request.getParameter("codice"));
        RequestDispatcher dispatcher;

        if (prenotazioneBean != null) {

            request.setAttribute("codice", prenotazioneBean.getCodice());


            //TODO aggiungere lo stesso controllo anche al modifica data che sta sopra
            Date today = new Date();
            LocalTime time = LocalTime.now();
            boolean isbefore = time.isBefore(LocalTime.parse("07:00"));

            //controllo che la modifica della prenotazione avvenga prima delle 7 am del giorno della prenotazione oppure avvenga in un giorno antecedente alla data della prenotazione
            if (((prenotazioneBean.getData().compareTo(today) == 0) && isbefore == true) || prenotazioneBean.getData().compareTo(today) > 0) {

                //TODO: DA FINIRE
            }


            dispatcher = request.getServletContext().getRequestDispatcher("/view/prenotazione/ModificaPrenotazioniView.jsp");

        }else {

            request.setAttribute("message", "Si è verificato un errore");
            dispatcher = request.getServletContext().getRequestDispatcher(JSP_PATH);

        }

        dispatcher.forward(request,response);

    }


    private void visualizzaPrenotazioni(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(JSP_PATH);

        String parameter = request.getParameter("matricola");

        request.removeAttribute("prenotazioni");
        request.setAttribute("prenotazioni", PrenotazioneDAO.doQuery(PrenotazioneDAO.doFindPrenotazioni, parameter));

        dispatcher.forward(request,response);

    }

    /**
     * Metodo per effettuare richieste doGet
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

}
