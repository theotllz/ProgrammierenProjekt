package src.Devicemanagementprojekt;

import java.io.Serializable;

public class Device implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private boolean Verfuegbarkeit;
    // später ersetzen durch Variable von Personenklasse
    public int Ausleiher;
    public String Notizen;
    public String neuPreis;

    public Device(String name) {
        this.name = name;
        this.Verfuegbarkeit = true;
        this.Notizen = "/";
        this.neuPreis = "/";
    }

    public String getName() {
        return name;
    }

    public boolean getVerfuegbarkeit() {
        return Verfuegbarkeit;
    }

    public void Ausleihen(int Ausleiher) {
        this.Verfuegbarkeit = false;
        this.Ausleiher = Ausleiher;
    }

    public void Rückgabe() {
        this.Verfuegbarkeit = true;
    }

    public int getAusleiher() {
        return Ausleiher;
    }

    public void setAusleiher(int Ausleiher) {
        this.Ausleiher = Ausleiher;
    }

    public String getNotizen() {
        return Notizen;
    }

    public void setNotizen(String Notizen) {
        this.Notizen = Notizen;
    }

    public String getNeuPreis() {
        return neuPreis;
    }

    public void setNeuPreis(String neuPreis) {
        this.neuPreis = neuPreis;
    }
}