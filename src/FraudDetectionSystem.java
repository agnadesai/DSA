import java.util.*;

public class FraudDetectionSystem {

    private Set<String> flaggedPhones = new HashSet<>();
    private Set<String> flaggedEmails = new HashSet<>();
    private Set<String> flaggedAddresses = new HashSet<>();
    private Set<String> flaggedSSNs = new HashSet<>();

    public Boolean handleEvent(Map<String, Object> event) {
        String eventType = (String) event.get("event_type");
        Map<String, Object> customerDetails = (Map<String, Object>) event.get("customer_details");

        if ("fraud_flag".equals(eventType)) {
            addFlaggedDetails(customerDetails);
            return null;
        } else if ("underwriting".equals(eventType)) {
            boolean isFraud = isSuspicious(customerDetails);
            if (isFraud) {
                addFlaggedDetails(customerDetails);
            }
            return isFraud;
        }

        return null;
    }

    private void addFlaggedDetails(Map<String, Object> customerDetails) {
        if (customerDetails.containsKey("phone")) {
            flaggedPhones.add((String) customerDetails.get("phone"));
        }
        if (customerDetails.containsKey("email")) {
            flaggedEmails.add((String) customerDetails.get("email"));
        }
        if (customerDetails.containsKey("address")) {
            flaggedAddresses.add((String) customerDetails.get("address"));
        }
        if (customerDetails.containsKey("ssn")) {
            flaggedSSNs.add((String) customerDetails.get("ssn"));
        }
    }

    private boolean isSuspicious(Map<String, Object> customerDetails) {
        if (customerDetails.containsKey("phone") && flaggedPhones.contains(customerDetails.get("phone"))) {
            return true;
        }
        if (customerDetails.containsKey("email") && flaggedEmails.contains(customerDetails.get("email"))) {
            return true;
        }
        if (customerDetails.containsKey("address") && flaggedAddresses.contains(customerDetails.get("address"))) {
            return true;
        }
        if (customerDetails.containsKey("ssn") && flaggedSSNs.contains(customerDetails.get("ssn"))) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        List<Map<String, Object>> events = new ArrayList<>();

        events.add(Map.of(
                "event_type", "underwriting",
                "loan_amount", 3000,
                "customer_details", Map.of(
                        "address", "123 Main St",
                        "phone", "182-920-4124",
                        "email", "johndoe@gmail.com",
                        "ssn", "4568698929"
                )
        ));

        events.add(Map.of(
                "event_type", "fraud_flag",
                "customer_details", Map.of(
                        "address", "123 Main St",
                        "phone", "182-920-4124",
                        "email", "johndoe@gmail.com",
                        "ssn", "4568698929"
                )
        ));

        events.add(Map.of(
                "event_type", "underwriting",
                "loan_amount", 3000,
                "customer_details", Map.of(
                        "address", "123 Main St",
                        "phone", "947-213-9402",
                        "email", "janedoe@yahoo.com",
                        "ssn", "4568698929"
                )
        ));

        events.add(Map.of(
                "event_type", "underwriting",
                "loan_amount", 9000,
                "customer_details", Map.of(
                        "address", "654 5th Ave",
                        "phone", "947-213-9402",
                        "email", "jamesdoe@hotmail.com",
                        "ssn", "938103583"
                )
        ));

        FraudDetectionSystem detector = new FraudDetectionSystem();
        for (Map<String, Object> event : events) {
            Boolean result = detector.handleEvent(event);
            if (result == null) {
                System.out.println();
            } else {
                System.out.println(result ? "1" : "0");
            }
        }
    }
}
