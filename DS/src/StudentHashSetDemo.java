import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class StudentHashSetDemo {

    static class Student {
        private final String name;
        private final int id;

        public Student(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public int getId() {
            return id;
        }

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
            return "Student{" +
                    "name='" + name + '\'' +
                    ", id=" + id +
                    '}';
        }
    }

    public static void main(String[] args) {
        Set<Student> studentSet = new HashSet<>();

        Student st1 = new Student("A", 1);
        Student st2 = new Student("B", 2);
        Student st3 = new Student("A", 3);

        studentSet.add(st1);
        studentSet.add(st2);
        studentSet.add(st3);

        System.out.println("Initial Set: " + studentSet);
        System.out.println("Set size: " + studentSet.size()); // Expect 2

        // Mutate st1's id
        st1.id = 3;
        System.out.println("\nAfter changing st1's id to 2:");
        System.out.println("Set: " + studentSet);
        System.out.println("Set size: " + studentSet.size()); // Still 2
        // Check if set contains a student with id=2 (new object)
        Student st4 = new Student("X", 2);
        System.out.println("\nDoes set contain new Student(\"X\", 2)? " + studentSet.contains(st4));

    }
}

