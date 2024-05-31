package fi.utu.tech.ooj.exercise4.exercise2;

import java.io.IOException;

public class Exercise2 {
    public Exercise2() {
        System.out.println("Exercise 2");

        try (var zipper = new TestZipper2("books.zip")) {
            zipper.run();
        } catch (IOException e) {
            System.err.println("Execution failed!");
            e.printStackTrace();
        }
    }
}
