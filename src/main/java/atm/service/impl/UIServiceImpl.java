package atm.service.impl;

import atm.service.AccountService;
import atm.service.DepositService;
import atm.service.UIService;
import atm.service.WithdrawService;
import atm.util.AtmScanner;

import java.math.BigDecimal;
import static atm.util.Messages.*;

public class UIServiceImpl implements UIService {
    private static UIServiceImpl instance;
    private final AtmScanner atmScanner = AtmScanner.getInstance();
    private AccountService accountService = AccountServiceImpl.getInstance();
    private WithdrawService withdrawService = WithdrawServiceImpl.getInstance();
    private DepositService depositService = DepositServiceImpl.getInstance();

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
        if (atmScanner.getScanner().hasNextBigDecimal()) {
            BigDecimal amountToWithdraw = atmScanner.getScanner().nextBigDecimal();
            withdrawService.withdrawAmount(amountToWithdraw);
        } else {
            System.out.println(INVALID_INPUT_NUMBER_POSITIVE);
            atmScanner.getScanner().next();
        }
    }

    @Override
    public void handleDeposit() {
        System.out.print(ENTER_TO_DEPOSIT);
        if (atmScanner.getScanner().hasNextBigDecimal()) {
            BigDecimal amountToDeposit = atmScanner.getScanner().nextBigDecimal();
            depositService.depositAmount(amountToDeposit);
        } else {
            System.out.println(INVALID_INPUT_NUMBER_POSITIVE);
            atmScanner.getScanner().next();
        }
    }

    @Override
    public int getMenuOption() {
        displayMenu();
        while (!atmScanner.getScanner().hasNextInt()) {
            System.out.println(INVALID_INPUT_NUMBER);
            atmScanner.getScanner().next();
        }
        return atmScanner.getScanner().nextInt();
    }

    private void displayMenu() {
        System.out.println("1 - " + CHECK_CARD_BALANCE);
        System.out.println("2 - " + WITHDRAW_FUNDS);
        System.out.println("3 - " + DEPOSIT_FUNDS);
        System.out.println("4 - " + VIEW_MINI_STATEMENT);
        System.out.println("5 - " + EXIT);
        System.out.print(CHOOSE_AN_OPERATION);
    }
}
