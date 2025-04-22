package GiecoQuestionWithTrie;

import java.util.*;

public class TrieNode {
    Map<Character, TrieNode> children;
    List<GiecoOffice> offices;

    public TrieNode() {
        children = new HashMap<>();
        offices = new ArrayList<>();
    }
}