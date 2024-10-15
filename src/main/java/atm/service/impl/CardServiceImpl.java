package atm.service.impl;

import atm.data.DataManager;
import atm.model.CardData;
import atm.repository.CardRepository;
import atm.repository.impl.CardRepositoryImpl;
import atm.service.CardService;
import atm.util.AtmScanner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

import static atm.util.Messages.*;

public class CardServiceImpl implements CardService {
    private static CardServiceImpl instance;
    private final DataManager dataManager = DataManager.getInstance();
    private final CardRepository cardRepository = CardRepositoryImpl.getInstance();
    private static final int MAX_ATTEMPTS = 3;
    private static final int BLOCK_DURATION_HOURS = 24;
    private final AtmScanner atmScanner = AtmScanner.getInstance();

    private CardServiceImpl() {}

    public static synchronized CardServiceImpl getInstance() {
        if (instance == null) {
            instance = new CardServiceImpl();
        }
        return instance;
    }

    @Override
    public List<String[]> readDataFromFile() {
        return dataManager.readDataFromFile();
    }

    @Override
    public String getValidPinCode(String cardNumber, AtmScanner atmScanner) {
        String pinCode = "";
        boolean isValid = false;
        while (!isValid) {
            System.out.print(ENTER_PIN_CODE);
            pinCode = atmScanner.getScanner().next();
            if (pinCode.length() != 4) {
                System.out.println(PIN_4_DIGITS);
                continue;
            }
            isValid = validate(cardNumber, pinCode);
            if (isCardBlocked(cardNumber)) {
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
    public boolean cardExists(String cardNumber, List<String[]> dataList) {
        return cardRepository.cardExists(cardNumber, dataList);
    }

    private boolean validate(String cardNumber, String pinCode) {
        List<String[]> dataList = dataManager.readDataFromFile();
        for (String[] data : dataList) {
            if (data[0].equals(cardNumber)) {
                CardData cardData = CardRepositoryImpl.getInstance().createCardDataFromFileData(data);
                if (isCardBlocked(cardData)) {
                    System.out.println(TRY_AGAIN_LATER);
                    return false;
                }
                if (cardData.getPinCode().equals(pinCode)) {
                    resetFailedAttemptsAndUnblock(cardData);
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

    public boolean isCardBlocked(String cardNumber) {
        List<String[]> dataList = dataManager.readDataFromFile();
        for (String[] data : dataList) {
            if (data[0].equals(cardNumber)) {
                CardData cardData = CardRepositoryImpl.getInstance().createCardDataFromFileData(data);
                return isCardBlocked(cardData);
            }
        }
        return false;
    }

    private boolean isCardBlocked(CardData cardData) {
        LocalDateTime currentTime = LocalDateTime.now();
        return cardData.getFailedAttempts() >= 3 &&
                (cardData.getBlockTimestamp() == null || currentTime.isBefore(cardData.getBlockTimestamp()));
    }

    private void blockCard(CardData cardData) {
        cardData.setBlockTimestamp(LocalDateTime.now().plusHours(BLOCK_DURATION_HOURS));
        CardRepositoryImpl.getInstance().updateCardData(cardData);
        System.out.println(MAX_ATTEMPTS);
    }

    private void resetFailedAttemptsAndUnblock(CardData cardData) {
        cardData.setFailedAttempts(0);
        cardData.setBlockTimestamp(null);
        CardRepositoryImpl.getInstance().updateCardData(cardData);
    }

    private void handleFailedAttempt(CardData cardData) {
        cardData.setFailedAttempts(cardData.getFailedAttempts() + 1);
        if (cardData.getFailedAttempts() >= MAX_ATTEMPTS) {
            getInstance().blockCard(cardData);
        } else {
            CardRepositoryImpl.getInstance().updateCardData(cardData);
            System.out.println(ATTEMPTS_LEFT + (MAX_ATTEMPTS - cardData.getFailedAttempts()));
        }
    }
}
