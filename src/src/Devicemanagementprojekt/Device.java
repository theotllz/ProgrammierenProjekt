package src.Devicemanagementprojekt;

import java.io.Serializable;
import java.time.LocalDate;

public class Device implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private boolean Verfuegbarkeit;
    public String Ausleiher;
    public String Notizen;
    public String neuPreis;
    public int AusleihDauer;
    public LocalDate AusleihTag;
    public LocalDate RückgabeTag;

    public Device(String name) {
        this.name = name;
        this.Verfuegbarkeit = true;
        this.Notizen = "/";
        this.neuPreis = "/";
        this.AusleihDauer = 0;
        this.AusleihTag = LocalDate.parse("0001-01-01");
        this.RückgabeTag = LocalDate.parse("0001-01-01");
    }

    public String getName() {
        return name;
    }

    public boolean getVerfuegbarkeit() {
        return Verfuegbarkeit;
    }

    public void Ausleihen(String Ausleiher) {
        this.Verfuegbarkeit = false;
        this.Ausleiher = Ausleiher;
    }

    public void Rückgabe() {
        this.Verfuegbarkeit = true;
        setAusleiher("12437");
    }

    public String getAusleiher() {
        return Ausleiher;
    }

    public void setAusleiher(String Ausleiher) {
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
    public void setAusleihTag(LocalDate date) {this.AusleihTag = date;}

    public LocalDate getAusleihTag(){return AusleihTag;}

    public void setAusleihDauer(int Dauer){this.AusleihDauer = Dauer;}

    public int getAusleihDauer(){return AusleihDauer;}

    public LocalDate getRückgabeTag() {return RückgabeTag;}

    public void setRückgabeTag(LocalDate date){this.RückgabeTag = date;}

}