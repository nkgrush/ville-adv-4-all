package fi.utu.tech.ooj.exercise4.exercise3;

interface Winged {
    default void fly() {
        System.out.println(this + " flies!");
    }
}

interface Bipedal {
    default void walk() {
        System.out.println(this + " walks!");
    }
}

abstract class Bird implements Winged, Bipedal {

}

class Crow extends Bird {
    private static int idx = 0;
    private int id = ++idx;

    @Override
    public String toString() {
        return "Varis " + id;
    }
}


// example showing challange2 advantages

class Dinosaur implements Bipedal {
    @Override
    public String toString() {
        return "ROOOOAR!";
    }

    /*other dinosaur business-logic*/
}

class Pterodactyl extends Dinosaur implements Winged {
    @Override
    public void fly() {
        System.out.println(this + " flies!");
    }
    /* pterodactyls business logic*/
}



public class Exercise3 {
    public Exercise3() {
        System.out.println("Exercise 3");

        challenge1(new Crow());
        challenge2(new Crow());

        // will not compile, because Pterodactyl is not strictly a Bird
        // challenge1(new Pterodactyl());
        challenge2(new Pterodactyl());
    }

    void challenge1(Bird b) {
        System.out.println("In this challenge, we fly and then we walk!");

        b.fly();
        b.walk();
    }

    <X extends Winged & Bipedal> void challenge2(X b) {
        System.out.println("In this challenge, we fly and then we walk!");

        b.fly();
        b.walk();
    }
}
