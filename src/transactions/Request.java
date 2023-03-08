package transactions;

public class Request {
    private String requestId;
    private String placeName;
    private String productId;

    public Request(String requestId, String placeName, String productId) {
        this.requestId = requestId;
        this.placeName = placeName;
        this.productId = productId;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getProductId() {
        return productId;
    }
}
