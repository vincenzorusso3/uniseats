package it.uniseats.utils;

import com.stark.ai.Jarvis;
import it.uniseats.model.beans.AulaBean;
import it.uniseats.model.beans.PrenotazioneBean;
import it.uniseats.model.beans.StudenteBean;
import it.uniseats.model.dao.AulaDAO;
import it.uniseats.model.dao.PrenotazioneDAO;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;


public class Adapter {

  public static void listener(PrenotazioneBean p, StudenteBean s)
      throws SQLException, ParseException, CloneNotSupportedException {


    ArrayList<PrenotazioneBean> prenotazioniList = getPrenotazioni(p, s);

    if (prenotazioniList != null) {

      ArrayList<String> auleUtilizzate = getAuleUtilizzate(prenotazioniList);

      prenotazioniList.removeIf(prenotazione -> !prenotazione.getCodiceAula().equals("00"));

      if (prenotazioniList.size() == 20) {

        //parte JARVIS
        int[] codiciPrenotazioni = getCodiciPrenotazioni(prenotazioniList);

        int[] disposizione = Jarvis.disponiPrenotazioni(codiciPrenotazioni);

        ArrayList<AulaBean> listaAule =
            (ArrayList<AulaBean>) AulaDAO.doQuery(AulaDAO.doRetrieveAll, s.getDipartimento());

        if (listaAule != null) {

          AulaBean aulaDaUtilizzare = getAulaVuota(listaAule, auleUtilizzate);

          if (aulaDaUtilizzare != null) {

            ArrayList<PrenotazioneBean> settedPrenotazioni = new ArrayList<>();

            for (int i = 0; i < disposizione.length; i++) {
              updatePrenotazione(i, prenotazioniList, disposizione[i], aulaDaUtilizzare,
                  settedPrenotazioni);
            }

            updateDataBase(settedPrenotazioni);

          }

        }


      }

    }


  }

  private static void updateDataBase(ArrayList<PrenotazioneBean> settedPrenotazioni)
      throws SQLException, ParseException {

    for (PrenotazioneBean prenotazione : settedPrenotazioni) {
      PrenotazioneDAO.doQuery(PrenotazioneDAO.doUpdateAulaPosto, prenotazione);
    }

  }

  private static void updatePrenotazione(int i, ArrayList<PrenotazioneBean> prenotazioni, int tipo,
                                         AulaBean aulaDaUtilizzare,
                                         ArrayList<PrenotazioneBean> settedPrenotazioni) {

    String progressivo = (i < 10) ? String.valueOf(i) : ("0" + i);
    String posto = aulaDaUtilizzare.getCodice() + "-" + progressivo;

    for (PrenotazioneBean prenotazione : prenotazioni) {

      if (!settedPrenotazioni.contains(prenotazione)) {

        if (prenotazione.isSingolo() && tipo == 0) {
          setPrenotazione(prenotazione, aulaDaUtilizzare.getCodice(), posto, settedPrenotazioni);
        }

        if (!prenotazione.isSingolo() && tipo == 1) {
          setPrenotazione(prenotazione, aulaDaUtilizzare.getCodice(), posto, settedPrenotazioni);
        }

        break;

      }

    }

  }

  private static void setPrenotazione(PrenotazioneBean prenotazione, String codice, String posto,
                                      ArrayList<PrenotazioneBean> settedPrenotazioni) {
    prenotazione.setCodiceAula(codice);
    prenotazione.setCodicePosto(posto);
    settedPrenotazioni.add(prenotazione);
  }


  private static AulaBean getAulaVuota(ArrayList<AulaBean> listaAule,
                                       ArrayList<String> auleUtilizzate) {

    for (AulaBean aula : listaAule) {

      if (!auleUtilizzate.contains(aula.getCodice())) {
        return aula;
      }

    }

    return null;

  }

  private static int[] getCodiciPrenotazioni(ArrayList<PrenotazioneBean> prenotazioniList) {

    int[] prenotazioni = new int[prenotazioniList.size()];

    for (int i = 0; i < prenotazioniList.size(); i++) {
      if (prenotazioniList.get(i).isSingolo()) {
        prenotazioni[i] = 0;
      } else {
        prenotazioni[i] = 1;
      }
    }

    return prenotazioni;

  }

  private static ArrayList<String> getAuleUtilizzate(ArrayList<PrenotazioneBean> prenotazioniList) {

    ArrayList<String> auleUtilizzate = new ArrayList<>();
    for (PrenotazioneBean prenotazione : prenotazioniList) {
      if (!auleUtilizzate.contains(prenotazione.getCodiceAula())) {
        auleUtilizzate.add(prenotazione.getCodiceAula());
      }
    }

    return auleUtilizzate;

  }

  private static ArrayList<PrenotazioneBean> getPrenotazioni(PrenotazioneBean p, StudenteBean s)
      throws SQLException, ParseException {
    Date date = p.getData();
    String dipartimento = s.getDipartimento();

    ArrayList<String> parameter = new ArrayList<>();

    parameter.add(DateUtils.dateToString(date));
    parameter.add(dipartimento);

    return (ArrayList<PrenotazioneBean>) PrenotazioneDAO
        .doQuery(PrenotazioneDAO.findByDataDipartimento, parameter);
  }

}
