package it.uniseats.model.beans;

public class PostoBean {

    /**
     * Variabile istanza
     */
    private String codice;

    /**
     * Costruttore vuoto
     */
    public PostoBean() { }

    /**
     * Costruttore
     * @param codice codice identificativo di un posto
     */
    public PostoBean(String codice) {
        this.codice = codice;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) { this.codice = codice; }
}
