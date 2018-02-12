package third_sem.streams;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Streams {
    private static final String filename = "table.txt";

    private static class Employee {
        String name, post;
        int salary;

        Employee(String line) {
            String[] divided = line.split(" ");
            name = divided[0];
            post = divided[1];
            salary = Integer.parseInt(divided[2]);
        }

        public String getName() {
            return name;
        }
    }

    public static void main(String[] args) {
        List<Employee> employees;
        try {
            File source = new File(filename);

            //using streams to read lines from source file and turn them into list of employees
            employees = Files
                    .readAllLines(source.toPath())
                    .stream()
                    .map(Employee::new)
                    .collect(Collectors.toList());

            Collections.sort(employees, Comparator.comparing(Employee::getName));
            for(Employee e : employees) System.out.println(e.getName());

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }


        //checking functions
        System.out.println("Max salary:");
        System.out.println(maxSalary(employees));

        System.out.println("Min salary:");
        System.out.println(minSalary(employees));

        System.out.println("Average salary:");
        System.out.println(averageSalary(employees));

        System.out.println("Quantity of employees with a specific post:");
        System.out.println(jobCount(employees));

        System.out.println("Quantity of employees with a specific name's first char:");
        System.out.println(abc(employees));


    }

    //following functions(max,min and average salary) use basic stream methods
    public static int maxSalary(List<Employee> list) {
        return list
                .stream()
                .mapToInt((e) -> e.salary)
                .max()
                .getAsInt();
    }

    public static int minSalary(List<Employee> list) {
        return list
                .stream()
                .mapToInt((e) -> e.salary)
                .min()
                .getAsInt();

    }

    public static double averageSalary(List<Employee> list) {
        return list
                .stream()
                .mapToInt((e) -> e.salary)
                .average()
                .getAsDouble();
    }

    public static Map<String, Integer> jobCount(List<Employee> list) {
        //use post as the key
        //use 1(one) as a value and  Integer::sum as merge function
        return list
                .stream()
                .collect(Collectors
                        .toMap(
                                (e) -> e.post,
                                (e) -> 1,
                                Integer::sum));
    }

    public static Map<Character, Integer> abc(List<Employee> list) {
        //use first char as key
        //use 1(one) as a value and  Integer::sum as merge function
        return list
                .stream()
                .sorted(Comparator.comparing(Employee::getName))
                .collect(Collectors
                        .toMap(
                                (e) -> e.name.charAt(0),
                                (e) -> 1,
                                Integer::sum));
    }

}

