package com.esi.jwtauth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Patient extends UserDAO{

    @Column
    private String nom;
    @Column
    private String prenom;
    @Column
    private String dateNaissance;
}
