package it.uniseats.model.beans;

import java.util.Date;

/**
 * La classe <code>PrenotazioneBean</code> rappresenta le prenotazioni realizzate
 * dagli studenti nel sistema Uniseats
 * Un oggetto <code>PrenotazioneBean</code> rappresenta la prenotazione di un posto.
 * Ogni prenotazione ha un codice identificativo, una data.
 * Singolo rappresenta il tipo di prenotazione (true = gruppo, false = singolo).
 * Inoltre ha un codice posto (A1-01, ...), un codice aula (A1, B2,..)
 * e la matricola dello studente che ha effettuato la prenotazione (0512105851, ...).
 */

public class PrenotazioneBean {

  /**
   * Variabili d'istanza.
   */
  private String codice;
  private Date data;
  private boolean singolo;
  private String codicePosto;
  private String codiceAula;
  private String matricolaStudente;

  /**
   * Costruttore vuoto.
   */
  public PrenotazioneBean() {
  }

  /**
   * Costruttore.
   *
   * @param codice            il <b>codice</b> della prenotazione
   * @param data              la <b>data</b> per la quale è prevista la prenotazione
   * @param singolo           la <b>tipologia</b> di prenotazione (true = gruppo, false = singolo)
   * @param codicePosto       il <b>codice</b> del posto a sedere nell'aula
   * @param codiceAula        il <b>codice</b> dell'aula per la quale si prenota un posto
   * @param matricolaStudente la <b>matricola</b> dello studente che effettua la prenotazione
   */
  public PrenotazioneBean(String codice, Date data, boolean singolo, String codicePosto,
                          String codiceAula, String matricolaStudente) {
    this.codice = codice;
    this.data = data;
    this.singolo = singolo;
    this.codicePosto = codicePosto;
    this.codiceAula = codiceAula;
    this.matricolaStudente = matricolaStudente;
  }

  /** Metodo di accesso.
    *
    * @return il <b>codice</b> della prenotazione
   */
  public String getCodice() {
    return codice;
  }

  /** Metodo di accesso.
   *
   *
   * @return la <b>data</b> della prenotazione
   */
  public Date getData() {
    return data;
  }

  /**Metodo di accesso.
   *
   *
   * @return true se la <b>prenotazione</b> è in gruppo, false se la prenotazione è singola
   */
  public boolean isSingolo() {
    return singolo;
  }

  /**Metodo di accesso.
   *
   * @return il <b>codice</b> del posto prenotato
   */
  public String getCodicePosto() {
    return codicePosto;
  }

  /** Metodo di accesso.
   *
   * @return l'<b>aula</b> per cui si effettua la prenotazione
   */
  public String getCodiceAula() {
    return codiceAula;
  }

  /**Metodo di accesso.
   *
   * @return la <b>matricola</b> dello studente che effettua la prenotazione
   */
  public String getMatricolaStudente() {
    return matricolaStudente;
  }

  /**
   * Modifica il codice del posto.
   *
   * @param codicePosto il <b>codice</b> del posto a sedere nell'aula
   */
  public void setCodicePosto(String codicePosto) {
    this.codicePosto = codicePosto;
  }

  /**
   * Modifica il codice dell'aula.
   *
   * @param codiceAula il <b>codice</b> dell'aula per la quale si prenota un posto
   */
  public void setCodiceAula(String codiceAula) {
    this.codiceAula = codiceAula;
  }

  /**
   * Modifica la matricola dello studente.
   *
   * @param matricolaStudente la <b>matricola</b> dello studente che effettua la prenotazione
   */
  public void setMatricolaStudente(String matricolaStudente) {
    this.matricolaStudente = matricolaStudente;
  }

  /**
   * Modifica la data della prenotazione.
   *
   * @param data la <b>data</b> per la quale è prevista la prenotazione
   */
  public void setData(Date data) {
    this.data = data;
  }

  /**
   * Modifica la tipologia della prenotazione.
   *
   * @param gruppo la <b>tipologia</b> di prenotazione (true = gruppo, false = singolo)
   */
  public void setSingolo(boolean gruppo) {
    this.singolo = gruppo;
  }

  /**
   * Modifica il codice della prenotazione.
   *
   * @param codice il <b>codice</b> della prenotazione
   */
  public void setCodice(String codice) {
    this.codice = codice;
  }

}


