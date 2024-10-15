package atm.repository.impl;

import atm.data.DataManager;
import atm.model.CardData;
import atm.repository.CardRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CardRepositoryImpl implements CardRepository {
    private static CardRepositoryImpl instance;
    private final DataManager dataManager = DataManager.getInstance();
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private CardRepositoryImpl() {}

    public static synchronized CardRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new CardRepositoryImpl();
        }
        return instance;
    }

    @Override
    public CardData createCardDataFromFileData(String[] data) {
        CardData cardData = new CardData(data[0], data[1]);
        cardData.setFailedAttempts(Integer.parseInt(data[3]));
        cardData.setBlockTimestamp(parseDateTime(data[4]));
        return cardData;
    }

    @Override
    public void updateCardData(CardData cardData) {
        List<String[]> dataList = dataManager.readDataFromFile();
        for (String[] data : dataList) {
            if (data[0].equals(cardData.getCardNumber())) {
                data[3] = String.valueOf(cardData.getFailedAttempts());
                data[4] = cardData.getBlockTimestamp() != null ? cardData.getBlockTimestamp().format(DATE_TIME_FORMATTER) : "0";
                break;
            }
        }
        dataManager.writeDataToFile(dataList);
    }

    @Override
    public boolean cardExists(String cardNumber, List<String[]> dataList) {
        for (String[] data : dataList) {
            if (data[0].equals(cardNumber)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public LocalDateTime parseDateTime(String dateTimeString) {
        try {
            if (dateTimeString.equals("0")) {
                return null;
            }
            return LocalDateTime.parse(dateTimeString, DATE_TIME_FORMATTER);
        } catch (Exception e) {
            return null;
        }
    }
}
