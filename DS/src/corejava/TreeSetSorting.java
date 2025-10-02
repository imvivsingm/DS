package corejava;

import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.Comparator;

class Emp implements Comparable<Emp> {
    private String name;
    private int age;

    public Emp(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }

    @Override
    public String toString() {
        return name + " (" + age + ")";
    }

    @Override
    public int compareTo(Emp other) {
        return Integer.compare(this.age, other.age);
    }
}

public class TreeSetSorting {
    public static void main(String[] args) {
        Comparator<Emp> nameComparator = Comparator.comparing(Emp::getName).thenComparing(Emp::getAge);

        NavigableSet<Emp> employee = new TreeSet<>(nameComparator);

        employee.add(new Emp("xAlice", 2002));
        employee.add(new Emp("zBob", 20));
        employee.add(new Emp("Charlie", 25));

        for (Emp s : employee) {
            System.out.println(s);
        }
    }
}

