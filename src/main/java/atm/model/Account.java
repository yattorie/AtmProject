package atm.model;

import java.math.BigDecimal;

public class Account {
    private BigDecimal amount;
    private final CardData cardData;

    public Account(CardData cardData) {
        this.cardData = cardData;
    }

    public CardData getCardData() {
        return cardData;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}