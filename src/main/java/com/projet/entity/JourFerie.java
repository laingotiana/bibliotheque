package com.projet.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "jour_ferie")
public class JourFerie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date", nullable = false, unique = true)
    @Temporal(TemporalType.DATE)
    private Date date;

    // Constructeurs
    public JourFerie() {}

    public JourFerie(Date date) {
        this.date = date;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}