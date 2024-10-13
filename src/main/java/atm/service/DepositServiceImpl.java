package atm.service;

import atm.interfaces.IAccountService;
import atm.interfaces.IDepositService;
import atm.model.Account;

import java.math.BigDecimal;

import static atm.util.Messages.*;

public class DepositServiceImpl implements IDepositService {
    private static DepositServiceImpl instance;
    private MiniStatementServiceImpl miniStatementService = MiniStatementServiceImpl.getInstance();
    private IAccountService IAccountService = AccountServiceImpl.getInstance();
    private Account account;

    private DepositServiceImpl() {
    }

    public static synchronized DepositServiceImpl getInstance() {
        if (instance == null) {
            instance = new DepositServiceImpl();
        }
        return instance;
    }

    @Override
    public void depositAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println(DEPOSIT_MUST_BE_POSITIVE);
            return;
        }
        try {
            deposit(amount);
            System.out.println(amount + SUCCESSFULLY_DEPOSITED);
            IAccountService.viewBalance();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deposit(BigDecimal amount) {
        if (amount.compareTo(new BigDecimal("1000000")) <= 0) {
            BigDecimal newBalance = account.getAmount().add(amount);
            account.setAmount(newBalance);
            miniStatementService.addEntry(DEPOSITED + amount + ", "+ NEW_BALANCE + newBalance);
            updateBalance();
        } else {
            throw new IllegalArgumentException(DEPOSIT_LIMIT);
        }
    }

    @Override
    public void updateBalance() {
        IAccountService.updateBalance();
    }

    @Override
    public void setAccount(Account account) {
        this.account = account;
    }

}
