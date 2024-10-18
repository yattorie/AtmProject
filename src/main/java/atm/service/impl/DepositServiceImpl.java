package atm.service.impl;

import atm.model.Account;
import atm.service.AccountService;
import atm.service.DepositService;
import atm.service.MiniStatementService;
import java.math.BigDecimal;
import static atm.util.Messages.*;

public class DepositServiceImpl implements DepositService {
    private static DepositServiceImpl instance;
    private final MiniStatementService miniStatementService = MiniStatementServiceImpl.getInstance();
    private final AccountService accountService = AccountServiceImpl.getInstance();

    private DepositServiceImpl() {}

    public static synchronized DepositServiceImpl getInstance() {
        if (instance == null) {
            instance = new DepositServiceImpl();
        }
        return instance;
    }

    @Override
    public void depositAmount(Account account, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println(DEPOSIT_MUST_BE_POSITIVE);
            return;
        }
        try {
            deposit(account, amount);
            System.out.println(amount + SUCCESSFULLY_DEPOSITED);
            accountService.viewBalance(account);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deposit(Account account, BigDecimal amount) {
        if (amount.compareTo(new BigDecimal("1000000")) <= 0) {
            BigDecimal newBalance = account.getAmount().add(amount);
            account.setAmount(newBalance);
            miniStatementService.addEntry(DEPOSITED + amount + ", " + NEW_BALANCE + newBalance);
            accountService.updateBalance(account, account.getCardData());
        } else {
            throw new IllegalArgumentException(DEPOSIT_LIMIT);
        }
    }
}
