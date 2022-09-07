package com.shvetsov.ycore.complexexamples;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ComplexExamples {

    public static void main(String[] args) {

        Person[] personsData = new Person[]{
                new Person(0, "Harry"),
                new Person(0, "Harry"), // дубликат
                new Person(1, "Harry"), // тёзка
                new Person(2, "Harry"),
                new Person(3, "Emily"),
                new Person(4, "Jack"),
                new Person(4, "Jack"),
                new Person(5, "Amelia"),
                new Person(5, "Amelia"),
                new Person(6, "Amelia"),
                new Person(7, "Amelia"),
                new Person(8, "Amelia"),
        };

        System.out.println("*************************************************************************");
        System.out.println("Raw data:");
        System.out.println("*************************************************************************");

        for (Person person : personsData) {
            System.out.println(person.getId() + " - " + person.getName());
        }

        System.out.println();
        System.out.println("*************************************************************************");
        System.out.println("Duplicate filtered, grouped by name, sorted by name and id:");
        System.out.println("*************************************************************************");

        removeDuplicates(personsData);

        System.out.println();
        System.out.println("*************************************************************************");
        System.out.println("Убрать дубликаты, отсортировать по идентификатору, сгруппировать по имени");
        System.out.println("*************************************************************************");

        count(personsData);

        System.out.println("*************************************************************************");
    }


    public static void removeDuplicates(Person[] personsData) {

        Map<Integer, Person> mapPersons;
        //Comparator<Person> personComparator = Comparator.comparing(Person::getName).thenComparing(Person::getId);
        Comparator<Map.Entry<Integer, Person>> entryComparator = (o1, o2) -> {
            int res = o1.getValue().getName().compareTo(o2.getValue().getName());
            if (res == 0) {
                if (o1.getValue().getId() != Integer.MAX_VALUE && o2.getValue().getId() > -1) {
                    return o1.getValue().getId() - (o2.getValue().getId());
                } else {
                    return 1;
                }
            }
            return res;
        };

        mapPersons = Arrays.stream(personsData)
                .collect(Collectors.toMap(Person::getId, e -> new Person(e.getId(), e.getName()), (o1, o2) -> o1));

        Set<Map.Entry<Integer, Person>> entrySet = mapPersons.entrySet();
        List<Map.Entry<Integer, Person>> entriesList = new ArrayList<>(entrySet);

        entriesList.sort(entryComparator);

        int count = 1;
        int nameCount = 1;
        String currentName;
        String nextName;

        for (int i = 0; i < entriesList.size(); i++) {

            currentName = entriesList.get(i).getValue().getName();
            nextName = entriesList.get(nameCount).getValue().getName();

            if (i == 0) {
                System.out.println(currentName + ":");
            }

            if (!currentName.equals(nextName)) {
                System.out.println(count + " " + entriesList.get(i).getValue().getName() + " (" + entriesList.get(i).getValue().getId() + ")");
                System.out.println(nextName + ":");
                count = 1;
            } else {
                System.out.println(count + " " + entriesList.get(i).getValue().getName() + " (" + entriesList.get(i).getValue().getId() + ")");
                count++;
            }

            if (nameCount < entriesList.size() - 1) {
                nameCount++;
            }
        }
    }

    public static void count(Person[] personsData) {
        Map<String, Integer> personCount = new HashMap<>();
        Integer count = 1;
        Arrays.stream(personsData).distinct().forEach(person -> personCount.merge(person.getName(), count, Integer::sum));

        for (Map.Entry<String, Integer> set : personCount.entrySet()) {
            System.out.println("Key: " + set.getKey() + "\n" + "Value:" + set.getValue());
        }
    }


    //*******************************TASK 2
    public int[] findPair(int[] data, int target) {

        Set<Integer> setValues = new HashSet<>();

        for (int i = 0; i < data.length; i++) {
            Integer valueToFind = target - data[i];
            if (setValues.contains(valueToFind)) {
                return new int[]
                        {valueToFind, data[i]};
            }
            setValues.add(data[i]);
        }
        return new int[0];
    }

    //*******************************TASK 3
    public boolean fuzzySearch(String input, String text) {
        char[] inChar = input.toCharArray();
        char[] textChar = text.toCharArray();
        int i = 0;

        for (int j = 0; j < textChar.length; j++) {
            if (inChar[i] == textChar[j]) {
                if (i == inChar.length - 1) {
                    return true;
                } else {
                    i++;
                }
            }
        }
        return i > inChar.length;
    }
}

class Person {

    private int id;
    private String name;

    public Person() {
    }

    Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return getId() == person.getId() && getName().equals(person.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString() {
        return name + " " + "(" + id + ")";
    }
}

class ComplexExamplesTest {

    ComplexExamples complexExamples;

    //*******************************TASK 2
    int[] values1;
    int[] values2;
    int[] values3;
    int[] values4;
    int target1;
    int target2;
    int target3;
    int target4;

    //*******************************TASK 3
    String input1;
    String input2;
    String input3;
    String input4;
    String input5;
    String input6;
    String input7;

    String text1;
    String text2;
    String text3;
    String text4;
    String text5;
    String text6;
    String text7;

    @BeforeEach
    void setUp() {

        complexExamples = new ComplexExamples();

        //*******************************TASK 2
        values1 = new int[]
                {3, 4, 2, 7};
        values2 = new int[]
                {2, 5, 9, 1, 10};
        values3 = new int[]
                {-3, 1, 8, 2};
        values4 = new int[]
                {4, 1, 8, 2};

        target1 = 10;
        target2 = 11;
        target3 = 9;
        target4 = 100;

        //*******************************TASK 3
        input1 = "car";
        input2 = "cwhl";
        input3 = "cwhee";
        input4 = "cartwheel";
        input5 = "cwheeel";
        input6 = "lw";
        input7 = "carex";


        text1 = "ca6$$#_rtwheel";
        text2 = "cartwheel";
        text3 = "cartwheel";
        text4 = "cartwheel";
        text5 = "cartwheel";
        text6 = "cartwheel";
        text7 = "car";
    }

    //*******************************TASK 2
    @Test
    void shouldReturnArrayOfNumsThatSumOfTarget() {
        assertThat(complexExamples.findPair(values1, target1)).containsExactly(3, 7);
        assertThat(complexExamples.findPair(values2, target2)).containsExactly(2, 9);
        assertThat(complexExamples.findPair(values3, target3)).containsExactly(1, 8);
        assertThat(complexExamples.findPair(values4, target4)).isEmpty();
    }

    //*******************************TASK 3
    @Test
    void shouldReturnFuzzySearchResult() {
        assertThat(complexExamples.fuzzySearch(input1, text1)).isTrue();
        assertThat(complexExamples.fuzzySearch(input2, text2)).isTrue();
        assertThat(complexExamples.fuzzySearch(input3, text3)).isTrue();
        assertThat(complexExamples.fuzzySearch(input4, text4)).isTrue();
        assertThat(complexExamples.fuzzySearch(input5, text5)).isFalse();
        assertThat(complexExamples.fuzzySearch(input6, text6)).isFalse();
        assertThat(complexExamples.fuzzySearch(input7, text7)).isFalse();

    }
}






