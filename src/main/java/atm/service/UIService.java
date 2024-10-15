package atm.service;

public interface UIService {

    void viewBalance();

    void viewMiniStatement();

    void handleWithdrawal();

    void handleDeposit();

    int getMenuOption();
}
