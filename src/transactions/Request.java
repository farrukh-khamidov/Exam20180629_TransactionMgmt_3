package transactions;

public class Request {
    private String requestId;
    private Place place;
    private String productId;

    public Request(String requestId, Place place, String productId) {
        this.requestId = requestId;
        this.place = place;
        this.productId = productId;
    }

    public String getRequestId() {
        return requestId;
    }

    public Place getPlace() {
        return place;
    }

    public String getProductId() {
        return productId;
    }
}
