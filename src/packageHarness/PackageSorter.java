package packageHarness;

public class PackageSorter {

    /**
     * Determine the dispatch stack for a package based on its dimensions and mass.
     *
     * @param width  package width in cm
     * @param height package height in cm
     * @param length package length in cm
     * @param mass   package mass in kg
     * @return "STANDARD", "SPECIAL", or "REJECTED"
     */
    public static String sort(double width, double height, double length, double mass) {
        // Calculate volume
        double volume = width * height * length;

        // Check conditions
        boolean isBulky = volume >= 1_000_000
                || width >= 150
                || height >= 150
                || length >= 150;

        boolean isHeavy = mass >= 20;

        // Determine stack
        if (isBulky && isHeavy) {
            return "REJECTED";
        } else if (isBulky || isHeavy) {
            return "SPECIAL";
        } else {
            return "STANDARD";
        }
    }

    // Simple test harness
    public static void main(String[] args) {
        System.out.println(sort(50, 50, 50, 10));      // STANDARD
        System.out.println(sort(200, 100, 50, 10));    // SPECIAL (bulky)
        System.out.println(sort(40, 40, 40, 25));      // SPECIAL (heavy)
        System.out.println(sort(200, 200, 200, 25));   // REJECTED (bulky + heavy)

        // Edge cases
        System.out.println(sort(100, 100, 100, 20));   // SPECIAL (heavy threshold)
        System.out.println(sort(150, 50, 50, 10));     // SPECIAL (dimension threshold)
        System.out.println(sort(100, 100, 100, 19.99));// STANDARD (just under heavy)
    }
}
