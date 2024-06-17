package src;

public interface Gerät {
    public int getID();
    public String getName();
    public boolean getVerfügbarkeit();
    public void ausleihen();
    public void rückgabe();


}
