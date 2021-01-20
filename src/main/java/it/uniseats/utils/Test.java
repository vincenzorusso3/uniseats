/*package it.uniseats.utils;

import it.uniseats.model.beans.AulaBean;
import it.uniseats.model.beans.PrenotazioneBean;
import it.uniseats.model.beans.StudenteBean;
import it.uniseats.model.dao.AulaDAO;
import it.uniseats.model.dao.PrenotazioneDAO;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;


public class Test {

  public static void listener(PrenotazioneBean p, StudenteBean s)
      throws SQLException, ParseException {

    Date date = p.getData();
    String dipartimento = s.getDipartimento();

    ArrayList<String> parameter = new ArrayList<>();

    parameter.add(DateUtils.dateToString(date));
    parameter.add(dipartimento);

    ArrayList<PrenotazioneBean> pList = (ArrayList<PrenotazioneBean>) PrenotazioneDAO.doQuery(PrenotazioneDAO.findByDataDipartimento, parameter);

    if (pList != null) {

      ArrayList<String> auleUtilizzate = new ArrayList<>();
      for (PrenotazioneBean prenotazione : pList) {
        if (!auleUtilizzate.contains(prenotazione.getCodiceAula()))
          auleUtilizzate.add(prenotazione.getCodiceAula());
      }

      pList.removeIf(prenotazione -> !prenotazione.getCodiceAula().equals("00"));

      if (pList.size() == 20) {

        //parte JARVIS
        int[] prenotazioni = new int[pList.size()];

        for (int i = 0; i < pList.size(); i++) {
          if (pList.get(i).isSingolo())
            prenotazioni[i] = 0;
          else
            prenotazioni[i] = 1;
        }

        int[] disposizione=; //TODO: collegare jarvis

        ArrayList<AulaBean> listaAule =
            (ArrayList<AulaBean>) AulaDAO.doQuery(AulaDAO.doRetrieveAll, dipartimento);

        AulaBean aulaDaUtilizzare = null;
        for (AulaBean aula : listaAule) {

          if (!auleUtilizzate.contains(aula.getCodice())) {
            aulaDaUtilizzare = aula;
            break;
          }

        }

        if (aulaDaUtilizzare == null) {

          int count = 0;
          for (int i : disposizione) {

            if (i == 0) { //prenotazione singola

              for (PrenotazioneBean pren : pList) {

                if (pren.isSingolo()) {
                  pren.setCodiceAula(aulaDaUtilizzare.getCodice());
                  pren.setCodicePosto(aulaDaUtilizzare.getCodice() + "-" + count);
                  count++;
                }

              }

            } else { //prenotazione in gruppo
              for (PrenotazioneBean pren : pList) {

                if (!pren.isSingolo()) {
                  pren.setCodiceAula(aulaDaUtilizzare.getCodice());
                  pren.setCodicePosto(aulaDaUtilizzare.getCodice() + "-" + count);
                  count++;
                }

              }

            }

          }

        }

      }

    }



  }

}
*/