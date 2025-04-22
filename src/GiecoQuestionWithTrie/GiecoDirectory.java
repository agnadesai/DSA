package GiecoQuestionWithTrie;

import java.util.HashMap;
import java.util.List;

import java.util.*;

public class GiecoDirectory {
    private Map<String, List<GiecoOffice>> stateMap;
    private ZipCodeTrie zipTrie;

    public GiecoDirectory() {
        stateMap = new HashMap<>();
        zipTrie = new ZipCodeTrie();
    }

    public void addOffice(String address, String state) {
        GiecoOffice office = new GiecoOffice(address, state);
        stateMap.putIfAbsent(state.toLowerCase(), new ArrayList<>());
        stateMap.get(state.toLowerCase()).add(office);
        zipTrie.insert(office);
    }

    public List<GiecoOffice> filterByState(String state) {
        return stateMap.getOrDefault(state.toLowerCase(), new ArrayList<>());
    }

    public List<GiecoOffice> searchByZipPrefix(String zipPrefix) {
        return zipTrie.searchByPrefix(zipPrefix);
    }
}

