package org.it_sakerhet_java23_karl_kowal_uppgift1.service;

import jakarta.servlet.http.HttpSession;
import org.it_sakerhet_java23_karl_kowal_uppgift1.dto.UserDTO;
import org.it_sakerhet_java23_karl_kowal_uppgift1.entity.UserEntity;
import org.it_sakerhet_java23_karl_kowal_uppgift1.repo.UserRepository;
import org.it_sakerhet_java23_karl_kowal_uppgift1.security.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final Hashing hashing;

    public UserService() {
        this.hashing = new Hashing();
    }

    //Kollar ifall en användare med emailen redan finns annars regar en ny användare.
    public void registerUser(UserEntity user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Användaren finns redan!");
        }
        String hashedPassword = hashing.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }
    /*Jämför hasherna och gör om entityn till en dto och sätter den i session.
    Vi vill inte ha entityn i session eftersom det kan finns känslig info från databasen i den.
    Detta gör också att det blir lättare att kontrollera vad som finns data mässigt i sessionen.
    Hade entityn varit större haft en many to many relation eller likande så hade objektet påverkat prestandan negativt.
    */
    public boolean loginUser(String email, String password, HttpSession session) {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null || !hashing.verifyPassword(password, user.getPassword())) {
            return false;
        }

        UserDTO userDTO = new UserDTO((long) user.getId(), user.getEmail(),user.getAdress(),user.getName());
        session.setAttribute("user", userDTO);
        return true;
    }
    //Existerar mest för att jag inte ska behöva skapa en instans av repot i alla klasser som behöver denna funktionen.
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void updateUser(UserEntity updatedUser) {
        UserEntity existingUser = userRepository.findByEmail(updatedUser.getEmail());
        if (existingUser != null) {
            existingUser.setName(updatedUser.getName());
            existingUser.setAdress(updatedUser.getAdress());
            userRepository.save(existingUser);
        }
    }
    public void deleteUserByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if (user != null) {
            userRepository.delete(user);
        }
    }
}