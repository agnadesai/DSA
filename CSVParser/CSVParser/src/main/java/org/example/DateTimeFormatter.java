package org.example;

import java.time.LocalDate;

public class DateTimeFormatter {

    public static void main(String[] args) {
        String date = "25-07-2025";

        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        System.out.println("date " + date);


    }
}
