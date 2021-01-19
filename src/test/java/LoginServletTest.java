import it.uniseats.control.gestione_accesso.LoginServlet;
import it.uniseats.model.beans.StudenteBean;
import it.uniseats.model.dao.StudenteDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletConfig;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginServletTest {



        private LoginServlet servlet;
        private MockHttpServletRequest request;
        private MockHttpServletResponse response;
        private MockHttpSession session;
        private  StudenteDAO dao = new StudenteDAO();
        private  StudenteBean s2;

        @BeforeEach
        public void setUp() {
            servlet = new LoginServlet();
            request = new MockHttpServletRequest();
            response = new MockHttpServletResponse();
            request.setSession(session);

        }


        @Test
        public void  oneWaySetup() throws ServletException {
            ServletConfig sg = new MockServletConfig();
            servlet.init(sg);
        }

    @Test
    public void loginTest() throws ServletException, IOException {
        request.addParameter("action", "Login");
        request.addParameter("email", "a.sabia15@studenti.unisa.it");
        request.addParameter("password", "accioLaurea");
        servlet.doGet(request,response);
        assertEquals("/view/LandingPageView.jsp" , response.getRedirectedUrl());
    }


    }

