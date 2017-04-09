package com.hackerrank.annotations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class FunctionalJava {


    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    static class Person {
        String name;
        int age;
        String city = "bangalore";


        @Override
        public String toString() {
            return name;
        }
    }


    public static void main(String[] args) {

        Collector<Person, ArrayList<String>, ArrayList<String>> personNameCollector = Collector.of(
                ArrayList::new,
                (strings, person) -> strings.add(person.getName()),
                (c, d) -> {
                    c.addAll(d);
                    return c;
                }
        );

        List<Person> persons =
                Arrays.asList(
                        new Person("Max", 18, "Bangalore"),
                        new Person("Peter", 23, "Chennai"),
                        new Person("Poona", 23, "Chennai"),
                        new Person("Pamela", 29, "Chennai"),
                        new Person("David", 21, "Bangalore"),
                        new Person("Prajith", 28, "Mumbai"),
                        new Person("Pooja", 90, "Mumbai"));

        List<Person> filtered =
                persons
                        .stream()
                        .filter(p -> p.name.startsWith("P"))
                        .collect(Collectors.toList());

//        System.out.println(filtered);    // [Peter, Pamela]
//        System.out.println(persons
//                .stream()
//                .collect(personNameCollector));
        System.out.println(persons.stream()
                .collect(Collectors
                        .toMap(Person::getCity, Function.identity(), BinaryOperator
                                .minBy(Comparator
                                        .comparing(Person::getAge)))));

    }

}


