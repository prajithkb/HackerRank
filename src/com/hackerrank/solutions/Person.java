package com.hackerrank.solutions;

import com.google.common.base.Converter;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Person {

    public enum Sex {
        MALE, FEMALE
    }

    String name;
    int age;
    String emailAddress;
    Sex sex;

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void printPerson() {
        System.out.println(name);
    }

    static void checkEligibilityAndConsume(List<Person> people, Predicate<Person> filter, Consumer<Person> consume) {
        people.stream().filter(filter).forEach(consume);
    }

    static String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    static Person create() {
        Random random = new Random();
        Person person = new Person();
        person.setAge(random.nextInt(100));
        person.setSex(random.nextBoolean() ? Sex.MALE : Sex.FEMALE);
        String name = randomIdentifier(random);
        person.setName(name);
        person.setEmailAddress(name + "@gmail.com");
        return person;
    }

    static String randomIdentifier(Random rand) {
        StringBuilder builder = new StringBuilder();
        while (builder.toString().length() == 0) {
            int length = rand.nextInt(5) + 5;
            for (int i = 0; i < length; i++)
                builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
        }
        return builder.toString();
    }

    class ActionConverter<A> extends Converter<A, String>{

        Function<A, String> serializer;

        Function<String,A> deserializer;

        public ActionConverter(Function<A, String> serializer,Function<String,A> deserializer) {
            Preconditions.checkNotNull(serializer, "Serializer cannot be null");
            Preconditions.checkNotNull(deserializer, "Deserializer cannot be null");
        }


        @Override
        protected String doForward(A a) {
            return serializer.apply(a);
        }

        @Override
        protected A doBackward(String b) {
            return deserializer.apply(b);
        }



    }
    static Supplier<Person> personSupplier = Person::create;

    public static void main(String[] args) {

        List<Person> persons = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            persons.add(personSupplier.get());
        }
        System.out.println(persons.stream().map(person -> person.getAge()).filter(integer -> integer > 10).findFirst().get());
        // after filter
        checkEligibilityAndConsume(persons, p -> p.sex == Sex.MALE && p.age >90, p -> p.printPerson());

    }

}