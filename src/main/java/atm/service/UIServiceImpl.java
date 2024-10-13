package atm.service;

import atm.interfaces.IUIService;

import java.math.BigDecimal;
import java.util.Scanner;
import static atm.util.Messages.*;

public class UIServiceImpl implements IUIService {
    private static UIServiceImpl instance;
    private final Scanner scanner = new Scanner(System.in);
    private AccountServiceImpl accountService = AccountServiceImpl.getInstance();
    private WithdrawServiceImpl withdrawService = WithdrawServiceImpl.getInstance();
    private DepositServiceImpl depositService = DepositServiceImpl.getInstance();

    private UIServiceImpl() {}

    public static synchronized UIServiceImpl getInstance() {
        if (instance == null) {
            instance = new UIServiceImpl();
        }
        return instance;
    }

    @Override
    public void viewBalance() {
        accountService.viewBalance();
    }

    @Override
    public void viewMiniStatement() {
        accountService.viewMiniStatement();
    }

    @Override
    public void handleWithdrawal() {
        System.out.print(ENTER_TO_WITHDRAW);
        if (scanner.hasNextBigDecimal()) {
            BigDecimal amountToWithdraw = scanner.nextBigDecimal();
            withdrawService.withdrawAmount(amountToWithdraw);
        } else {
            System.out.println(INVALID_INPUT_NUMBER_POSITIVE);
            scanner.next();
        }
    }

    @Override
    public void handleDeposit() {
        System.out.print(ENTER_TO_DEPOSIT);
        if (scanner.hasNextBigDecimal()) {
            BigDecimal amountToDeposit = scanner.nextBigDecimal();
            depositService.depositAmount(amountToDeposit);
        } else {
            System.out.println(INVALID_INPUT_NUMBER_POSITIVE);
            scanner.next();
        }
    }

    @Override
    public int getMenuOption() {
        displayMenu();
        while (!scanner.hasNextInt()) {
            System.out.println(INVALID_INPUT_NUMBER);
            scanner.next();
        }
        return scanner.nextInt();
    }

    @Override
    public void displayMenu() {
        System.out.println("1 - " + CHECK_CARD_BALANCE);
        System.out.println("2 - " + WITHDRAW_FUNDS);
        System.out.println("3 - " + DEPOSIT_FUNDS);
        System.out.println("4 - " + VIEW_MINI_STATEMENT);
        System.out.println("5 - " + EXIT);
        System.out.print(CHOOSE_AN_OPERATION);
    }
}
