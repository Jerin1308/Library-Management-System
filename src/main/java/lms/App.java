package lms;

import lms.lib.LibManager;
import lms.lib.LibManagers;

public class App {
    public static void main( String[] args ){
        LibManagers manager = new LibManager();
        manager.start();
    }
}
