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
import java.util.Date;

@WebServlet(name="ManagePrenotazioneServlet")
public class ManagePrenotazioneServlet extends HttpServlet {

    private final String JSP_PATH = "/view/prenotazione/VisualizzaPrenotazioniView.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        if(action != null) {

            switch (action) {

                case "visualizzaPrenotazioni":
                    try {
                        visualizzaPrenotazioni(request,response);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    break;

                case "modificaPrenotazione":
                    try {
                        modificaPrenotazione(request,response);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    break;

                case "modificaData":
                    try {
                        modificaData(request,response);
                    } catch (SQLException | ParseException throwables) {
                        throwables.printStackTrace();
                    }
                    break;

            }

        } else {
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(JSP_PATH);
            dispatcher.forward(request,response);
        }

    }

    private void modificaData(HttpServletRequest request, HttpServletResponse response) throws ParseException, SQLException, ServletException, IOException {

        String codice = request.getParameter("codice");
        String dateTemp = request.getParameter("data");

        Date dataPrenotazione = DateUtils.parseDate(dateTemp);

        PrenotazioneBean prenotazioneBean = (PrenotazioneBean) PrenotazioneDAO.doQuery("doRetrieveByCode", codice);

        if (prenotazioneBean != null) {

            if (prenotazioneBean.getData().equals(dataPrenotazione)) {
                PrenotazioneDAO.doQuery("doUpdateData", prenotazioneBean);
            }

        } else {
            request.setAttribute("message","Si è verificato un errore");
        }

        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(JSP_PATH);
        dispatcher.forward(request,response);

    }

    private void modificaPrenotazione(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {

        PrenotazioneBean prenotazioneBean = (PrenotazioneBean) PrenotazioneDAO.doQuery("doRetrieveByCode", request.getParameter("codice"));
        RequestDispatcher dispatcher;

        if (prenotazioneBean != null) {

            request.setAttribute("codice", prenotazioneBean.getCodice());

            //TODO: DA FINIRE

            dispatcher = request.getServletContext().getRequestDispatcher("/view/prenotazione/ModificaPrenotazioniView.jsp");

        } else {

            request.setAttribute("message", "Si è verificato un errore");
            dispatcher = request.getServletContext().getRequestDispatcher(JSP_PATH);

        }

        dispatcher.forward(request,response);

    }

    private void visualizzaPrenotazioni(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(JSP_PATH);

        String parameter = request.getParameter("matricola");

        request.removeAttribute("prenotazioni");
        request.setAttribute("prenotazioni", PrenotazioneDAO.doQuery("doFindPrenotazioni", parameter));

        dispatcher.forward(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

}
