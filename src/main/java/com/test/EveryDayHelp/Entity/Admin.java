package com.test.EveryDayHelp.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.web.bind.annotation.RequestMapping;

@Entity
public class Admin {

    @Id
    private String email;
    private String password;

    public Admin() {

    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}