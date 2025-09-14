package org.example;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

class Employee {
    String name;
    String role;

    Employee(String name, String role) {
        this.name = name;
        this.role = role;
    }
}
public class ReadUtf8File {

    public static void main(String[] args) {
        try (InputStream is = ReadUtf8File.class.getClassLoader().getResourceAsStream("test.csv");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
          //  byte[] bytes = is.readAllBytes();
          //  System.out.println("hello" + new String(bytes, StandardCharsets.UTF_8));
            String line;
            reader.readLine();
            ArrayList<Employee> employees = new ArrayList();
            while ((line = reader.readLine()) != null) {
                String[] values= line.split(",");
                Employee employee = new Employee(values[0], values[1]);
                employees.add(employee);
                //System.out.println(values[0]);
            }

            for(Employee employee: employees) {
                System.out.println("name " + employee.name);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
