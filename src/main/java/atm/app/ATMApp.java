package atm.app;

import atm.interfaces.IAccountService;
import atm.interfaces.ICardService;
import atm.interfaces.IUIService;
import atm.service.*;

import java.util.List;
import java.util.Scanner;

import static atm.util.Messages.*;

public class ATMApp {
    private final ICardService ICardService = CardServiceImpl.getInstance();
    private final IUIService IUIService = UIServiceImpl.getInstance();
    private final IAccountService IAccountService = AccountServiceImpl.getInstance();
    private Scanner scanner = new Scanner(System.in);


    public void startATM() {
        System.out.println(WELCOME_MESSAGE);
        System.out.print(ENTER_CARD_NUMBER);
        String cardNumber = scanner.next();
        if (!ICardService.isValidCardNumberFormat(cardNumber)) {
            System.out.println(INVALID_CARD_NUMBER_FORMAT);
            return;
        }

        List<String[]> dataList;
        try {
            dataList = ICardService.readDataFromFile();
        } catch (RuntimeException e) {
            System.out.println(ERROR_READING_CARD_DATA + e.getMessage());
            return;
        }

        boolean cardExists = ICardService.cardExists(cardNumber, dataList);
        if (!cardExists) {
            System.out.println(CARD_NOT_FOUND);
            return;
        }

        String pinCode = ICardService.getValidPinCode(cardNumber, scanner);
        IAccountService.initializeAccount(cardNumber, pinCode);

        while (true) {
            int choice = IUIService.getMenuOption();
            switch (choice) {
                case 1:
                    IUIService.viewBalance();
                    break;
                case 2:
                    IUIService.handleWithdrawal();
                    break;
                case 3:
                    IUIService.handleDeposit();
                    break;
                case 4:
                    IUIService.viewMiniStatement();
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
