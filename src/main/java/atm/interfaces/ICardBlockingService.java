package atm.interfaces;

import atm.model.CardData;

public interface ICardBlockingService {

    boolean isCardBlocked(String cardNumber);

    boolean isCardBlocked(CardData cardData);

    void blockCard(CardData cardData);

    void resetFailedAttemptsAndUnblock(CardData cardData);
}
