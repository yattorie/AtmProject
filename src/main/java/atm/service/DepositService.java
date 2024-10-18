package atm.service;

import atm.model.Account;

import java.math.BigDecimal;

public interface DepositService {

    void depositAmount(Account account, BigDecimal amount);
}
