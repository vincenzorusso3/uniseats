package control;

import static org.junit.jupiter.api.Assertions.assertEquals;

import it.uniseats.control.gestione_accesso.LoginServlet;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletConfig;

class LoginServletTest {

  private LoginServlet servlet;
  private MockHttpServletRequest request;
  private MockHttpServletResponse response;
  private MockHttpSession session;

  @BeforeEach
  public void setUp() {
    servlet = new LoginServlet();
    request = new MockHttpServletRequest();
    response = new MockHttpServletResponse();
    request.setSession(session);

  }

  @BeforeEach
  public void oneWaySetup() throws ServletException {
    ServletConfig sg = new MockServletConfig();
    servlet.init(sg);
  }

  //TC_1.2_01
  @Test
  public void loginTestFailedEmailNonEsistente() throws IOException {
    request.getSession().setAttribute("logged", false);
    request.addParameter("action", "Login");
    request.addParameter("email", "a.sabia@studenti.unisa.it");
    request.addParameter("password", "accioLaurea");
    servlet.doGet(request, response);
    assertEquals("Username e/o password non validi!", request.getAttribute("errore"));

  }

  //TC_1.2_02
  @Test
  public void loginTestFailedPasswordNonEsistente() throws IOException {
    request.getSession().setAttribute("logged", false);
    request.addParameter("action", "Login");
    request.addParameter("email", "a.sabia15@studenti.unisa.it");
    request.addParameter("password", "accioLaurea1");
    servlet.doGet(request, response);
    assertEquals("Username e/o password non validi!", request.getAttribute("errore"));


  }

  //TC_1.2_03
  @Test
  public void loginTestFailedPasswordNonCorrispondente() throws IOException {
    request.getSession().setAttribute("logged", false);
    request.addParameter("action", "Login");
    request.addParameter("email", "a.sabia15@studenti.unisa.it");
    request.addParameter("password", "Alessia3000");
    servlet.doGet(request, response);
    assertEquals("Username e/o password non validi!", request.getAttribute("errore"));
  }

  //TC_1.2_04
  @Test
  public void loginTestSuccessfull() throws ServletException, IOException {
    request.getSession().setAttribute("logged", false);
    request.addParameter("action", "Login");
    request.addParameter("email", "a.sabia15@studenti.unisa.it");
    request.addParameter("password", "accioLaurea");
    servlet.doGet(request, response);
    assertEquals(true,request.getSession().getAttribute("logged"));
  }
}

