package String;

import java.util.HashMap;

public class ParseString {

    public static void main(String[] args) {
        HashMap<String, HashMap<String, Double>> map = new HashMap<>();
        String input = "USD:CAD:DHL:5,USD:GBP:FEDX:10";
        String[] entries = input.split(",");
        for(String entry: entries) {
            String[] record = entry.split(":");
             String src = record[0];
             String target = record[1];
             String method = record[2];
             String amount = record[3];
            System.out.println("length" + record.length);

        }
    }
}
