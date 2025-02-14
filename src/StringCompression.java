public class StringCompression {

    public static String compressString(char[] chars) {
        if (chars == null || chars.length == 0) {
            return "";
        }

        StringBuilder compressed = new StringBuilder();
        int countConsecutive = 0;

        for (int i = 0; i < chars.length; i++) {
            countConsecutive++;

            // If next character is different than current, append this char to result
            if (i + 1 >= chars.length || chars[i] != chars[i + 1]) {
                compressed.append(chars[i]);
                compressed.append(countConsecutive);
                countConsecutive = 0;
            }
        }

        // Return the original string if compressed string is not smaller
        return compressed.length() < chars.length ? compressed.toString() : new String(chars);
    }

    public static void main(String[] args) {
        char[] input = "aabcccccaaa".toCharArray();
        String compressed = compressString(input);
        System.out.println("Compressed string: " + compressed);
    }
}