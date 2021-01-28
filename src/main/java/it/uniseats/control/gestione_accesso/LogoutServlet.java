package it.uniseats.control.gestione_accesso;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet per effettuare il logout dal sito.
 */

@WebServlet(name = "LogoutServlet")
public class LogoutServlet extends HttpServlet {

  /**
   * Metodo per effettuare richieste doPost.
   *
   * @param request  HttpServletRequest
   * @param response HttpServletResponse
   * @throws IOException se si verifica una eccezione
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

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

    request.getSession().invalidate();

    //redirect to Login page
    String redirectedPage = "/view/login/LoginView.jsp";
    response.sendRedirect(request.getContextPath() + redirectedPage);

  }

}

