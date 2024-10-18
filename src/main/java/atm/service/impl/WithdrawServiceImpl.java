package atm.service.impl;

import atm.model.Account;
import atm.service.AccountService;
import atm.service.MiniStatementService;
import atm.service.WithdrawService;
import java.math.BigDecimal;
import static atm.util.Messages.*;

public class WithdrawServiceImpl implements WithdrawService {
    private static WithdrawServiceImpl instance;
    private final MiniStatementService miniStatementService = MiniStatementServiceImpl.getInstance();
    private final AccountService accountService = AccountServiceImpl.getInstance();

    private WithdrawServiceImpl() {}

    public static synchronized WithdrawServiceImpl getInstance() {
        if (instance == null) {
            instance = new WithdrawServiceImpl();
        }
        return instance;
    }

    @Override
    public void withdrawAmount(Account account, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println(WITHDRAWAL_MUST_BE_POSITIVE);
            return;
        }
        try {
            withdraw(account, amount);
            System.out.println(PLEASE_TAKE_CASH + amount);
            accountService.viewBalance(account);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void withdraw(Account account, BigDecimal amount) {
        if (amount.remainder(new BigDecimal("50")).compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalArgumentException(AMOUNT_MULTIPLE_OF_50);
        }
        if (amount.compareTo(account.getAmount()) > 0) {
            throw new IllegalArgumentException(INSUFFICIENT_FUNDS);
        }
        BigDecimal newBalance = account.getAmount().subtract(amount);
        account.setAmount(newBalance);
        miniStatementService.addEntry(WITHDREW + amount + ", " + NEW_BALANCE + newBalance);
        accountService.updateBalance(account, account.getCardData());
    }
}
