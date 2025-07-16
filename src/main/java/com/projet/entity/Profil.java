package com.projet.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "profil")
public class Profil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profil")
    private int idProfil;

    @Column(name = "type_adherant", nullable = false)
    private String typeAdherant;

    @Column(name = "quota_pret", nullable = false)
    private int quotaPret;

    @Column(name = "quota_reservation", nullable = false)
    private int quotaReservation;
    
    @Column(name = "quota_prolongement", nullable = false)
    private int quotaProlongement;

    @Column(name ="jour_penalite" , nullable = false)
    private int jour_penalite;

    @Column(name = "jour_pret", nullable = false)
    private int jourPret;
    
    public Profil() {}

    public Profil(int idProfil, String typeAdherant, int quotaPret, int quotaReservation) {
        this.idProfil = idProfil;
        this.typeAdherant = typeAdherant;
        this.quotaPret = quotaPret;
        this.quotaReservation = quotaReservation;
    }

    public int getIdProfil() {
        return idProfil;
    }

    public void setIdProfil(int idProfil) {
        this.idProfil = idProfil;
    }

    public String getTypeAdherant() {
        return typeAdherant;
    }

    public void setTypeAdherant(String typeAdherant) {
        this.typeAdherant = typeAdherant;
    }

    public int getQuotaPret() {
        return quotaPret;
    }

    public void setQuotaPret(int quotaPret) {
        this.quotaPret = quotaPret;
    }

    public int getQuotaReservation() {
        return quotaReservation;
    }

    public void setQuotaReservation(int quotaReservation) {
        this.quotaReservation = quotaReservation;
    }
    public int getJourpenalite(){
        return jour_penalite;
    }
    public int getQuotaProlongement() {
        return quotaProlongement;
    }
    public int getJourPret() {
        return jourPret;
    }
}