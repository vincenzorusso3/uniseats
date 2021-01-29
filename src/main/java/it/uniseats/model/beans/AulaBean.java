package it.uniseats.model.beans;

/**
 * La classe <code>AulaBean</code> rappresenta l'aula contenente
 * i posti che saranno occupati dagli studenti quando
 * effettueranno una prenotazione con il sistema UniSeats
 * Un oggetto <code>AulaBean</code> rappresenta un aula.
 * Ogni aula ha un codice identificativo (A1, A2, ..)
 * un dipartimento di appartenenza (Fisica, Farmacia, ..)
 * un numero totale di posti al suo interno
 * l'edificio in cui è collocata (F, B1,..)
 */

public class AulaBean {

  /**
   * Variabili d'istanza.
   */
  private String codice;
  private String dipartimento;
  private int nPosti;
  private String edificio;

  /**
   * Costruttore vuoto.
   */

  public AulaBean() {
  }

  /**
   * Costruttore.
   *
   * @param codice       rappresenta il <b>codice</b> identificativo di un'aula
   * @param dipartimento rappresenta a quale <b>dipartimento</b> appartiene
   * @param nPosti       rappresenta quanti <b>posti</b> contiene
   * @param edificio     rappresenta in quale <b>edificio</b> è collocata
   */

  public AulaBean(String codice, String dipartimento, int nPosti, String edificio) {
    this.codice = codice;
    this.dipartimento = dipartimento;
    this.nPosti = nPosti;
    this.edificio = edificio;
  }


  /**
   * Prelevo codice Aula.
   *
   * @return il <b>codice</b> dell'aula
   */
  public String getCodice() {
    return codice;
  }

  /**
   * Prelevo dipartimento.
   *
   * @return il <b>dipartimento</b>
   */
  public String getDipartimento() {
    return dipartimento;
  }

  /**
   * Prelevo numero posti.
   *
   * @return il <b>numero posti</b> totali
   */
  public int getnPosti() {
    return nPosti;
  }


  /**
   *Prelevo edificio.
   *
   * @return l'<b>edificio</b> a cui appartiene
   */
  public String getEdificio() {
    return edificio;
  }

  /**
   * Modifica il codice dell'aula.
   *
   * @param codice è il <b>codice</b> identificativo di un'aula
   */
  public void setCodice(String codice) {
    this.codice = codice;
  }

  /**
   * Modifica il dipartimento di un'aula.
   *
   * @param dipartimento è il <b>dipartimento</b> di appartenenza
   */
  public void setDipartimento(String dipartimento) {
    this.dipartimento = dipartimento;
  }

  /**
   * Modifica il numero di posti di un'aula.
   *
   * @param nPosti è il <b>numero di posti totali</b> di un'aula
   */
  public void setnPosti(int nPosti) {
    this.nPosti = nPosti;
  }

  /**
   * Modifica l'edificio di un'aula.
   *
   * @param edificio è l'<b>edificio</b> in cui è collocata
   */
  public void setEdificio(String edificio) {
    this.edificio = edificio;
  }


}
