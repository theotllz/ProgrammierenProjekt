package src;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Geräte1 implements Gerät{
    private int id;
    private String gerätename;
    private boolean Verfügbarkeit;

    public Geräte1(int id, String gerätename, boolean verfügbarkeit) {
        this.id = id;
        this.gerätename = gerätename;
        Verfügbarkeit = verfügbarkeit;
    }

    //ID
    public int getID() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    //Name
    public String getName() {
        return gerätename;
    }
    public void setName(String name) {
        this.gerätename = name;
    }

    //Verfügbarkeit

    @Override
    public boolean getVerfügbarkeit() {
        return false;
    }

    @Override
    public void ausleihen() {




        LocalDateTime now = LocalDateTime.now();
        // Define the format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        this.Verfügbarkeit=false;
        System.out.println("src.Gerät zum Zeitpunkt " + formattedDateTime + " ausgeliehen");
    }
    public void rückgabe(){

    }
}
