package it.uniseats.control.gestione_utente;


import it.uniseats.model.beans.StudenteBean;
import it.uniseats.model.dao.StudenteDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet per la gestione del profilo utente
 */

@WebServlet(name = "ProfiloUtenteServlet")
public class ProfiloUtenteServlet extends HttpServlet {

    /**
     * Metodo per effettuare richieste doPost
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String action = request.getParameter("action");

        String JSP_PATH = "/view/profilo_utente/ProfiloUtenteView.jsp";

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(JSP_PATH);

        if (action != null) {

            switch (action) {

                case "confermaDelete":
                    try {
                        //si effettua la cancellazione del profilo dello studente
                        deleteProfile(request,response);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    break;

                case "confermaUpdate":
                    try {
                        //si incrementa l'anno di corso dello studente
                        updateAnno(request,response,dispatcher);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
            }

        } else {
            dispatcher.forward(request, response);
        }

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

    /**
     * Metodo per effettuare l'incremento dell'anno di corso dello studente di una unità
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param dispatcher RequestDispatcher
     * @throws SQLException
     * @throws ServletException
     * @throws IOException
     */
    private void updateAnno(HttpServletRequest request, HttpServletResponse response, RequestDispatcher dispatcher) throws SQLException, ServletException, IOException {

        StudenteBean studMod= (StudenteBean) StudenteDAO.doQuery(StudenteDAO.doRetrieveByMatricola,request.getParameter("matricola"));

        if (studMod != null){

            int anno = Integer.parseInt(request.getParameter("anno"));
            studMod.setAnno(anno);
            StudenteDAO.doQuery("doUpdate",studMod);

        } else {

            String message = "Si è verificato un errore";
            request.setAttribute("errore", message);

        }

        dispatcher.forward(request,response);

    }

    /**
     * Metodo per eliminare il profilo di uno studente
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     * @throws SQLException
     */
    private void deleteProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        StudenteDAO.doQuery(StudenteDAO.doDelete,request.getParameter("matricola"));

        String message = "Profilo eliminato con successo";
        request.setAttribute("errore", message);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("");
        dispatcher.forward(request, response);

    }

}
