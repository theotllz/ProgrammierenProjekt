package src;

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


    public boolean getVerfuegbarkeit() {
        return Verfuegbarkeit;
    }
    public void setVerfuegbarkeit(boolean verfuegbarkeit, int Ausleiher) {
        this.Verfuegbarkeit = verfuegbarkeit;
        this.Ausleiher = Ausleiher;
    }

    public int getAusleiher() {
        return Ausleiher;
    }
}
