package src;
public class IO {
    public static void main(String[] args) {
        Gerät iphone = geräterstellung(284, "macbook");
        iphone.ausleihen();
        iphone.rueckgabe();
        iphone.ausleihen();
        iphone.rueckgabe();
        iphone.rueckgabe();
    }
    public static Gerät geräterstellung(int id, String Name){

        Gerät neuesobjekt = new Gerät(id, Name, true);
        return neuesobjekt;
    }
}




