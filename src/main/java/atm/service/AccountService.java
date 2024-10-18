package atm.service;

import atm.model.Account;
import atm.model.CardData;

public interface AccountService {

    Account initializeAccount(String cardNumber, String pinCode);

    void viewBalance(Account account);

    void updateBalance(Account account, CardData cardData);
}
