package atm.service;

import atm.data.DataManager;
import atm.interfaces.ICardBlockingService;
import atm.model.CardData;
import atm.repository.CardRepositoryImpl;

import java.time.LocalDateTime;
import java.util.List;

import static atm.util.Messages.*;

public class CardBlockingServiceImpl implements ICardBlockingService {
    private static CardBlockingServiceImpl instance;
    private final DataManager dataManager = DataManager.getInstance();
    private static final int BLOCK_DURATION_HOURS = 24;

    private CardBlockingServiceImpl() {}

    public static synchronized CardBlockingServiceImpl getInstance() {
        if (instance == null) {
            instance = new CardBlockingServiceImpl();
        }
        return instance;
    }

    @Override
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

    @Override
    public boolean isCardBlocked(CardData cardData) {
        LocalDateTime currentTime = LocalDateTime.now();
        return cardData.getFailedAttempts() >= 3 &&
                (cardData.getBlockTimestamp() == null || currentTime.isBefore(cardData.getBlockTimestamp()));
    }

    @Override
    public void blockCard(CardData cardData) {
        cardData.setBlockTimestamp(LocalDateTime.now().plusHours(BLOCK_DURATION_HOURS));
        CardRepositoryImpl.getInstance().updateCardData(cardData);
        System.out.println(MAX_ATTEMPTS);
    }

    @Override
    public void resetFailedAttemptsAndUnblock(CardData cardData) {
        cardData.setFailedAttempts(0);
        cardData.setBlockTimestamp(null);
        CardRepositoryImpl.getInstance().updateCardData(cardData);
    }
}
