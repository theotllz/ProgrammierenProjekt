package src.Devicemanagementprojekt;

public class Device {
    private String name;
    private boolean Verfuegbarkeit;
    //später ersetzen durch Variable von Personenklasse
    public int Ausleiher;
    public String Notizen;


    public Device(String name) {
        this.name = name;
        Verfuegbarkeit = true;
    }

    //Name Getter Setter
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //Verfügbarkeit Getter Setter
    public boolean getVerfuegbarkeit() {
        return Verfuegbarkeit;
    }

    public void Ausleihen(int Ausleiher) {
        this.Verfuegbarkeit = false;
        this.Ausleiher = Ausleiher;
    }
    public void Rückgabe(){
        this.Verfuegbarkeit = true;
    }



    public int getAusleiher() {
        return Ausleiher;
    }
    public void setAusleiher(int Ausleiher) {
        this.Ausleiher = Ausleiher;
    }
}
