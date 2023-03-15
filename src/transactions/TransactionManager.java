package transactions;
import java.util.*;

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
		for (String placeName : placeNames) {
			if (!placeMap.containsKey(placeName)) {
				region.addPlace(placeName);
				Place place = new Place(placeName, region);
				placeMap.put(placeName, place);
			}
		}
		return new LinkedList<>(region.getPlaces());
	}
	
	public List<String> addCarrier(String carrierName, String... regionNames) {
		Carrier carrier = new Carrier(carrierName);
		carrierMap.put(carrierName, carrier);
		for (String regionName : regionNames) {
			if (regionMap.containsKey(regionName))
				carrier.addRegion(regionMap.get(regionName));
		}
		List<String> carrierRegions = new LinkedList<>();
		for (Region region : carrier.getRegions()) {
			carrierRegions.add(region.getName());
		}
		return carrierRegions;
	}
	
	public List<String> getCarriersForRegion(String regionName) {
		Region region = regionMap.get(regionName);
		Set<String> carriersForRegion = new TreeSet<>();
		for (Carrier carrier : carrierMap.values()) {
			if (carrier.getRegions().contains(region)) {
				carriersForRegion.add(carrier.getName());
			}
		}
		return new ArrayList<>(carriersForRegion);
	}
	
//R2
	public void addRequest(String requestId, String placeName, String productId) throws TMException {
		if (requestMap.containsKey(requestId)) throw new TMException();
		if (!placeMap.containsKey(placeName)) throw new TMException();
		Request request = new Request(requestId, placeName, productId);
		requestMap.put(requestId, request);
	}
	
	public void addOffer(String offerId, String placeName, String productId) throws TMException {
		if (offerMap.containsKey(offerId)) throw new TMException();
		if (!placeMap.containsKey(placeName)) throw new TMException();
		Offer offer = new Offer(offerId, placeName, productId);
		offerMap.put(offerId, offer);
	}
	

//R3
	public void addTransaction(String transactionId, String carrierName, String requestId, String offerId) throws TMException {
		for (Transaction t : transactionMap.values()) {
			if (t.getRequestId().equals(requestId) || t.getOfferId().equals(offerId)) throw new TMException();
		}
		Request request = requestMap.get(requestId);
		Offer offer = offerMap.get(offerId);
		if (!request.getProductId().equals(offer.getProductId())) throw new TMException();
		Carrier carrier = carrierMap.get(carrierName);
		if (!carrier.getRegions().contains(placeMap.get(offer.getPlaceName()).getRegion()))throw new TMException();
		if (!carrier.getRegions().contains(placeMap.get(request.getPlaceName()).getRegion()))throw new TMException();

		Transaction transaction = new Transaction(transactionId, carrierName, requestId, offerId);
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

		Map<String, Long> map = new HashMap<>();
		for (Transaction t : transactionMap.values()) {
			Request request = requestMap.get(t.getRequestId());
			Place place = placeMap.get(request.getPlaceName());
			String rn = place.getRegion().getName();
			if (!map.containsKey(rn)) {
				map.put(rn, 1L);
			} else {
				map.put(rn, map.get(rn) + 1);
			}
		}

		SortedMap<Long, List<String>> result = new TreeMap<>(Comparator.reverseOrder());
		for (Map.Entry<String, Long> entry : map.entrySet()) {
			if (!result.containsKey(entry.getValue())) {
				List<String> list = new ArrayList<>();
				list.add(entry.getKey());
				result.put(entry.getValue(), list);
			} else {
				result.get(entry.getValue()).add(entry.getKey());
				Collections.sort(result.get(entry.getValue()));
			}
		}


		return result;
	}
	
	public SortedMap<String, Integer> scorePerCarrier(int minimumScore) {
		SortedMap<String, Integer> map = new TreeMap<>();
		for (Transaction transaction : transactionMap.values()) {
			if (transaction.getScore() >= minimumScore) {
				String cn = transaction.getCarrierName();
				if (!map.containsKey(cn)) {
					map.put(cn, transaction.getScore());
				} else {
					map.put(cn, map.get(cn) + transaction.getScore());
				}
			}
		}
		return map;
	}
	
	public SortedMap<String, Long> nTPerProduct() {
		SortedMap<String, Long> result = new TreeMap<>();
		for (Transaction t : transactionMap.values()) {
			Offer offer = offerMap.get(t.getOfferId());
			if (!result.containsKey(offer.getProductId())) {
				result.put(offer.getProductId(), 1L);
			} else {
				result.put(offer.getProductId(), result.get(offer.getProductId()) + 1);
			}
		}
		return result;
	}
	
	
}

