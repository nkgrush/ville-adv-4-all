package fi.utu.tech.ooj.exercise2.teht2;

import java.io.IOException;

public class Teht2 {
    public Teht2() {
        System.out.println("Tehtävä 2");

        try (var zipper = new TestZipper2("books.zip")) {
            zipper.run();
        } catch (IOException e) {
            System.err.println("Ajo epäonnistui!");
            e.printStackTrace();
        }
    }
}
