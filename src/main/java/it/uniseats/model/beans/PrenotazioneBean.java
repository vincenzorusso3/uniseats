package it.uniseats.model.beans;

import java.util.Date;

public class PrenotazioneBean {

    private String codice;
    private String qrCode;
    private Date data;
    private boolean gruppo;
    private String codicePosto;
    private String codiceAula;
    private String matricolaStudente;


    public PrenotazioneBean() {
    }

    public PrenotazioneBean(String codice,String qrCode, Date data, boolean gruppo, String codicePosto, String codiceAula, String matricolaStudente) {

        this.codice=codice;
        this.qrCode = qrCode;
        this.data = data;
        this.gruppo = gruppo;
        this.codicePosto= codicePosto;
        this.codiceAula=codiceAula;
        this.matricolaStudente=matricolaStudente;
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

    public String getCodicePosto() { return codicePosto; }

    public String getCodiceAula() { return codiceAula; }

    public String getMatricolaStudente() { return matricolaStudente; }

    public void setCodicePosto(String codicePosto) { this.codicePosto = codicePosto; }

    public void setCodiceAula(String codiceAula) { this.codiceAula = codiceAula; }

    public void setMatricolaStudente(String matricolaStudente) { this.matricolaStudente = matricolaStudente; }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setGruppo(boolean gruppo) {
        this.gruppo = gruppo;
    }

    public void setCodice(String codice) { this.codice = codice; }
}
