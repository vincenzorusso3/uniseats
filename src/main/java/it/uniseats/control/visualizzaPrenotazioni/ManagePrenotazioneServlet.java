package it.uniseats.control.visualizzaPrenotazioni;


import it.uniseats.model.beans.PrenotazioneBean;
import it.uniseats.model.dao.PrenotazioneDAO;
import it.uniseats.model.dao.StudenteDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

@WebServlet(name="ManagePrenotazioneServlet")
public class ManagePrenotazioneServlet extends HttpServlet {

    private PrenotazioneBean bean;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       String action = request.getParameter("action");
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/VisualizzaPrenotazioniView.jsp");

        if(action != null) {
            try {

                if (action.equalsIgnoreCase("visualizzaPrenotazioni")) {

                    request.removeAttribute("prenotazioni");
                    request.setAttribute("prenotazioni", PrenotazioneDAO.doQuery("doFindPrenotazioni", request.getParameter("matricola")));


                } else if (action.equalsIgnoreCase("modifica")) {

                    bean = (PrenotazioneBean) PrenotazioneDAO.doQuery("doRetrieveByCode", request.getParameter("codice"));
                    request.setAttribute("codice", bean.getCodice());
                    dispatcher = request.getServletContext().getRequestDispatcher("/ModificaPrenotazioniView.jsp");

                } else if (action.equalsIgnoreCase("modificaPrenotazione")) {

                    String dateTemp = request.getParameter("data");
                    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);

                    Date dataPrenotazione = df.parse(dateTemp);

                    if (!bean.getData().equals(dataPrenotazione)) {
                        PrenotazioneDAO.doQuery("doUpdateData", bean);
                    }

                    dispatcher = request.getServletContext().getRequestDispatcher("Home/.jsp");

                }
            }
                catch (ParseException | SQLException e) {
                    e.printStackTrace();
                }

        }
            dispatcher.forward(request, response);
        }






    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


}
