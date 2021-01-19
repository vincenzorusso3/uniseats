
import it.uniseats.control.gestione_utente.ProfiloUtenteServlet;
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
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfiloUtenteServletTest {

    private ProfiloUtenteServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockHttpSession session;

    @BeforeEach
    public void setUp() {
        servlet = new ProfiloUtenteServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.setSession(session);

    }


    @BeforeEach
    public void  oneWaySetup() throws ServletException {
        ServletConfig sg = new MockServletConfig();
        servlet.init(sg);
    }


    @Test
    public void updateTest() throws ServletException, IOException, SQLException {
        StudenteBean bean=new StudenteBean("Daniele","Salerno","0512137888","d.salerno7@studenti.unisa,it",
                "carrello",3,"Informatica");
        StudenteDAO.doQuery(StudenteDAO.doSave,bean);
        request.addParameter("action","confermaUpdate");

        request.addParameter("annomod","5");
        request.getSession().setAttribute("logged", true);
        request.getSession().setAttribute("nome", bean.getNome());
        request.getSession().setAttribute("cognome", bean.getCognome());
        request.getSession().setAttribute("matricola", bean.getMatricola());
        request.getSession().setAttribute("email", bean.getEmail());
        request.getSession().setAttribute("anno", bean.getAnno());
        request.getSession().setAttribute("dipartimento", bean.getDipartimento());





        servlet.doGet(request,response);
        assertEquals(null,request.getAttribute("errore"));
        assertEquals("/view/profilo_utente/ProfiloUtenteView.jsp", response.getForwardedUrl());

    }

    @Test
    public void deleteTest() throws ServletException, IOException{

        StudenteBean bean=new StudenteBean("Daniele","Salerno","0512137888","d.salerno7@studenti.unisa,it",
                "carrello",3,"Informatica");

        request.addParameter("action","confermaDelete");


        request.getSession().setAttribute("logged", true);
        request.getSession().setAttribute("nome", bean.getNome());
        request.getSession().setAttribute("cognome", bean.getCognome());
        request.getSession().setAttribute("matricola", bean.getMatricola());
        request.getSession().setAttribute("email", bean.getEmail());
        request.getSession().setAttribute("anno", bean.getAnno());
        request.getSession().setAttribute("dipartimento", bean.getDipartimento());









        servlet.doGet(request,response);
        assertEquals("Profilo eliminato con successo",request.getAttribute("errore"));
        assertEquals("/view/login/LoginView.jsp", response.getForwardedUrl());

    }



}


