package it.uniseats.model.beans;

public class PostoBean {

    private String codice;
    private AulaBean aula;


    public PostoBean(){}

    public PostoBean(String codice ) {
        this.codice = codice;
    }

    public String getCodice() {
        return codice;
    }


    public void setCodice(String codice) { this.codice = codice; }

    public AulaBean getAula() {
        /*TODO: write getAula() method*/
        return null;
    }

    public void setAula(AulaBean aula) {
        this.aula = aula;
    }
}
