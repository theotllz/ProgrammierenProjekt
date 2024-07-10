package src.ideen;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Gerät {
    private int id;
    private String gerätename;
    private boolean Verfuegbarkeit;

    public Gerät(int id, String gerätename, boolean verfuegbarkeit) {
        this.id = id;
        this.gerätename = gerätename;
        Verfuegbarkeit = verfuegbarkeit;
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

    public boolean getVerfügbarkeit() {
        return Verfuegbarkeit;
    }


    //Aktionen

    public void ausleihen() {
    if(Verfuegbarkeit){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        System.out.println("src.ideen.Gerät zum Zeitpunkt " + formattedDateTime + " ausgeliehen\n");
        Verfuegbarkeit =false;
    }
    else {
        System.out.println("wenn du das sehen kannst gibt es einen fehler im programm");
        System.out.println("Gerät nicht verfügbar\n");
    }
    }

    public void rueckgabe() {
        if(!Verfuegbarkeit){
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime2 = now.format(formatter);
            System.out.println("Gerät um " + formattedDateTime2 + "Zurückgegeben\n");
            Verfuegbarkeit = true;
        }
        else {
            System.out.println("Dies sollte eigentlich nicht zu sehen sein");
            System.out.println("Rückgabe nicht möglich da bereits Verfügbar\n");
        }
        }



    }
