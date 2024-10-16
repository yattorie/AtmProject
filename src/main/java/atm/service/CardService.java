package atm.service;

import java.util.List;

public interface CardService {

    List<String[]> readDataFromFile();

    String getValidPinCode(String cardNumber);

    boolean isValidCardNumberFormat(String cardNumber);

    boolean cardExists(String cardNumber, List<String[]> dataList);

}
