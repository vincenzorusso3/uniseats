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

@WebServlet(name = "ProfiloUtenteServlet")
public class ProfiloUtenteServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ProfiloUtenteView.jsp");

        if (action != null) {
            if(action.equalsIgnoreCase("confermaDelete")){
                try {
                    StudenteDAO.doQuery("doDelete",request.getParameter("matricola"));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                String message = "Profilo eliminato con successo";
                request.setAttribute("errore", message);
                dispatcher = getServletContext().getRequestDispatcher("Home/.jsp");
                dispatcher.forward(request, response);
            }


            if(action.equalsIgnoreCase("confermaUpdate")){
                try {
                    StudenteBean studMod= (StudenteBean) StudenteDAO.doQuery("doRetrieveByMatricola",request.getParameter("matricola"));
                    studMod.setAnno(Integer.parseInt(request.getParameter("annomod")));
                    StudenteDAO.doQuery("doUpdate",studMod);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }




            }
            dispatcher.forward(request, response);



        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
