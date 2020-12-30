package com.uniseats.prenotazioni;

import java.util.ArrayList;

public class StudenteBean {

    private String nome;
    private String cognome;
    private String matricola;
    private String email;
    private String password;
    private int anno;
    private String dipartimento;

    private ArrayList<PrenotazioneBean> prenotazione;

    public StudenteBean() {

    }

    public StudenteBean(String nome, String cognome, String matricola, String email, String password, int anno, String dipartimento) {
        this.nome = nome;
        this.cognome = cognome;
        this.matricola = matricola;
        this.email = email;
        this.password = password;
        this.anno = anno;
        this.dipartimento = dipartimento;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getMatricola() {
        return matricola;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public String getDipartimento() {
        return dipartimento;
    }

    public ArrayList<PrenotazioneBean> getPrenotazione() {
        return prenotazione;
    }
}
