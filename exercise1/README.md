# Exercise 1

In the project template for the exercise, there is an implementation in the exercise1 folder that includes the classes Zipper and TestZipper. The use of these classes is demonstrated in the Exercise1 class. Your task is to interpret the given implementation and answer the following questions:

### a.) Go through each class in the implementation and describe which class construct it represents. Look carefully to find all the classes. The exercise intentionally includes code that appears difficult to increase the challenge of identification.

- `public class Exercise1` a concrete top level class; it has no fields and it's constructor is used to run the exercise logic for demonstration
- `abstract class Zipper implements AutoCloseable` an abstract top‑level class; it defines all generic logic - create a temp directory, unzip, iterate files, delete on close
- `protected abstract static class Handler`, defined inside `Zipper`; it is a protected static nested abstract class.
It separately defines `handle()` contract; provides a separate static entity inside
- `class TestZipper extends Zipper` a protected package-private concrete class; it provides `createHandler` method
- `new Handler(file) { /**/}` anonymous inner class;  -- provides concrete implementation of Handler 

### b.) For each construct, explain why that particular construct was chosen. Describe which features of the construct are used why, and why another construct would not be suitable for the task.

- `public class Exercise1` -- concrete top level class was chosen to incapsulate the exercise logic; the feature used here is the ability to define methods inside a class; alternatively, a static method could be used, but it also makes sense to have exercise1 as a separate class, and not a method in Main 
- `abstract class Zipper implements AutoCloseable` an abstract top‑level class; the feature here is abstract methods in an abstract class. It forces the subclass to provide implementation for the methods defined in the parent class, while the parent can also implement some methods with common logic
- `protected abstract static class Handler`, protected static nested abstract class; the features here are the static keyword for an internal class, which allows it to be instatiated without an instance of outer class; the abstract keyword forces subclasses to implement the `handle()` method, thus creating a contract for handling files; protected to limit visibility
- `class TestZipper extends Zipper` a protected package-private concrete class; the feature here is inheritance, which allows `TestZipper` to reuse the logic defined in `Zipper` and implement the abstract methods, getting a concrete class
- `new Handler(file) { /**/}` anonymous inner class; feature here is creation of an anonymous subclass of `Handler`, proviging a concrete implementation with `handle`

### C) It is said that the temporary directory has a finite lifespan. How does the concept of the directory's lifespan relate to the classes defined in the implementation?

Here the lifespan is handled by `Zipper` class which implements `AutoCloseable`, this interface consists of a single method `close()`.
In `Zipper` constructor a directory is created with `Files.createTempDirectory`. The directory exists as long as Zipper 
instance was not closed. When zipper calls `close()` the implementation from `Zipper` walks directories and removes them.
So here the lifespan is controlled by AutoClosable interface. The `Excercise1` class uses try with resources block to
implicitly use `AutoClosable` interface of zipper.

### D) Why does the class signature for Handler include the keywords `protected abstract static class Handler` and how can the word static be appropriate in this context?

- `protected`: only Zipper and its subclasses can access Handler subclass, it is not directly accessible by external clients
- `abstract`: this class has abstract keyword, because it requires a concrete class first, implementing `abstract handle()`, before it can be instantiated
- `static`: static inner class means that it can be instantiated from the outer class `Zipper` itself, without creating an instance, and it is also not bound to an instance


---

- link to README.md for whole part 4: [README.md](../README.md)
