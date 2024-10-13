package atm.interfaces;

import java.util.List;
import java.util.Scanner;

public interface ICardService {

    List<String[]> readDataFromFile();

    String getValidPinCode(String cardNumber, Scanner scanner);

    boolean isValidCardNumberFormat(String cardNumber);

    boolean cardExists(String cardNumber, List<String[]> dataList);
}
