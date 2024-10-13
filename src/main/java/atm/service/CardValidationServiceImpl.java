package atm.service;

import atm.data.DataManager;
import atm.interfaces.ICardValidationService;
import atm.model.CardData;
import atm.repository.CardRepositoryImpl;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import static atm.util.Messages.*;

public class CardValidationServiceImpl implements ICardValidationService {
    private static CardValidationServiceImpl instance;
    private final DataManager dataManager = DataManager.getInstance();
    private final CardBlockingServiceImpl cardBlockingServiceImpl = CardBlockingServiceImpl.getInstance();
    private static final int MAX_ATTEMPTS = 3;

    private CardValidationServiceImpl() {}

    public static synchronized CardValidationServiceImpl getInstance() {
        if (instance == null) {
            instance = new CardValidationServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean validate(String cardNumber, String pinCode) {
        List<String[]> dataList = dataManager.readDataFromFile();
        for (String[] data : dataList) {
            if (data[0].equals(cardNumber)) {
                CardData cardData = CardRepositoryImpl.getInstance().createCardDataFromFileData(data);
                if (cardBlockingServiceImpl.isCardBlocked(cardData)) {
                    System.out.println(TRY_AGAIN_LATER);
                    return false;
                }
                if (cardData.getPinCode().equals(pinCode)) {
                    cardBlockingServiceImpl.resetFailedAttemptsAndUnblock(cardData);
                    return true;
                } else {
                    handleFailedAttempt(cardData);
                    if (cardData.getFailedAttempts() >= MAX_ATTEMPTS) {
                        return false;
                    }
                    return false;
                }
            }
        }
        System.out.println(CARD_NOT_FOUND);
        return false;
    }

    @Override
    public String getValidPinCode(String cardNumber, Scanner scanner) {
        String pinCode = "";
        boolean isValid = false;
        while (!isValid) {
            System.out.println(ENTER_PIN_CODE);
            pinCode = scanner.next();
            if (pinCode.length() != 4) {
                System.out.println(PIN_4_DIGITS);
                continue;
            }
            isValid = validate(cardNumber, pinCode);
            if (cardBlockingServiceImpl.isCardBlocked(cardNumber)) {
                System.out.println(CARD_BLOCKED);
                System.out.println(PICK_UP_CARD);
                System.out.println(THANK_YOU);
                System.exit(0);
            }
            if (!isValid) {
                System.out.println(INVALID_PIN_CODE);
            }
        }
        return pinCode;
    }

    @Override
    public boolean isValidCardNumberFormat(String cardNumber) {
        Pattern cardNumberPattern = Pattern.compile("\\d{4}-\\d{4}-\\d{4}-\\d{4}");
        return cardNumberPattern.matcher(cardNumber).matches();
    }

    @Override
    public void handleFailedAttempt(CardData cardData) {
        cardData.setFailedAttempts(cardData.getFailedAttempts() + 1);
        if (cardData.getFailedAttempts() >= MAX_ATTEMPTS) {
            CardBlockingServiceImpl.getInstance().blockCard(cardData);
        } else {
            CardRepositoryImpl.getInstance().updateCardData(cardData);
            System.out.println(ATTEMPTS_LEFT + (MAX_ATTEMPTS - cardData.getFailedAttempts()));
        }
    }
}
