package atm.service;

import atm.util.AtmScanner;

import java.util.List;

public interface CardService {

    List<String[]> readDataFromFile();

    String getValidPinCode(String cardNumber, AtmScanner atmScanner);

    boolean isValidCardNumberFormat(String cardNumber);

    boolean cardExists(String cardNumber, List<String[]> dataList);

}
