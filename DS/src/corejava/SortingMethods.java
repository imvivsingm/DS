package corejava;

import java.util.*;

class student implements Comparable<student> {
    private String name;
    private int age;

    public student(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    @Override
    public String toString() {
        return "student{" + "name=" + name + ", age=" + age + '}';
    }
    @Override
    public int compareTo(student other) {
        return Integer.compare(this.age, other.age);
    }
}
public class SortingMethods {

    public static void main(String[] args) {
        List<student> students = new ArrayList<>();
        students.add(new student(91, "A"));
        students.add(new student(2, "B"));
        students.add(new student(3, "aaa"));
        students.add(new student(3, "bbb"));
        System.out.println(students);
        Comparator<student> comparator = Comparator.comparing(student::getAge).thenComparing(student::getName);
        students.sort(comparator);
        System.out.println(students);

    }

}
