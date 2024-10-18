package atm.service;

import atm.model.Account;

public interface UIService {

    void viewBalance(Account account);

    void viewMiniStatement();

    void handleWithdrawal(Account account);

    int getMenuOption();

    void handleDeposit(Account account);
}
