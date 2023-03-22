package transactions;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransactionManager {

	private Map<String, Region> regionMap = new HashMap<>();
	private Map<String, Carrier> carrierMap = new HashMap<>();
	private Map<String, Request> requestMap = new HashMap<>();
	private Map<String, Offer> offerMap = new HashMap<>();
	private Map<String, Transaction> transactionMap = new HashMap<>();
	private Map<String, Place> placeMap = new HashMap<>();
	
//R1
	public List<String> addRegion(String regionName, String... placeNames) {
		Region region = new Region(regionName);
		regionMap.put(regionName, region);
		Stream.of(placeNames).filter(pn -> !placeMap.containsKey(pn)).forEach(pn -> {
			Place place = new Place(pn, region);
			region.addPlace(place);
			placeMap.put(pn, place);
		});
		return region.getPlaces().stream().map(Place::getName).sorted().toList();
	}
	
	public List<String> addCarrier(String carrierName, String... regionNames) {
		Carrier carrier = new Carrier(carrierName);
		carrierMap.put(carrierName, carrier);
		Arrays.stream(regionNames).filter(rn -> regionMap.containsKey(rn)).forEach(rn -> carrier.addRegion(regionMap.get(rn)));
		return carrier.getRegions().stream().map(Region::getName).sorted().toList();
	}
	
	public List<String> getCarriersForRegion(String regionName) {
		return carrierMap.values().stream().filter(carrier -> carrier.getRegions().contains(regionMap.get(regionName)))
				.map(Carrier::getName).sorted().toList();
	}
	
//R2
	public void addRequest(String requestId, String placeName, String productId) throws TMException {
		if (requestMap.containsKey(requestId)) throw new TMException();
		if (!placeMap.containsKey(placeName)) throw new TMException();
		Request request = new Request(requestId, placeMap.get(placeName), productId);
		requestMap.put(requestId, request);
	}
	
	public void addOffer(String offerId, String placeName, String productId) throws TMException {
		if (offerMap.containsKey(offerId)) throw new TMException();
		if (!placeMap.containsKey(placeName)) throw new TMException();
		Offer offer = new Offer(offerId, placeMap.get(placeName), productId);
		offerMap.put(offerId, offer);
	}
	

//R3
	public void addTransaction(String transactionId, String carrierName, String requestId, String offerId) throws TMException {
		for (Transaction t : transactionMap.values()) {
			if (t.getRequest().getRequestId().equals(requestId) || t.getOffer().getOfferId().equals(offerId)) throw new TMException();
		}
		Request request = requestMap.get(requestId);
		Offer offer = offerMap.get(offerId);
		if (!request.getProductId().equals(offer.getProductId())) throw new TMException();
		Carrier carrier = carrierMap.get(carrierName);
		if (!carrier.getRegions().contains(offer.getPlace().getRegion()))throw new TMException();
		if (!carrier.getRegions().contains(request.getPlace().getRegion()))throw new TMException();

		Transaction transaction = new Transaction(transactionId, carrier, request, offer);
		transactionMap.put(transactionId, transaction);
	}
	
	public boolean evaluateTransaction(String transactionId, int score) {
		if (score < 1 || score > 10) return false;
		Transaction transaction = transactionMap.get(transactionId);
		transaction.setScore(score);
		return true;
	}
	
//R4
public SortedMap<Long, List<String>> deliveryRegionsPerNT() {
	Map<String, Long> map = transactionMap.values().stream()
			.collect(Collectors.groupingBy(t -> t.getRequest().getPlace().getRegion().getName(), Collectors.counting()));
	TreeMap<Long, List<String>> treeMap = new TreeMap<>(Comparator.reverseOrder());
	map.entrySet().stream().collect(Collectors.groupingBy(Map.Entry::getValue, () -> treeMap, Collectors.mapping(Map.Entry::getKey, Collectors.toList())));
	treeMap.forEach((key, value) -> Collections.sort(value));
	return treeMap;

}

	public SortedMap<String, Integer> scorePerCarrier(int minimumScore) {
		return transactionMap.values().stream().filter(transaction -> transaction.getScore() >= minimumScore)
				.collect(Collectors.groupingBy(transaction -> transaction.getCarrier().getName(), TreeMap::new, Collectors.summingInt(Transaction::getScore)));

	}
	
	public SortedMap<String, Long> nTPerProduct() {
		return transactionMap.values().stream()
				.collect(Collectors.groupingBy(transaction -> transaction.getOffer().getProductId(), TreeMap::new, Collectors.counting()));
	}
	
	
}

