package String;

import java.util.*;

class Conversion {
    String method;
    String cost;

    Conversion(String method, String cost) {
        this.method = method;
        this.cost = cost;
    }
}

class Result {

    List<String> route;
    List<String> carriers;

    Double cost;
}
public class FlightConnection {
    static HashMap<String, HashMap<String, Conversion>> originDestCostMap = new HashMap<>();


    public static void main(String[] args) {
        String input = "UK:US:FedEx:4,UK:FR:Jet1:2,US:UK:RyanAir:8,CA:UK:CanadaAir:8";
        String[] entries = input.split(",");

        for (String entry : entries) {
            String[] data = entry.split(":");
            String origin = data[0];
            String destination = data[1];
            String carrier = data[2];
            String cost = data[3];
            originDestCostMap
                    .computeIfAbsent(origin, k -> new HashMap<>())
                    .put(destination, new Conversion(carrier, cost));


        }

//        for(Map.Entry<String, HashMap<String, Conversion>> entry: originDestCostMap.entrySet()) {
//            System.out.println("key" + entry.getKey());
//            entry.getValue().entrySet().forEach(en-> {
//                System.out.println("output " +en.getKey());
//            });
//        }
        //   String cost = findCost("UK", "FR");
        // System.out.println("cost" + cost);

        Result result = findOneHop("US", "FR");
//        for(String route: result.route) {
//            System.out.print(route + " ->");
//
//        }
        System.out.print(String.join("->",result.route));
        System.out.print(",");
        System.out.print(String.join("->",result.carriers));

        System.out.print(",");



        // for(String cost : result.cost) {
            System.out.println(result.cost );

       // }
    }

    public static String findCost(String origin, String dest) {
        for (Map.Entry<String, HashMap<String, Conversion>> destCostMap : originDestCostMap.entrySet()) {
            if (destCostMap.getKey().equals(origin)) {
                for (Map.Entry<String, Conversion> destCostEntry : destCostMap.getValue().entrySet()) {
                    if (destCostEntry.getKey().equals(dest)) {
                        return destCostEntry.getValue().cost;
                    }
                }
            }
        }
        return null;
    }

    public static Result findOneHop(String origin, String dest) {
        Result result = new Result();

        Double total = 0.0;
        String cost = findCost(origin, dest);
        if (cost != null) {
            result.route = List.of( origin, dest);
            result.carriers = List.of();
            result.cost = Double.parseDouble(cost);
            return  result;
        }
        if (originDestCostMap.containsKey(origin)) {
            HashMap<String, Conversion> firstHops = originDestCostMap.get(origin);
            // origin : US dest : fr
            // US -> UK if first hop
            for (Map.Entry<String, Conversion> firstHop : firstHops.entrySet()) {
                // check if first hop can make to final dest
                String firstHopCost = firstHop.getValue().cost;
                if (originDestCostMap.containsKey(firstHop.getKey())) {
                    HashMap<String, Conversion> possibleDestinationCheck = originDestCostMap.get(firstHop.getKey());
                    if (possibleDestinationCheck.containsKey(dest)) {
                        Conversion finalDest = possibleDestinationCheck.get(dest);
                        result.route = List.of(origin,firstHop.getKey(), dest);
                        result.carriers = List.of(firstHop.getValue().method, finalDest.method);

                         total = Double.parseDouble(firstHopCost)
                                + Double.parseDouble(finalDest.cost);
                         result.cost = total;

                    }
                }


            }
        }
        return result;
    }
}