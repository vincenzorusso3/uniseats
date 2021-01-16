package it.uniseats.model.beans;

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
     * @param codice codice identificativo di un posto
     */
    public PostoBean(String codice, String codiceAula) {
        this.codice = codice;
        this.codiceAula = codiceAula;
    }

    public String getCodice() {
        return codice;
    }

    public String getCodiceAula() {
        return codiceAula;
    }

    public void setCodiceAula(String codiceAula) {
        this.codiceAula = codiceAula;
    }

    public void setCodice(String codice) { this.codice = codice; }
}


