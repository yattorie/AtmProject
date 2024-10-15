package atm.service;

import atm.model.Account;

import java.math.BigDecimal;

public interface DepositService {

    void depositAmount(BigDecimal amount);

    void setAccount(Account account);
}
