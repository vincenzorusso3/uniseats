package it.uniseats.model.beans;

/**
 * Un oggetto <code>PostoBean</code> rappresenta un singolo posto all'interno di un'aula.
 * Ogni posto ha un codice identificativo (A1-01, A1-02,..) e il codice dell'aula a cui appartiene (A1, A2, ..)
 */

public class PostoBean {

    /**
     * Variabile istanza
     */
    private String codice;
    private String codiceAula;

    /**
     * Costruttore vuoto
     */
    public PostoBean() { }


    /**
     * Costruttore
     * @param codice è il <b>codice</b> identificativo di un posto
     * @param codiceAula è il <b>codice</b> dell'aula a cui appartiene
     */
    public PostoBean(String codice, String codiceAula) {
        this.codice = codice;
        this.codiceAula = codiceAula;
    }

    /**
     *
     * @return il <b>codice</b> del posto
     */
    public String getCodice() {
        return codice;
    }

    /**
     *
     * @return il <b>codice</b> dell'aula
     */
    public String getCodiceAula() {
        return codiceAula;
    }

    /**
     * Modifica il codice dell'aula
     * @param codiceAula è il <b>codice</b> dell'aula a cui appartiene
     */
    public void setCodiceAula(String codiceAula) {
        this.codiceAula = codiceAula;
    }

    /**
     * Modifica il codice del posto
     * @param codice è il <b>codice</b> identificativo del posto
     */
    public void setCodice(String codice) { this.codice = codice; }
}


