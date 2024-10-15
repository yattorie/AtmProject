package atm.service;

public interface AccountService {

    void initializeAccount(String cardNumber, String pinCode);

    void viewBalance();

    void viewMiniStatement();

    void updateBalance();
}
