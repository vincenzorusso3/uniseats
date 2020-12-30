package com.uniseats.prenotazioni;

public class PostoBean {

    private String codice;
    private Boolean occupato;

    public PostoBean() {
        occupato = false;
    }

    public PostoBean(String codice, Boolean occupato) {
        this.codice = codice;
        this.occupato = occupato;
    }

    public String getCodice() {
        return codice;
    }

    public Boolean isOccupato() {
        return occupato;
    }

    public void setOccupato(Boolean occupato) {
        this.occupato = occupato;
    }

    public AulaBean getAula(){
        /*TODO: write getAula() method*/
        return null;
    }
}
