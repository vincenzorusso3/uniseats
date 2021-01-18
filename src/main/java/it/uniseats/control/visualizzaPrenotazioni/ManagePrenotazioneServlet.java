package it.uniseats.control.visualizzaPrenotazioni;


import it.uniseats.model.beans.PrenotazioneBean;
import it.uniseats.model.dao.PrenotazioneDAO;
import it.uniseats.utils.DateUtils;


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
        Date today = new Date();
        PrenotazioneBean prenotazioneBean = (PrenotazioneBean) PrenotazioneDAO.doQuery(PrenotazioneDAO.doRetrieveByCode, codice);

        if (prenotazioneBean != null) {

            //controllo che la data inserita sia diversa dalla data attuale della prenotazione
            if (prenotazioneBean.getData().equals(dataPrenotazione)) {
                //controllo che la modifica della prenotazione venga effettuata prima delle 07:00 del giorno della prenotazione o in un giorno antecedente la data per cui è prevista la prenotazione
                if(checkData(prenotazioneBean.getData())) {
                    //la modifica è possibile solo se la nuova data è oggi e il tipo di prenotazione sia singola o in generale se la nuova data è diversa dalla data corrente
                    if((dataPrenotazione.compareTo(today)==0 && prenotazioneBean.isSingolo()) || dataPrenotazione.compareTo(today)>0) {
                        PrenotazioneDAO.doQuery(PrenotazioneDAO.doUpdateData, prenotazioneBean);
                    }
                }
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

           if(checkData(prenotazioneBean.getData())){
               //TODO invocaione metodo canIupdate passandogli il nuovo tipo e la data della prenotazione se true allora consenti la modifica
           }


            dispatcher = request.getServletContext().getRequestDispatcher("/view/prenotazione/ModificaPrenotazioniView.jsp");

        } else {

            request.setAttribute("message", "Si è verificato un errore");
            dispatcher = request.getServletContext().getRequestDispatcher(JSP_PATH);

        }

        dispatcher.forward(request, response);

    }


    private void visualizzaPrenotazioni(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(JSP_PATH);

        String parameter = request.getParameter("matricola");

        request.removeAttribute("prenotazioni");
        request.setAttribute("prenotazioni", PrenotazioneDAO.doQuery(PrenotazioneDAO.doFindPrenotazioni, parameter));

        dispatcher.forward(request, response);

    }

    /**
     * Metodo per effettuare richieste doGet
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Metodo che controlla se la <b>data della prenotazione</b>
     * @param date la <b>data</b> della p
     * @return true se la <b>data della prenotazione è oggi e l'orario in cui si chiede la modifica è antecedente alle 7 del mattino</b>
     * oppure se la <b>data della prenotazione è successiva alla data per la quale si richiede la modifica</b>
     * @return  false altrimenti
     */
    private boolean checkData(Date date) {
        Date today = new Date();
        LocalTime time = LocalTime.now();
        boolean isbefore = time.isBefore(LocalTime.parse("07:00"));
        if (((date.compareTo(today) == 0) && isbefore == true) || date.compareTo(today) > 0) {
            return true;
        }
        else return false;
    }

    /**
     * Metodo che controlla se è possibile modificare il tipo di prenotazione
     * @param singolo nuova tipologia di prenotazione <b>true</b> se il nuovo tipo è singolo, <b>false</b> se il nuovo tipo è gruppo
     * @param date la data per la quale è prevista la prenotazione
     * @return true se è possibile effettuare la modifca
     * @return false se non è possibile effettuare la modifica
     */
    private boolean canIUpdate(Boolean singolo, Date date) {
        Date today = new Date();
        //se la nuova tipologia di prenotazione è singola, posso sempre modificare
        if ((singolo == true && ((date.compareTo(today) == 0) || date.compareTo(today) > 0))) {

            return true;

        } else
                //se la nuova tipologia è gruppo, non posso modificare se la data della prenotazione è la data corrente
                if (singolo == false && date.compareTo(today) == 0) {

            return false;

        } else
            //in tutti gli altri casi posso effettuare la modifica
            return true;
         }

    }


