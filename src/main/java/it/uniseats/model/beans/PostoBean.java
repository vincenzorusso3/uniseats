package it.uniseats.model.beans;

public class PostoBean {

    private String codice;

    public PostoBean() { }

    public PostoBean(String codice) {
        this.codice = codice;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) { this.codice = codice; }
}
