package org.it_sakerhet_java23_karl_kowal_uppgift1.dto;

import lombok.Getter;
import lombok.Setter;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
//Dto som används för att sätta användaren i session eftersom vi använder oss av Http session istället för Spring security.
public class UserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String email;
    private String name;
    private String adress;

    public UserDTO(Long id,String email,String adress,String name){
        this.id = id;
        this.email = email;
        this.adress = adress;
        this.name = name;
    }
}
