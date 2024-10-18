package atm.service.impl;

import atm.model.Account;
import atm.model.CardData;
import atm.repository.AccountRepository;
import atm.repository.impl.AccountRepositoryImpl;
import atm.service.AccountService;
import static atm.util.Messages.*;

public class AccountServiceImpl implements AccountService {
    private static AccountServiceImpl instance;
    private final AccountRepository accountRepository = AccountRepositoryImpl.getInstance();

    private AccountServiceImpl() {}

    public static synchronized AccountServiceImpl getInstance() {
        if (instance == null) {
            instance = new AccountServiceImpl();
        }
        return instance;
    }

    @Override
    public Account initializeAccount(String cardNumber, String pinCode) {
        Account account = accountRepository.findAccountByCardNumberAndPinCode(cardNumber, pinCode);
        if (account == null) {
            throw new IllegalArgumentException(INVALID_CARD_OR_PIN);
        }
        return account;
    }

    @Override
    public void viewBalance(Account account) {
        System.out.println(AVAILABLE_BALANCE + account.getAmount());
    }

    @Override
    public void updateBalance(Account account, CardData cardData) {
        accountRepository.updateBalance(cardData.getCardNumber(), cardData.getPinCode(), account.getAmount());
    }
}
