package it.uniseats.utils;

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

    for ()


  }

  public void x(String dipartimento) throws SQLException {
    ArrayList<AulaBean> listaAule =
        (ArrayList<AulaBean>) AulaDAO.doQuery(AulaDAO.doRetrieveAll, dipartimento);

    if (listaAule != null) {

      for (AulaBean a : listaAule) {

        int nposti = a.getnPosti();
        if (nposti != 0) {
          nposti--;
          a.setnPosti(nposti);
          if (nposti == 0) {
            //Parte algoritmo
            int[] array; // Posti organizzati by AI

          }
          break;
        }

      }

    }
  }


}
