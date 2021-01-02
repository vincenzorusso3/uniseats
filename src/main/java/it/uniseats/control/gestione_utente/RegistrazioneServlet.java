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

    private StudenteDAO studenteDao=new StudenteDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action != null) {

            if (action.equalsIgnoreCase("add")) {

                String email = request.getParameter("email");
                StudenteBean test = null;
                try {
                    test = (StudenteBean) studenteDao.doQuery("doRetrieveByEmail", (Object) email);
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                if (test != null) {

                    String message = "Esiste un account registrato con questa email!";
                    request.setAttribute("message", message);

                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/RegistrazioneView.jsp");
                    dispatcher.forward(request, response);

                } else {

                    String nome = request.getParameter("nome");
                    String cognome = request.getParameter("cognome");
                    String matricola = request.getParameter("matricola");
                    String password = request.getParameter("password");
                    int anno=Integer.parseInt(request.getParameter("anno"));
                    String dipartimento=request.getParameter("dipartimento");


                    StudenteBean studenteBean=new StudenteBean(nome, cognome, matricola, email, SHA512Utils.getSHA512(password), anno, dipartimento);

                    try {
                        int success = (int) studenteDao.doQuery("doSave", studenteBean);

                        if (success > 0) {

                            String message = "Registrazione effettuata con successo";
                            request.setAttribute("message", message);

                            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/RegistrazioneView.jsp");
                            dispatcher.forward(request, response);

                        } else {
                            String message = "Registrazione fallita. Si prega di riprovare";
                            request.setAttribute("message", message);

                            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/RegistrazioneView.jsp");
                            dispatcher.forward(request, response);

                        }

                    } catch (SQLException e) {

                        String message = "Registrazione fallita. Si prega di riprovare";
                        request.setAttribute("message", message);

                        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/RegistrazioneView.jsp");
                        dispatcher.forward(request, response);


                    }
                }

            }
        } else {

            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/RegistrazioneView.jsp");
            dispatcher.forward(request, response);
        }



    }
}
