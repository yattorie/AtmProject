package atm.interfaces;

import atm.model.Account;

import java.math.BigDecimal;

public interface IDepositService {

    void depositAmount(BigDecimal amount);

    void deposit(BigDecimal amount);

    void updateBalance();

    void setAccount(Account account);
}
