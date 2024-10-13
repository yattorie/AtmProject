package atm.service;

import atm.data.DataManager;
import atm.interfaces.ICardService;
import atm.repository.CardRepositoryImpl;

import java.util.List;
import java.util.Scanner;

public class CardServiceImpl implements ICardService {
    private static CardServiceImpl instance;
    private final DataManager dataManager = DataManager.getInstance();
    private final CardValidationServiceImpl cardValidationServiceImpl = CardValidationServiceImpl.getInstance();
    private final CardBlockingServiceImpl cardBlockingServiceImpl = CardBlockingServiceImpl.getInstance();
    private final CardRepositoryImpl cardRepositoryImpl = CardRepositoryImpl.getInstance();

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
    public String getValidPinCode(String cardNumber, Scanner scanner) {
        return cardValidationServiceImpl.getValidPinCode(cardNumber, scanner);
    }

    @Override
    public boolean isValidCardNumberFormat(String cardNumber) {
        return cardValidationServiceImpl.isValidCardNumberFormat(cardNumber);
    }

    @Override
    public boolean cardExists(String cardNumber, List<String[]> dataList) {
        return cardRepositoryImpl.cardExists(cardNumber, dataList);
    }
}
