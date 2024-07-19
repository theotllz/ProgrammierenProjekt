package src.Devicemanagementprojekt;

import src.Device;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String name;
    private String email;
    private String password;
    private boolean Admin;

    public User(int id, String name, String email, String password, boolean Admin){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.Admin = Admin;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public boolean getAdmin() {
        return Admin;
    }

    public void changeAdmin() {
        if(Admin){
            Admin = false;
        }
        else{
            Admin = true;
        }
    }

    public String getName() {
        return name;
    }

    public String getPassword(){
        return password;
    }
}
