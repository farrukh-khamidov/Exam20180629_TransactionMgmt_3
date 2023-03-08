package transactions;

public class Offer {
    private String offerId;
    private String placeName;
    private String productId;

    public Offer(String offerId, String placeName, String productId) {
        this.offerId = offerId;
        this.placeName = placeName;
        this.productId = productId;
    }

    public String getOfferId() {
        return offerId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getProductId() {
        return productId;
    }
}
