package atm.service;

import atm.interfaces.IAccountService;
import atm.model.Account;
import atm.model.CardData;
import atm.repository.AccountRepository;
import atm.repository.AccountRepositoryImpl;

import static atm.util.Messages.*;

public class AccountServiceImpl implements IAccountService {
    private static AccountServiceImpl instance;
    private Account account;
    private CardData cardData;
    private final AccountRepository accountRepository = AccountRepositoryImpl.getInstance();
    private MiniStatementServiceImpl miniStatementService = MiniStatementServiceImpl.getInstance();

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
