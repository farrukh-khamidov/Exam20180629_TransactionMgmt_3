package transactions;

public class Offer {
    private String offerId;
    private Place place;
    private String productId;

    public Offer(String offerId, Place place, String productId) {
        this.offerId = offerId;
        this.place = place;
        this.productId = productId;
    }

    public String getOfferId() {
        return offerId;
    }

    public Place getPlace() {
        return place;
    }

    public String getProductId() {
        return productId;
    }
}
