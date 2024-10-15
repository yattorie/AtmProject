package atm.service.impl;

import atm.model.Account;
import atm.model.CardData;
import atm.repository.AccountRepository;
import atm.repository.impl.AccountRepositoryImpl;
import atm.service.AccountService;
import atm.service.MiniStatementService;

import static atm.util.Messages.*;

public class AccountServiceImpl implements AccountService {
    private static AccountServiceImpl instance;
    private Account account;
    private CardData cardData;
    private final AccountRepository accountRepository = AccountRepositoryImpl.getInstance();
    private MiniStatementService miniStatementService = MiniStatementServiceImpl.getInstance();

    private AccountServiceImpl() {}

    public static synchronized AccountServiceImpl getInstance() {
        if (instance == null) {
            instance = new AccountServiceImpl();
        }
        return instance;
    }

    @Override
    public void initializeAccount(String cardNumber, String pinCode) {
        account = accountRepository.findAccountByCardNumberAndPinCode(cardNumber, pinCode);
        if (account != null) {
            cardData = account.getCardData();
            WithdrawServiceImpl.getInstance().setAccount(account);
            DepositServiceImpl.getInstance().setAccount(account);
        } else {
            throw new IllegalArgumentException(INVALID_CARD_OR_PIN);
        }
    }

    @Override
    public void viewBalance() {
        System.out.println(AVAILABLE_BALANCE + account.getAmount());
    }

    @Override
    public void viewMiniStatement() {
        miniStatementService.viewMiniStatement();
    }

    @Override
    public void updateBalance() {
        accountRepository.updateBalance(cardData.getCardNumber(), cardData.getPinCode(), account.getAmount());
    }
}
