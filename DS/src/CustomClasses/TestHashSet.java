package CustomClasses;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class Student {
    String name;
    int id;

    public Student(String name, int id) {
        this.name = name;
        this.id = id;
    }

    // equals and hashCode based on id only
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return id == student.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Student{name='" + name + "', id=" + id + '}';
    }
}

public class TestHashSet {
    public static void main(String[] args) {
        Set<Student> studentList = new HashSet<>();

        Student st1 = new Student("A", 1);
        Student st2 = new Student("B", 2);

        studentList.add(st1);
        studentList.add(st2);

        System.out.println("Before mutation:");
        System.out.println("Student set size: " + studentList.size()); // Expected 2
        System.out.println("Set contains st1? " + studentList.contains(st1)); // true

        // Mutate st1.id after adding to HashSet
        st1.id = 2;

        System.out.println("\nAfter mutation:");
        System.out.println("Student set size: " + studentList.size()); // Still 2, but broken state
        System.out.println("Set contains st1? " + studentList.contains(st1)); // Likely false now
        System.out.println("Set contains new Student with id=2? " + studentList.contains(new Student("X", 2))); // true

        System.out.println("\nContents of student set:");
        for (Student s : studentList) {
            System.out.println(s);
        }
    }
}

