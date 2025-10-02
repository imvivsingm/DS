package DS;

class Parent {
    Parent() {
        System.out.println("Parent Default Constructor");
    }

    Parent(String msg) {
        System.out.println("Parent Param Constructor:1 " + msg);
    }
}

class Child extends Parent {
    Child() {
        this("Hello");  // Calls Child(String)
        System.out.println("Child Default Constructor3");
    }

    Child(String msg) {

        super(msg);    // Calls Parent(String)
        msg = msg+msg;
        System.out.println("Child Param Constructor:2 " + msg);
    }
}

public class Test {
    public static void main(String[] args) {
        new Child();
    }
}

