package it.uniseats.control.gestione_accesso;

import it.uniseats.model.beans.StudenteBean;
import it.uniseats.model.dao.StudenteDAO;
import it.uniseats.utils.SHA512Utils;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet per effettuare la login.
 */
@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {

  private final String JSP_PATH = "/view/login/LoginView.jsp";

  /**
   * Metodo per effettuare richieste doPost.
   *
   * @param request  HttpServletRequest
   * @param response HttpServletResponse
   * @throws IOException se si verifica una eccezione
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    doGet(request, response);
  }

  /**
   * Metodo per effettuare richieste doGet.
   *
   * @param request  HttpServletRequest
   * @param response HttpServletResponse
   * @throws IOException se si verifica una eccezione
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    String action = request.getParameter("action");
    String from=request.getParameter("from");


    if (action != null) {

      if (action.equalsIgnoreCase("Login")) {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String encrypted = SHA512Utils.getSHA512(password);

        String redirectedPage;

        try {

          StudenteBean bean = (StudenteBean) StudenteDAO.doQuery(StudenteDAO.doRetrieveByEmail, email);

          if (bean.getMatricola() != null && bean.getPassword().equals(encrypted)) {

            request.getSession().setAttribute("logged", true);
            request.getSession().setAttribute("nome", bean.getNome());
            request.getSession().setAttribute("cognome", bean.getCognome());
            request.getSession().setAttribute("matricola", bean.getMatricola());
            request.getSession().setAttribute("email", bean.getEmail());
            request.getSession().setAttribute("anno", bean.getAnno());
            request.getSession().setAttribute("dipartimento", bean.getDipartimento());

            //redirect to LandindPage
            redirectedPage = "/view/LandingPageView.jsp";

            if (from.equals("inizia")){

              //redirect to NuovaPrenotazione
              redirectedPage = "/view/prenotazione/NuovaPrenotazioneView.jsp";
            }


            response.sendRedirect(request.getContextPath() + redirectedPage);

          } else {

            String message = "Username e/o password non validi!";

            request.setAttribute("errore", message);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(JSP_PATH);
            dispatcher.forward(request, response);

          }

        } catch (Exception e) {

          String message = "Username e/o password non validi!";

          request.setAttribute("errore", message);
          RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(JSP_PATH);

          try {

            dispatcher.forward(request, response);

          } catch (ServletException servletException) {

            servletException.printStackTrace();

          }
        }

      }

    } else {

      response.sendRedirect(request.getContextPath() + JSP_PATH);

    }
  }

}

