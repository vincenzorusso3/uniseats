package com.uniseats.prenotazioni.bean;

public class PostoBean {

    private int codice;
    private Boolean occupato;

    public PostoBean() {
        occupato = false;
    }

    public PostoBean(int codice, Boolean occupato) {
        this.codice = codice;
        this.occupato = occupato;
    }

    public int getCodice() {
        return codice;
    }

    public Boolean isOccupato() {
        return occupato;
    }

    public void setOccupato(Boolean occupato) {
        this.occupato = occupato;
    }

    public AulaBean getAula() {
        /*TODO: write getAula() method*/
        return null;
    }

}
