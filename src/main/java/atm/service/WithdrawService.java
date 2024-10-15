package atm.service;

import atm.model.Account;

import java.math.BigDecimal;

public interface WithdrawService {

    void withdrawAmount(BigDecimal amount);

    void setAccount(Account account);
}
