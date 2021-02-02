package it.uniseats.control.gestione_utente;

import it.uniseats.model.beans.StudenteBean;
import it.uniseats.model.dao.AulaDao;
import it.uniseats.model.dao.StudenteDao;
import it.uniseats.utils.Sha512Utils;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet per la registrazione di un nuovo studente.
 */
@WebServlet(name = "RegistrazioneServlet")
public class RegistrazioneServlet extends HttpServlet {

  private final String JSP_PATH = "/view/profilo_utente/RegistrazioneView.jsp";

  /**
   * Metodo per effettuare richieste doPost.
   *
   * @param request  HttpServletRequest
   * @param response HttpServletResponse
   * @throws ServletException se si verifica una eccezione
   * @throws IOException se si verifica una eccezione
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    doGet(request, response);
  }

  /**
   * Metodo per effettuare richieste doGet.
   *
   * @param request  HttpServletRequest
   * @param response HttpServletResponse
   * @throws ServletException se si verifica una eccezione
   * @throws IOException se si verifica una eccezione
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response)
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

      if (action.equals("getDipartimenti")) {
        try {

          getDip(request, response);
        } catch (SQLException throwables) {

          String message = "Errore. Si prega di riprovare";
          request.setAttribute("message", message);

          dispatcher.forward(request, response);

        }
      }

    } else {
      dispatcher.forward(request, response);
    }

  }

  /**
   * Metodo per aggiungere un nuovo studente.
   *
   * @param request  HttpServletRequest
   * @param response HttpServletResponse
   * @throws SQLException se si verifica una eccezione
   * @throws ServletException se si verifica una eccezione
   * @throws IOException se si verifica una eccezione
   */
  private void addUser(HttpServletRequest request, HttpServletResponse response)
          throws SQLException, ServletException, IOException {

    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(JSP_PATH);
    String email = request.getParameter("email");
    String matricola = request.getParameter("matricola");

    StudenteBean studenteBean =
            (StudenteBean) StudenteDao.doQuery(StudenteDao.doRetrieveByEmail, email);
    String emailNull = studenteBean.getEmail();
    studenteBean = (StudenteBean) StudenteDao.doQuery(StudenteDao.doRetrieveByMatricola, matricola);
    String matricolaNull = studenteBean.getMatricola();


    if (emailNull != null) {

      String message = "Esiste già un account con questa e-mail";
      request.setAttribute("message", message);

      dispatcher.forward(request, response);

    } else if (matricolaNull != null) {

      String message = "Esiste già un account con questa Matricola";
      request.setAttribute("message", message);

      dispatcher.forward(request, response);
    } else {

      String nome = request.getParameter("nome");
      String cognome = request.getParameter("cognome");

      String password = request.getParameter("password");
      int anno = Integer.parseInt(request.getParameter("anno"));
      String dipartimento = request.getParameter("dipartimento");

      studenteBean =
              new StudenteBean(nome, cognome, matricola, email, Sha512Utils.getSha512(password),
                  anno, dipartimento);

      Integer success = (Integer) StudenteDao.doQuery(StudenteDao.doSave, studenteBean);

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

  /**
   * Metodo per la lista dei dipartimenti.
   *
   * @param request  HttpServletRequest
   * @param response HttpServletResponse
   * @throws SQLException se si verifica una eccezione
   * @throws ServletException se si verifica una eccezione
   * @throws IOException se si verifica una eccezione
   */


  private void getDip(HttpServletRequest request, HttpServletResponse response)
          throws SQLException, ServletException, IOException {


    ArrayList<String> dip = (ArrayList<String>) AulaDao.doQuery(AulaDao.getDipartimenti, "Temp");

    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(
        "/view/profilo_utente/RegistrazioneView.jsp");
    request.getSession().setAttribute("dipartimenti", dip);


    dispatcher.forward(request, response);




  }

}


