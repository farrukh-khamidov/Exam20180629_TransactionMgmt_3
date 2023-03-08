package transactions;

public class Transaction {
    private String transactionId;
    private String carrierName;
    private String requestId;
    private String offerId;
    private int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Transaction(String transactionId, String carrierName, String requestId, String offerId) {
        this.transactionId = transactionId;
        this.carrierName = carrierName;
        this.requestId = requestId;
        this.offerId = offerId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getOfferId() {
        return offerId;
    }
}
