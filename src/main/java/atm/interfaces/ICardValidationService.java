package atm.interfaces;

import atm.model.CardData;

import java.util.Scanner;

public interface ICardValidationService {

    boolean validate(String cardNumber, String pinCode);

    String getValidPinCode(String cardNumber, Scanner scanner);

    boolean isValidCardNumberFormat(String cardNumber);

    void handleFailedAttempt(CardData cardData);
}
