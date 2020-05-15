package pl.javastart.library.app;

public class LibraryApp {

    private final static String APP_NAME="Bilbioteka v1.21";
    public static void main(String[] args) {

        System.out.println(APP_NAME);
        LibraryControl libControl = new LibraryControl();
        libControl.controlLoop();
    }  
}
 