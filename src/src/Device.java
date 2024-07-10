package src;

public class Device {
    private String name;
    private boolean Verfuegbarkeit;
    //später ersetzen durch Variable von Personenklasse
    public String Ausleiher;


    public Device(String name) {
        this.name = name;
    }

    //Name Getter Setter
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public boolean isVerfuegbarkeit() {
        return Verfuegbarkeit;
    }
    public void setVerfuegbarkeit(boolean verfuegbarkeit, String Ausleiher) {
        this.Verfuegbarkeit = verfuegbarkeit;
        this.Ausleiher = Ausleiher;
    }


}
