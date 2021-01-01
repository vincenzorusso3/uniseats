package it.uniseats.model.beans;

import java.util.Date;

public class PrenotazioneBean {

    private String codice;
    private String qrCode;
    private Date data;
    private boolean gruppo;
    private StudenteBean studente;
    private PostoBean posto;

    public PrenotazioneBean() {
    }

    public PrenotazioneBean(String codice,String qrCode, Date data, boolean gruppo, StudenteBean studente, PostoBean posto) {

        this.codice=codice;
        this.qrCode = qrCode;
        this.data = data;
        this.gruppo = gruppo;
        this.studente = studente;
        this.posto = posto;
    }

    public String getCodice() { return codice; }

    public String getQrCode() {
        return qrCode;
    }

    public Date getData() {
        return data;
    }

    public boolean isGruppo() {
        return gruppo;
    }

    public StudenteBean getStudente() {
        return studente;
    }

    public PostoBean getPosto() {
        return posto;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setGruppo(boolean gruppo) {
        this.gruppo = gruppo;
    }

    public void setStudente(StudenteBean studente) {
        this.studente = studente;
    }

    public void setPosto(PostoBean posto) {
        this.posto = posto;
    }

    public void setCodice(String codice) { this.codice = codice; }
}
