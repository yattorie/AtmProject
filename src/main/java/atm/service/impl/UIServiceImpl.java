package atm.service.impl;

import atm.model.Account;
import atm.service.MiniStatementService;
import atm.service.UIService;
import atm.util.AtmScanner;
import java.math.BigDecimal;
import static atm.util.Messages.*;

public class UIServiceImpl implements UIService {
    private static UIServiceImpl instance;
    private final AtmScanner atmScanner = AtmScanner.getInstance();
    private final AccountServiceImpl accountService = AccountServiceImpl.getInstance();
    private final WithdrawServiceImpl withdrawService = WithdrawServiceImpl.getInstance();
    private final DepositServiceImpl depositService = DepositServiceImpl.getInstance();
    private final MiniStatementService miniStatementService = MiniStatementServiceImpl.getInstance();

    private UIServiceImpl() {}

    public static synchronized UIServiceImpl getInstance() {
        if (instance == null) {
            instance = new UIServiceImpl();
        }
        return instance;
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

    @Override
    public void viewBalance(Account account) {
        accountService.viewBalance(account);
    }

    @Override
    public void handleWithdrawal(Account account) {
        System.out.print(ENTER_TO_WITHDRAW);
        if (atmScanner.getScanner().hasNextBigDecimal()) {
            BigDecimal amountToWithdraw = atmScanner.getScanner().nextBigDecimal();
            withdrawService.withdrawAmount(account, amountToWithdraw);
        } else {
            System.out.println(INVALID_INPUT_NUMBER_POSITIVE);
            atmScanner.getScanner().next();
        }
    }

    @Override
    public void handleDeposit(Account account) {
        System.out.print(ENTER_TO_DEPOSIT);
        if (atmScanner.getScanner().hasNextBigDecimal()) {
            BigDecimal amountToDeposit = atmScanner.getScanner().nextBigDecimal();
            depositService.depositAmount(account, amountToDeposit);
        } else {
            System.out.println(INVALID_INPUT_NUMBER_POSITIVE);
            atmScanner.getScanner().next();
        }
    }

    @Override
    public void viewMiniStatement() {
        miniStatementService.viewMiniStatement();
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
