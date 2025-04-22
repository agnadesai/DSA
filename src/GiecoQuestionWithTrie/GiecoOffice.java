package GiecoQuestionWithTrie;

public class GiecoOffice {
    String address;
    String state;

    public GiecoOffice(String address, String state) {
        this.address = address;
        this.state = state.toLowerCase(); // Normalize for case-insensitive search
    }

    public String getZipCode() {
        // Extract the last 5-digit number from the address
        String[] parts = address.split("\\s+");
        for (int i = parts.length - 1; i >= 0; i--) {
            if (parts[i].matches("\\d{5}")) {
                return parts[i];
            }
        }
        return ""; // No valid ZIP code found
    }

    @Override
    public String toString() {
        return address;
    }
}