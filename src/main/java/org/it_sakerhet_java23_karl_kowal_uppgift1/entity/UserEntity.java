package org.it_sakerhet_java23_karl_kowal_uppgift1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name ="users")
@Table(name ="users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//Representerar en tabell i databasen.
//Kan använda repo.
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "adress")
    private String adress;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;
}
