package GiecoQuestionWithTrie;

public class Main {
    public static void main(String[] args) {
        GiecoDirectory directory = new GiecoDirectory();

        directory.addOffice("123 Main St, Dallas, TX 75001", "Texas");
        directory.addOffice("456 West St, Austin, TX 75002", "Texas");
        directory.addOffice("789 East St, Albany, NY 12084", "New York");

        // 1. Filter by state
        System.out.println("Offices in Texas:");
        for (GiecoOffice office : directory.filterByState("Texas")) {
            System.out.println(office);
        }

        // 2. Full ZIP search
        System.out.println("\nOffice in ZIP 75001:");
        for (GiecoOffice office : directory.searchByZipPrefix("75001")) {
            System.out.println(office);
        }

        // 3. Partial ZIP search
        System.out.println("\nOffices in ZIPs starting with '75':");
        for (GiecoOffice office : directory.searchByZipPrefix("75")) {
            System.out.println(office);
        }
    }
}