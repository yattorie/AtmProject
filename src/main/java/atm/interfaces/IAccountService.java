package atm.interfaces;

public interface IAccountService {

    void initializeAccount(String cardNumber, String pinCode);

    void viewBalance();

    void viewMiniStatement();

    void updateBalance();
}
