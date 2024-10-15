package atm.util;

import java.util.Scanner;

public class AtmScanner {
    private static AtmScanner instance;
    private Scanner scanner;

    private AtmScanner() {
        scanner = new Scanner(System.in);
    }

    public static synchronized AtmScanner getInstance() {
        if (instance == null) {
            instance = new AtmScanner();
        }
        return instance;
    }

    public Scanner getScanner() {
        return scanner;
    }
}
