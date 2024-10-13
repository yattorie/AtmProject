package atm.model;

import java.time.LocalDateTime;

public class CardData {
    private String cardNumber;
    private String pinCode;
    private int failedAttempts;
    private LocalDateTime blockTimestamp;

    public CardData(String cardNumber, String pinCode) {
        this.cardNumber = cardNumber;
        this.pinCode = pinCode;
        this.failedAttempts = 0;
        this.blockTimestamp = null;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public LocalDateTime getBlockTimestamp() {
        return blockTimestamp;
    }

    public void setBlockTimestamp(LocalDateTime blockTimestamp) {
        this.blockTimestamp = blockTimestamp;
    }
}
