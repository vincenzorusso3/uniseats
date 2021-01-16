package it.uniseats.model.beans;

import java.util.ArrayList;

public class StudenteBean {

    /**
     * Variabili d'istanza
     */

    private String nome;
    private String cognome;
    private String matricola;
    private String email;
    private String password;
    private int anno;
    private String dipartimento;

    /**
     * Costruttore Vuoto
     */

    public StudenteBean() { }

    /**
     * Costruttore
     *
     * @param nome è il nome dello studente
     * @param cognome è il cognome dello studente
     * @param matricola è la matricola dello studente
     * @param email è l'email che lo studente usa per loggarsi al sito
     * @param password è la password che lo studente usa per loggarsi al sito
     * @param anno è l'anno di corso dello studente (1,2,3,4,5,6)
     * @param dipartimento è il dipartimento di appartenenza dello studente
     */

    public StudenteBean(String nome, String cognome, String matricola, String email, String password, int anno, String dipartimento) {
        this.nome = nome;
        this.cognome = cognome;
        this.matricola = matricola;
        this.email = email;
        this.password = password;
        this.anno = anno;
        this.dipartimento = dipartimento;
    }

    /**
     * @return il nome dello studente
     */
    public String getNome() {
        return nome;
    }

    /**
     * @return il cognome dello studente
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * @return la matricola dello studente
     */
    public String getMatricola() {
        return matricola;
    }

    /**
     * @return l'email dello studente
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return la password dello studente
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return l'anno di corso dello studente
     */
    public int getAnno() {
        return anno;
    }

    /**
     * @return il dipartimento di appartenenza dello studente
     */
    public String getDipartimento() {
        return dipartimento;
    }

    /**
     * Modifica il nome dello studente
     * @param nome è il nome dello studente
     */
    public void setNome(String nome) { this.nome = nome; }

    /**
     * Modifica il cognome dello studente
     * @param cognome è il cognome dello studente
     */
    public void setCognome(String cognome) { this.cognome = cognome; }

    /**
     * Modifica la matricola dello studente
     * @param matricola è la matricola dello studente
     */
    public void setMatricola(String matricola) { this.matricola = matricola; }

    /**
     * Modifica l'email dello studente
     * @param email è l'email che lo studente usa per loggarsi al sito
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Modifica la password dello studente
     * @param password è la password che lo studente usa per loggarsi al sito
     */
    public void setPassword(String password) { this.password = password; }

    /**
     * Modifica l'anno di corso dello studente
     * @param anno è l'anno di corso dello studente
     */
    public void setAnno(int anno) { this.anno = anno; }

    /**
     * Modifica il dipartimento dello studente
     * @param dipartimento è il dipartimento di appartenenza dello studente
     */
    public void setDipartimento(String dipartimento) { this.dipartimento = dipartimento; }
}
