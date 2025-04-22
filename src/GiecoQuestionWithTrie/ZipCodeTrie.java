package GiecoQuestionWithTrie;

import java.util.*;

public class ZipCodeTrie {
    private TrieNode root;

    public ZipCodeTrie() {
        root = new TrieNode();
    }

    public void insert(GiecoOffice office) {
        String zip = office.getZipCode();
        if (zip.isEmpty()) return; // Skip if ZIP is missing

        TrieNode node = root;
        for (char digit : zip.toCharArray()) {
            node.children.putIfAbsent(digit, new TrieNode());
            node = node.children.get(digit);
            node.offices.add(office); // Add office to each prefix
        }
    }

    public List<GiecoOffice> searchByPrefix(String prefix) {
        TrieNode node = root;
        for (char digit : prefix.toCharArray()) {
            if (!node.children.containsKey(digit)) {
                return new ArrayList<>();
            }
            node = node.children.get(digit);
        }
        return node.offices;
    }
}
