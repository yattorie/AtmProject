package atm.interfaces;

public interface IUIService {

    void viewBalance();

    void viewMiniStatement();

    void handleWithdrawal();

    void handleDeposit();

    int getMenuOption();

    void displayMenu();
}
