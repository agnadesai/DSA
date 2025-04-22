package synopsys;

import java.util.*;

class LogEntry {
    private long timestamp;
    private String featureName;
    private String clientId;

    public LogEntry(long timestamp, String featureName, String clientId) {
        this.timestamp = timestamp;
        this.featureName = featureName;
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getFeatureName() {
        return featureName;
    }
}

public class ClientFeatureAnalyzer {
    public List<String> findQualifiedClients(List<LogEntry> day1Logs, List<LogEntry> day2Logs) {
        // Process logs for each day
        Map<String, Set<String>> day1ClientFeatures = processLogs(day1Logs);
        Map<String, Set<String>> day2ClientFeatures = processLogs(day2Logs);

        // Find common clients between both days
        Set<String> day1Clients = day1ClientFeatures.keySet();
        Set<String> day2Clients = day2ClientFeatures.keySet();
        Set<String> commonClients = new HashSet<>(day1Clients);
        commonClients.retainAll(day2Clients);

        // Filter clients based on feature count
        List<String> qualifiedClients = new ArrayList<>();
        for (String clientId : commonClients) {
            Set<String> allFeatures = new HashSet<>();
            allFeatures.addAll(day1ClientFeatures.get(clientId));
            allFeatures.addAll(day2ClientFeatures.get(clientId));

            if (allFeatures.size() >= 2) {
                qualifiedClients.add(clientId);
            }
        }

        Collections.sort(qualifiedClients);
        return qualifiedClients;
    }

    private Map<String, Set<String>> processLogs(List<LogEntry> logs) {
        Map<String, Set<String>> clientFeatures = new HashMap<>();

        for (LogEntry log : logs) {
            clientFeatures.computeIfAbsent(log.getClientId(), k -> new HashSet<>())
                    .add(log.getFeatureName());
        }

        return clientFeatures;
    }

    public static void main(String[] args) {
        List<LogEntry> day1Logs = Arrays.asList(
                new LogEntry(1617981000L, "Login", "clientA"),
                new LogEntry(1617981100L, "Search", "clientA"),
                new LogEntry(1617981200L, "Login", "clientB"),
                new LogEntry(1617981300L, "View", "clientC")
        );

        List<LogEntry> day2Logs = Arrays.asList(
                new LogEntry(1618067400L, "Search", "clientA"),
                new LogEntry(1618067500L, "Purchase", "clientA"),
                new LogEntry(1618067600L, "Login", "clientB"),
                new LogEntry(1618067700L, "Login", "clientD")
        );

        ClientFeatureAnalyzer analyzer = new ClientFeatureAnalyzer();
        List<String> qualifiedClients = analyzer.findQualifiedClients(day1Logs, day2Logs);

        System.out.println("Qualified clients: " + qualifiedClients);
    }


}