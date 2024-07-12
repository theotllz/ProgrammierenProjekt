package src.Devicemanagementprojekt;

import src.Device;

public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private boolean Admin;

    public User(int id, String name, String email, String password, boolean Admin) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.Admin = Admin;
    }

    public void ausleihen(Device device){
        device.setVerfuegbarkeit(false, id);
    }
    public void zurückgeben(Device device){
        device.setVerfuegbarkeit(true, 12437);
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
}
