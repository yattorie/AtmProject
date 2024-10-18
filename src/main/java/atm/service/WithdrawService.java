package atm.service;

import atm.model.Account;

import java.math.BigDecimal;

public interface WithdrawService {

    void withdrawAmount(Account account, BigDecimal amount);
}
