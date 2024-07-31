package src.Devicemanagementprojekt;

import src.Device;

import java.io.Serializable;

public class User implements Serializable {

    private String username;
    private String name;
    private String email;
    private String password;
    private boolean Admin;

    public User(String username, String name, String email, String password, boolean Admin){
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.Admin = Admin;
    }

    public String getUsername() {
        return username;
    }

    public boolean getAdmin() {
        return Admin;
    }

    public String getName() {
        return name;
    }

    public String getPassword(){
        return password;
    }
}
