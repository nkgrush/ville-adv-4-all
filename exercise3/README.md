# Exercise 4-3

### In the project base for the exercise in the exercise3 folder, there is an implementation that includes the classes Winged, Bipedal, Bird, and Crow. The class Exercise 3 demonstrates the use of these classes. Your task is to interpret the given implementation and answer the following questions:

```java
package nkgrush;

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

public class Exercise3 {
    public Exercise3() {
        System.out.println("Exercise 3");

        challenge1(new Crow());
        challenge2(new Crow());
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
```

#### a.) What language feature is involved in the methods challenge1 and challenge2? How does this feature manifest, and how should it be used?
Feature: routine `challenge2` uses type parameter constrains with multiple interfaces. More boradly both methods use polymorphism.
- `challenge2` uses a generic `<X extends Winged & Bipedal>` to allow any type that implements both interfaces
  to be passed as an argument and restricting it to other argument types.
- `challenge1` uses a specific type `Bird`, which is an abstract class that implements both interfaces,
  but it then recieves `new Crow()`, which is then upcasted to `Bird`, reaching the same goal.
- but `challenge2` is more flexible, as it allows any class that implements both interfaces,
  not just `Bird` or its subclasses. For example a new class `Ptyrodactyl` which is flying and bipedal.

- other language features:
    - default methods
    - multiple interface implementation
    - abstract classes
    - method overriding


#### b.) The methods challenge1 and challenge2 seem to perform the same way based on their output. What are the key functional differences between them?
The key functional differences between `challenge1` and `challenge2` is that:
- `challenge1` is limited to a single class, although it is abstract and in this example used just to provide both interfaces.
  But in the class hierarchy there can be other classes that implement both interfaces independently, and many of them many
  not be subclasses of `Bird`; such as unrelated `Ptyrodactyl`.
- `challenge2` is more correct, although it is a bit rarer syntaxis. It will work in all class hierarchies for all objects
  for which it is sensible to run it (implementing both interfaces)

#### c.) Come up with and implement a meaningful situation from the perspective of object-oriented programming that demonstrates the advantages of the latter method, challenge2. (Presumably, the implementation has some advantages because it has a longer method definition -- why else would someone write a more complex signature for code that does exactly the same thing?).

Here we clearly see that an unrelated class hierarchy which independently implements both interfaces works
only with `challenge2` (which is intended to test any bipedal and winged object), while challenge1 also intends to test
winged and bipedal, but it cant in this case.


```java
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

void main(String[] args) {
    Exercise3 exercise = new Exercise3();

    Pterodactyl ptero = new Pterodactyl();
    // will not compile
    // exercise.challenge1(ptero);

    excercise.challenge2(ptero);
}
```

---

- link to README.md for whole part 4: [README.md](../README.md)


