package it.uniseats.model.beans;

import java.util.ArrayList;

public class StudenteBean {

    private String nome;
    private String cognome;
    private String matricola;
    private String email;
    private String password;
    private int anno;
    private String dipartimento;



    public StudenteBean() { }

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

    public String getDipartimento() {
        return dipartimento;
    }

    public void setNome(String nome) { this.nome = nome; }

    public void setCognome(String cognome) { this.cognome = cognome; }

    public void setMatricola(String matricola) { this.matricola = matricola; }

    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password; }

    public void setAnno(int anno) { this.anno = anno; }

    public void setDipartimento(String dipartimento) { this.dipartimento = dipartimento; }
}
