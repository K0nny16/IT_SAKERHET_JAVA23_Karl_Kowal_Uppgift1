package org.it_sakerhet_java23_karl_kowal_uppgift1.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Hashing {
    private final BCryptPasswordEncoder passwordEncoder;
    //En instans istället för flera. Att vi inte behöver instansiera den i metoderna.
    public Hashing(){
        //Hashas 10 ronder och lägger till sitt egna salt.
        //Skyddar mot Rainbow table attacker eftersom varje hash är unik tackvare saltet.
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    public String hashPassword(String password){
        return passwordEncoder.encode(password);
    }
    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        //Möjligt eftersom att matches metoden kan extracta saltet.
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}
