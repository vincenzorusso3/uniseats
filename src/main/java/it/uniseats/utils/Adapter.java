package it.uniseats.utils;

import com.stark.ai.Jarvis;
import it.uniseats.model.beans.AulaBean;
import it.uniseats.model.beans.PostoBean;
import it.uniseats.model.beans.PrenotazioneBean;
import it.uniseats.model.beans.StudenteBean;
import it.uniseats.model.dao.AulaDao;
import it.uniseats.model.dao.PostoDao;
import it.uniseats.model.dao.PrenotazioneDao;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

/**
 * Classe che connette il sistema Uniseats con il modulo di intelligenza artificiale Jarvis.
 */
public class Adapter {

  /**
   * Metodo che schedula le prenotazioni previste per la data corrente.
   *
   * @throws SQLException se si verifica una eccezione
   * @throws ParseException se si verifica una eccezione
   * @throws CloneNotSupportedException se si verifica una eccezione
   */
  public static boolean todaySchedule() throws SQLException, ParseException,
      CloneNotSupportedException {

    String today = DateUtils.dateToString(new Date());

    ArrayList<String> list = (ArrayList<String>)
        AulaDao.doQuery(AulaDao.getDipartimenti, "temp");

    if (list != null) {

      ArrayList<String> parameter = new ArrayList<>();
      parameter.add(today);
      for (String dipartimento : list) {
        parameter.add(dipartimento);

        LinkedList<PrenotazioneBean> prenotazioniList = (LinkedList<PrenotazioneBean>)
            PrenotazioneDao.doQuery(PrenotazioneDao.findByDataDipartimento, parameter);


        if (prenotazioniList != null) {

          ArrayList<String> auleUtilizzate = getAuleUtilizzate(prenotazioniList);
          prenotazioniList.removeIf(prenotazione -> !prenotazione.getCodiceAula().equals("00"));

          int[] codiciPrenotazioni = getCodiciPrenotazioni(prenotazioniList);
          int count = 0;
          for (int i = prenotazioniList.size(); i < 20; i++) {
            codiciPrenotazioni[i] = 0;
            count++;
          }

          int[] disposizioneTemp = Jarvis.disponiPrenotazioni(codiciPrenotazioni);
          ArrayList<Integer> disposizioneArrayList = new ArrayList<>();

          for (int j : disposizioneTemp) {
            disposizioneArrayList.add(j);
          }

          for (; count > 0; count--) {
            disposizioneArrayList.remove(Integer.valueOf(0));
          }

          int[] disposizione = new int[disposizioneArrayList.size()];
          for (int i = 0; i < disposizione.length; i++) {
            disposizione[i] = disposizioneArrayList.get(i);
          }

          ArrayList<AulaBean> listaAule = (ArrayList<AulaBean>)
              AulaDao.doQuery(AulaDao.doRetrieveAll, dipartimento);

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

        parameter.remove(dipartimento);

      }

      return true;

    }

    return false;

  }

  /**
   * Metodo che controlla invoca il modulo di intelloigenza artificiale Jarvis.
   *
   * @param p <b>PrenotazioneBean</b>, prenotazione
   * @param s <b>StudenteBean</b>, studente
   * @throws SQLException se si verifica una ecceziona
   * @throws ParseException se si verifica una eccezione
   * @throws CloneNotSupportedException se si verifica una eccezione
   */
  public static boolean listener(PrenotazioneBean p, StudenteBean s)
      throws SQLException, ParseException, CloneNotSupportedException {

    String todayString = DateUtils.dateToString(new Date());
    String prenDateString = DateUtils.dateToString(p.getData());

    if (todayString.equals(prenDateString)) {

      prenotazioneGiornoCorrente(p, s);
      return true;

    } else {

      LinkedList<PrenotazioneBean> prenotazioniList = getPrenotazioni(p, s);

      if (prenotazioniList != null) {

        ArrayList<String> auleUtilizzate = getAuleUtilizzate(prenotazioniList);

        prenotazioniList.removeIf(prenotazione -> !prenotazione.getCodiceAula().equals("00"));

        if (prenotazioniList.size() == 20) {

          //parte JARVIS
          int[] codiciPrenotazioni = getCodiciPrenotazioni(prenotazioniList);

          int[] disposizione = Jarvis.disponiPrenotazioni(codiciPrenotazioni);

          ArrayList<AulaBean> listaAule = (ArrayList<AulaBean>)
              AulaDao.doQuery(AulaDao.doRetrieveAll, s.getDipartimento());

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

        return true;

      }

      return false;

    }

  }

  /**
   * Metodo che gestisce le prenotazioni del giorno corrente.
   *
   * @param p <b>PrenotazioneBean</b>, prenotazione
   * @param s <b>StudenteBean</b>, studente
   * @throws SQLException se si verifica una eccezione
   * @throws ParseException se si verifica una eccezione
   */
  private static void prenotazioneGiornoCorrente(PrenotazioneBean p, StudenteBean s)
      throws SQLException, ParseException {

    LinkedList<PrenotazioneBean> prenotazioniList = getPrenotazioni(p, s);

    if (prenotazioniList != null) {

      if (prenotazioniList.size() < 60) {

        ArrayList<AulaBean> listaAule = (ArrayList<AulaBean>)
            AulaDao.doQuery(AulaDao.doRetrieveAll, s.getDipartimento());

        if (listaAule != null) {

          for (AulaBean aula : listaAule) {
            System.out.println(aula.getCodice());

            ArrayList<PostoBean> posti = (ArrayList<PostoBean>)
                PostoDao.doQuery(PostoDao.doRetrieveByAulaCode, aula.getCodice());

            if (posti != null) {

              for (PrenotazioneBean pren : prenotazioniList) {

                posti.removeIf(posto -> posto.getCodice().equals(pren.getCodicePosto()));

              }

              if (!posti.isEmpty()) {

                p.setCodicePosto(posti.get(0).getCodice());
                p.setCodiceAula(aula.getCodice());
                break;

              }

            }

          }

          if (!p.getCodicePosto().equals("00")) {

            //la prenotazione e' andata a buon fine
            PrenotazioneDao.doQuery(PrenotazioneDao.doUpdateAulaPosto, p);

          } else {

            //ERRORE
            System.out.println("ERRORE");

          }

        }

      }

    }

  }

  /**
   * Metodo che effettua l'update delle prenotazioni nel database
   * assegnando i posti alle prenotazioni.
   *
   * @param settedPrenotazioni prenotazioni da sistemare nel database
   *
   * @throws SQLException se si verifica una eccezione
   * @throws ParseException se si verifica una eccezione
   */
  private static void updateDataBase(ArrayList<PrenotazioneBean> settedPrenotazioni)
      throws SQLException, ParseException {

    for (PrenotazioneBean prenotazione : settedPrenotazioni) {
      PrenotazioneDao.doQuery(PrenotazioneDao.doUpdateAulaPosto, prenotazione);
    }

  }

  /**
   * Metodo che modifica una prenotazione.
   *
   * @param i elemento iesimo della lista
   * @param prenotazioni <b>PrenotazioneBean</b>, la prenotazione
   * @param tipo <b>Tipologia</b> della prenotazione
   * @param aulaDaUtilizzare <b>AulaBean</b> a cui saranno assegnate le prenotazioni
   * @param settedPrenotazioni <b>PrenotazioneBean</b> a cui sono assegnati i posti
   */
  private static void updatePrenotazione(int i, LinkedList<PrenotazioneBean> prenotazioni, int tipo,
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

  /**
   * Metodo per settare i parametri della prenotazione.
   *
   * @param prenotazione <b>PrenotazioneBean</b> a cui saranno settati i parametri
   * @param codice <b>chiave primaria</b> della aula
   * @param posto <b>posto</b> nella aula
   * @param settedPrenotazioni <b>Prenotazione</b>, prenotazione sistemata
   */
  private static void setPrenotazione(PrenotazioneBean prenotazione, String codice, String posto,
                                      ArrayList<PrenotazioneBean> settedPrenotazioni) {
    prenotazione.setCodiceAula(codice);
    prenotazione.setCodicePosto(posto);
    settedPrenotazioni.add(prenotazione);
  }

  /**
   * Metodo che restituisce, fra tutte le aule, una aula libera.
   *
   * @param listaAule <b>lista delle aule</b> presenti nel database
   * @param auleUtilizzate <b> aule occupate</b>
   * @return aula libera
   */
  private static AulaBean getAulaVuota(ArrayList<AulaBean> listaAule,
                                       ArrayList<String> auleUtilizzate) {

    for (AulaBean aula : listaAule) {

      if (!auleUtilizzate.contains(aula.getCodice())) {
        return aula;
      }

    }

    return null;

  }

  /**
   * Metodo che restituisce i codici delle prenotazioni.
   *
   * @param prenotazioniList <b>Lista delle prenotazioni</b>
   *
   * @return array di codici delle prenotazioni passate come parametro
   */
  private static int[] getCodiciPrenotazioni(LinkedList<PrenotazioneBean> prenotazioniList) {

    int[] prenotazioni = new int[20];

    for (int i = 0; i < prenotazioniList.size(); i++) {
      if (prenotazioniList.get(i).isSingolo()) {
        prenotazioni[i] = 0;
      } else {
        prenotazioni[i] = 1;
      }
    }

    return prenotazioni;

  }

  /**Metodo che restituisce le aule nel db che hanno posti già occupati.
   *
   * @param prenotazioniList <b>Lista delle prenotazioni</b>
   *
   * @return lista delle aule utilizzate
   */
  private static ArrayList<String> getAuleUtilizzate(
      LinkedList<PrenotazioneBean> prenotazioniList) {

    ArrayList<String> auleUtilizzate = new ArrayList<>();
    for (PrenotazioneBean prenotazione : prenotazioniList) {
      if (!auleUtilizzate.contains(prenotazione.getCodiceAula())) {
        auleUtilizzate.add(prenotazione.getCodiceAula());
      }
    }

    return auleUtilizzate;

  }

  /**
   * Metodo per ottenere le prenotazioni presenti nel database.
   *
   * @param p <b>un oggetto di tipo prenotazione</b>
   * @param s <b>un oggetto di tipo studente</b>
   * @return la lista delle prenotazioni
   * @throws SQLException se si verifica una eccezione
   * @throws ParseException se si verifica una eccezione
   */
  private static LinkedList<PrenotazioneBean> getPrenotazioni(PrenotazioneBean p, StudenteBean s)
      throws SQLException, ParseException {
    Date date = p.getData();
    String dipartimento = s.getDipartimento();

    ArrayList<String> parameter = new ArrayList<>();

    parameter.add(DateUtils.dateToString(date));
    parameter.add(dipartimento);

    return (LinkedList<PrenotazioneBean>) PrenotazioneDao.doQuery(
        PrenotazioneDao.findByDataDipartimento, parameter);
  }

}
