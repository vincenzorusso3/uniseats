package it.uniseats.control.visualizzaPrenotazioni;


import it.uniseats.model.dao.StudenteDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name="ManagePrenotazioneServlet")
public class ManagePrenotazioneServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       String action = request.getParameter("action");
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/VisualizzaPrenotazioni.jsp");

        if(action != null){
            if(action.equalsIgnoreCase("visualizzaPrenotazioni")){
                try{
                    StudenteDAO.doQuery("doFindPrenotazioni", request.getParameter("matricola"));
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
