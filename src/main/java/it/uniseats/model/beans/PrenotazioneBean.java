package it.uniseats.model.beans;

import java.util.Date;

public class PrenotazioneBean {

    /**
     * Variabili d'istanza
     */
    private String codice;
    private Date data;
    private boolean gruppo;
    private String codicePosto;
    private String edificio;
    private String codiceAula;
    private String matricolaStudente;

    /**
     * Costruttore vuoto
     */
    public PrenotazioneBean() {
    }

    /**
     * Costruttore
     *
     * @param codice il codice della prenotazione
     * @param data la data per la quale è prevista la prenotazione
     * @param gruppo la tipologia di prenotazione (1 = gruppo, 0 = singolo)
     * @param codicePosto il codice del posto a sedere nell'aula
     * @param edificio il codice dell'edificio che contiene l'aula
     * @param codiceAula il codice dell'aula per la quale si prenota un posto
     * @param matricolaStudente la matricola dello studente che effettua la prenotazione
     */
    public PrenotazioneBean(String codice, Date data, boolean gruppo, String codicePosto, String edificio,  String codiceAula, String matricolaStudente) {
        this.codice=codice;
        this.data = data;
        this.gruppo = gruppo;
        this.codicePosto= codicePosto;
        this.edificio = edificio;
        this.codiceAula=codiceAula;
        this.matricolaStudente=matricolaStudente;
    }

    /**
     * @return il codice della prenotazione
     */
    public String getCodice() { return codice; }

    /**
     * @return la data della prenotazione
     */
    public Date getData() { return data; }

    /**
     * @return 1 se la prenotazione è in gruppo, 0 se la prenotazione è singola
     */
    public boolean isGruppo() { return gruppo; }

    /**
     * @return il codice del posto prenotato
     */
    public String getCodicePosto() { return codicePosto; }

    /**
     * @return l'aula per cui si effettua la prenotazione
     */
    public String getCodiceAula() { return codiceAula; }

    /**
     * @return la matricola dello studente che effettua la prenotazione
     */
    public String getMatricolaStudente() { return matricolaStudente; }

    /**
     * @return l'edificio relativo alla prenotazione
     */
    public String getEdificio() { return edificio; }

    /**
     * Modifica il codice del posto
     * @param codicePosto il codice del posto a sedere nell'aula
     */
    public void setCodicePosto(String codicePosto) { this.codicePosto = codicePosto; }

    /**
     * Modifica il codice dell'aula
     * @param codiceAula il codice dell'aula per la quale si prenota un posto
     */
    public void setCodiceAula(String codiceAula) { this.codiceAula = codiceAula; }

    /**
     * Modifica la matricola dello studente
     * @param matricolaStudente la matricola dello studente che effettua la prenotazione
     */
    public void setMatricolaStudente(String matricolaStudente) { this.matricolaStudente = matricolaStudente; }

    /**
     * Modifica la data della prenotazione
     * @param data la data per la quale è prevista la prenotazione
     */
    public void setData(Date data) { this.data = data; }

    /**
     * Modifica la tipologia della prenotazione
     * @param gruppo la tipologia di prenotazione (1 = gruppo, 0 = singolo)
     */
    public void setGruppo(boolean gruppo) {
        this.gruppo = gruppo;
    }

    /**
     * Modifica il codice della prenotazione
     * @param codice il codice della prenotazione
     */
    public void setCodice(String codice) { this.codice = codice; }

    /**
     * Modifica l'edificio della prenotazione
     * @param edificio il codice dell'edificio che contiene l'aula
     */
    public void setEdificio(String edificio) { this.edificio = edificio; }
}
