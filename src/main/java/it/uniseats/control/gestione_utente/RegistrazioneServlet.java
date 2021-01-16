package it.uniseats.control.gestione_utente;

import it.uniseats.model.beans.StudenteBean;
import it.uniseats.model.dao.StudenteDAO;
import it.uniseats.utils.SHA512Utils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "RegistrazioneServlet")
public class RegistrazioneServlet extends HttpServlet {
    
    private final String JSP_PATH = "/view/profilo_utente/RegistrazioneView.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(JSP_PATH);


        if (action != null) {

            if (action.equals("add")) {
                try {
                    addUser(request, response);
                } catch (SQLException throwables) {

                    String message = "Registrazione fallita. Si prega di riprovare";
                    request.setAttribute("message", message);

                    dispatcher.forward(request, response);

                }
            }

        } else {
            dispatcher.forward(request, response);
        }

    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(JSP_PATH);
        String email = request.getParameter("email");

        StudenteBean studenteBean = (StudenteBean) StudenteDAO.doQuery("doRetrieveByEmail", email);

        if (studenteBean != null) {

            String message = "Esiste un account registrato con questa email!";
            request.setAttribute("message", message);

            dispatcher.forward(request, response);

        } else {

            String nome = request.getParameter("nome");
            String cognome = request.getParameter("cognome");
            String matricola = request.getParameter("matricola");
            String password = request.getParameter("password");
            int anno=Integer.parseInt(request.getParameter("anno"));
            String dipartimento=request.getParameter("dipartimento");

            studenteBean = new StudenteBean(nome, cognome, matricola, email, SHA512Utils.getSHA512(password), anno, dipartimento);

            Integer success = (Integer) StudenteDAO.doQuery("doSave", studenteBean);

            String message;

            if (success != null && success > 0) {
                message = "Registrazione effettuata con successo";
            } else {
                message = "Registrazione fallita. Si prega di riprovare";
            }

            request.setAttribute("message", message);
            dispatcher.forward(request, response);

        }

    }

}
