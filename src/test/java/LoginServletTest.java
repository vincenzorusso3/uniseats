import it.uniseats.control.gestione_accesso.LoginServlet;
import it.uniseats.model.beans.StudenteBean;
import it.uniseats.utils.SHA512Utils;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class LoginServletTest extends Mockito {

    private static LoginServlet servlet;
    private static MockHttpServletRequest request;
    private static MockHttpServletResponse response;
    private static StudenteBean bean;


    @BeforeEach
    void load() {
        servlet = new LoginServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

   @Test
    public void logInTest(){
            bean = new StudenteBean("Mario", "Rossi", "0512102938", "m.rossi@studenti.unisa.it", "marioRossi",2,"Matematica");
            request.setParameter("action","Login");
            request.setParameter("email","a.sabia15@studenti.unisa.it");
            request.setParameter("password", SHA512Utils.getSHA512("accioLaurea"));




        }




}
