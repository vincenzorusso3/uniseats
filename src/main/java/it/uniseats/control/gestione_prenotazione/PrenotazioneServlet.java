package it.uniseats.control.gestione_prenotazione;

import it.uniseats.model.beans.PrenotazioneBean;
import it.uniseats.model.beans.StudenteBean;
import it.uniseats.model.dao.PrenotazioneDao;
import it.uniseats.model.dao.StudenteDao;
import it.uniseats.utils.Adapter;
import it.uniseats.utils.DateUtils;
import it.uniseats.utils.QrCodeGenerator;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet per la creazione di una nuova prenotazione.
 */

@WebServlet(name = "PrenotazioneServlet", value = "/PrenotazioneServlet")
public class PrenotazioneServlet extends HttpServlet {

  private final String dbError = "Impossibile procedere con la prenotazione, riprova più tardi!";
  private final String auleFull = "Nessun posto disponibile per la data selezionata!";
  private final String hasPrenotation = "Hai già una prenotazione per questa data!";
  private final String invalidDate = "La data scelta non è valida!";

  private final String jspPath = "/view/prenotazione/NuovaPrenotazioneView.jsp";

  /**
   * Metodo per effettuare richieste doGet.
   *
   * @param request  HttpServletRequest
   * @param response HttpServletResponse
   * @throws ServletException se si verifica una eccezione
   * @throws IOException se si verifica una eccezione
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doPost(request, response);
  }


  /**
   * Metodo per effettuare richieste doPost.
   *
   *
   *  @param request HttpServletRequest
   * @param response HttpServletResponse
   * @throws ServletException se si verifica una eccezione
   * @throws IOException se si verifica una eccezione
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String action = request.getParameter("action");

    if (action != null) {


      switch (action) {

        case "prenotazioneSingola":
          try {
            prenotazione(request, response, true);
          } catch (ParseException | SQLException | CloneNotSupportedException e) {
            e.printStackTrace();
          }
          break;

        case "prenotazioneGruppo":
          try {
            prenotazione(request, response, false);
          } catch (ParseException | SQLException | CloneNotSupportedException e) {
            e.printStackTrace();
          }
          break;

        default:
          break;

      }

    } else {

      RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(jspPath);
      dispatcher.forward(request, response);

    }

  }

  /**
   * Metodo per creare una nuova prenotazione.
   *
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   * @param isPrenotazioneSingola per verificare se la prenotazione è singola
   * @throws ParseException se si verifica una eccezione
   * @throws SQLException se si verifica una eccezione
   * @throws ServletException se si verifica una eccezione
   * @throws IOException se si verifica una eccezione
   */
  private void prenotazione(HttpServletRequest request, HttpServletResponse response,
                            boolean isPrenotazioneSingola)
      throws ParseException, SQLException, ServletException, IOException,
      CloneNotSupportedException {

    String dateTemp;

    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(jspPath);

    if (isPrenotazioneSingola) {
      dateTemp = request.getParameter("dateValueSingolo");
    } else {
      dateTemp = request.getParameter("dateValueGruppo");
    }

    String date = DateUtils.englishToItalian(dateTemp);

    //controllo la validità della data selezionata
    if (isDateValid(date, isPrenotazioneSingola)) {

      StudenteBean user = getUser(request);
      String matricola = user.getMatricola();

      //controllo l'assenza di prenotazioni già effettuate per la data inserita
      if (checkPrenotazioni(matricola, date)) {

        //contollo la disponibilità di posti nelle aule
        if (checkPostiAule(user.getDipartimento(), date)) {

          //lo studente NON ha prenotazioni e c'e' almeno un posto libero
          String qrCode = QrCodeGenerator.generateCode(matricola, date);

          PrenotazioneBean prenotazione =
              new PrenotazioneBean(qrCode, DateUtils.parseDate(date), isPrenotazioneSingola, "00",
                  "00", matricola);
          Integer result = (Integer) PrenotazioneDao.doQuery(PrenotazioneDao.doSave, prenotazione);

          //se la prenotazione è stata salvata nel database con successo,
          // viene inoltrata al modulo IA oper l'assegnazione del posto a sedere.
          if (result != null && result > 0) {


            Adapter.listener(prenotazione, user);

            dispatcher = getServletContext().getRequestDispatcher(
                "/view/prenotazioni_effettuate/VisualizzaPrenotazioniView.jsp");

          } else {
            request.setAttribute("errore", dbError);
          }

        } else {
          request.setAttribute("errore", auleFull);
        }

      } else {
        request.setAttribute("errore", hasPrenotation);
      }

    } else {
      request.setAttribute("errore", invalidDate);
    }

    dispatcher.forward(request, response);

  }

  /**
   * Controllo che la data inserita per la prenotazione sia valida.
   *
   * @param date la <b>data</b> selezionata per la prenotazione
   * @param isPrenotazioneSingola la <b>tipologia</b> di prenotazione
   * @return <b>false</b> altrimenti
   * @throws ParseException se si verifica una eccezione
   */
  private boolean isDateValid(String date, boolean isPrenotazioneSingola) throws ParseException {

    Date today = new Date();
    Date selectedDay = DateUtils.parseDate(date);

    if (selectedDay.compareTo(today) > 0) {
      return true;
    }

    String todayString = DateUtils.dateToString(today);
    String selectedDayString = DateUtils.dateToString(selectedDay);

    if (selectedDayString.equals(todayString)) {
      return isPrenotazioneSingola;
    }

    return false;

  }

  /**
   * Restituisce lo studente loggato.
   *
   * @param request HttpServletRequest
   * @return lo <b>studente</b> loggato
   * @throws SQLException se si verifica una eccezione
   */
  private StudenteBean getUser(HttpServletRequest request) throws SQLException {

    HttpSession session = request.getSession(true);
    String email = (String) session.getAttribute("email");

    return (StudenteBean) StudenteDao.doQuery(StudenteDao.doRetrieveByEmail, email);

  }

  /**
   * Controllo che lo studente non abbia già effettuato una prenotazione per la stessa data.
   *
   * @param matricola la <b>matricola</b> dello studente
   * @param date      la <b>data di prenotazione</b> selezionata
   * @return <b>false</b> altrimenti
   * @throws SQLException se si verifica una eccezione
   * @throws ParseException se si verifica una eccezione
   */
  private boolean checkPrenotazioni(String matricola, String date)
      throws SQLException, ParseException {

    Date selectedDay = DateUtils.parseDate(date);

    ArrayList<PrenotazioneBean> prenotazioni =
        (ArrayList<PrenotazioneBean>) PrenotazioneDao.doQuery("doFindPrenotazioni", matricola);
    if (prenotazioni != null) {
      for (PrenotazioneBean p : prenotazioni) {

        if (DateUtils.parseDate(DateUtils.dateToString(p.getData())).compareTo(selectedDay) == 0) {
          return false;
        }
      }
      return true;
    } else {
      return false;
    }

  }

  /**
   * Controllo la disponibilità di posti a sedere nelle aule.
   *
   * @param dipartimento il dipartimento per la quale si effettua il controllo delle aule
   *                     ( corrisponde al dipartimento dello studente)
   * @return <b>false</b> altrimenti
   * @throws SQLException se si verifica una eccezione
   */
  private boolean checkPostiAule(String dipartimento, String data)
      throws SQLException, ParseException {

    ArrayList<String> parameter = new ArrayList<>();
    parameter.add(data);
    parameter.add(dipartimento);

    LinkedList<PrenotazioneBean> prenList = (LinkedList<PrenotazioneBean>) PrenotazioneDao
        .doQuery(PrenotazioneDao.findByDataDipartimento, parameter);

    if (prenList != null) {
      return prenList.size() < 60;
    }
    return false;

  }

}
