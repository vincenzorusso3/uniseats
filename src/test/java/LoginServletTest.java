


import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;



class LoginServletTest extends Mockito {
    private LoginServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    /**
     * Before.
     */
    @Before
    public void setUp() {
        servlet = new LoginServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void logInTest() throws ServletException, IOException  {
        request.setParameter("email", "a.sabia15@studenti.unisa.it");
        request.setParameter("password", "accioLaurea");
        assertThrows(IllegalArgumentException.class, () -> {
            servlet.doGet(request, response);
        });
    }



}





