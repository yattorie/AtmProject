package atm.api;

import atm.service.AccountService;
import atm.service.CardService;
import atm.service.UIService;
import atm.service.impl.AccountServiceImpl;
import atm.service.impl.CardServiceImpl;
import atm.service.impl.UIServiceImpl;

import java.util.List;
import atm.util.AtmScanner;


import static atm.util.Messages.*;

public class ATMApp {
    private final CardService cardService = CardServiceImpl.getInstance();
    private final UIService uiService = UIServiceImpl.getInstance();
    private final AccountService accountService = AccountServiceImpl.getInstance();
    private final AtmScanner atmScanner = AtmScanner.getInstance();

    public void startATM() {
        System.out.println(WELCOME_MESSAGE);
        System.out.print(ENTER_CARD_NUMBER);
        String cardNumber = atmScanner.getScanner().next();
        if (!cardService.isValidCardNumberFormat(cardNumber)) {
            System.out.println(INVALID_CARD_NUMBER_FORMAT);
            return;
        }

        List<String[]> dataList;
        try {
            dataList = cardService.readDataFromFile();
        } catch (RuntimeException e) {
            System.out.println(ERROR_READING_CARD_DATA + e.getMessage());
            return;
        }

        boolean cardExists = cardService.cardExists(cardNumber, dataList);
        if (!cardExists) {
            System.out.println(CARD_NOT_FOUND);
            return;
        }

        String pinCode = cardService.getValidPinCode(cardNumber);
        accountService.initializeAccount(cardNumber, pinCode);

        while (true) {
            int choice = uiService.getMenuOption();
            switch (choice) {
                case 1:
                    uiService.viewBalance();
                    break;
                case 2:
                    uiService.handleWithdrawal();
                    break;
                case 3:
                    uiService.handleDeposit();
                    break;
                case 4:
                    uiService.viewMiniStatement();
                    break;
                case 5:
                    System.out.println(PICK_UP_CARD);
                    System.out.println(EXITING_THE_SYSTEM);
                    System.out.println(THANK_YOU);
                    return;
                default:
                    System.out.println(INVALID_OPERATION_CHOICE);
            }
        }
    }
}
