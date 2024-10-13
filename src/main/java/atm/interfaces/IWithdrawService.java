package atm.interfaces;

import atm.model.Account;

import java.math.BigDecimal;

public interface IWithdrawService {

    void withdrawAmount(BigDecimal amount);

    void withdraw(BigDecimal amount);

    void updateBalance();

    void setAccount(Account account);
}
