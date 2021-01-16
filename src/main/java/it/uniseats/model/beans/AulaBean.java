package it.uniseats.model.beans;

import java.util.List;

public class AulaBean {

    /**
     * Variabili d'istanza
     */
    private String codice;
    private String dipartimento;
    private int nPosti;
    private String edificio;

    /**
     * Costruttore vuoto
     */

    public AulaBean() {}

    /**
     * Costruttore
     * @param codice rappresenta il codice identificativo di un'aula
     * @param dipartimento rappresenta a quale dipartimento appartiene
     * @param nPosti rappresenta quanti posti contiene
     * @param edificio rappresenta in quale edificio è collocata
     */

    public AulaBean(String codice, String dipartimento, int nPosti, String edificio) {
        this.codice = codice;
        this.dipartimento = dipartimento;
        this.nPosti = nPosti;
        this.edificio = edificio;
    }


    /**
     *
     * @return codice aula
     */
    public String getCodice() {
        return codice;
    }

    /**
     *
     * @return dipartimento
     */
    public String getDipartimento() {
        return dipartimento;
    }

    /**
     *
     * @return numero posti
     */
    public int getnPosti() {
        return nPosti;
    }


    /**
     *
     * @return edificio
     */
    public String getEdificio() {
        return edificio;
    }

    /**
     * Modifica il codice dell'aula
     * @param codice identificativo di un'aula
     */
    public void setCodice(String codice) { this.codice = codice; }

    /**
     * Modifica il dipartimento di un'aula
     * @param dipartimento è il dipartimento di appartenenza
     */
    public void setDipartimento(String dipartimento) { this.dipartimento = dipartimento; }

    /**
     * Modifica il numero di posti di un'aula
     * @param nPosti posti totali di un'aula
     */
    public void setnPosti(int nPosti) { this.nPosti = nPosti; }

    /**
     * Modifica l'edificio di un'aula
     * @param edificio edificio in cui è collocata
     */
    public void setEdificio(String edificio) { this.edificio = edificio; }


}
