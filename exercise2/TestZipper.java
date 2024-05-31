package fi.utu.tech.ooj.exercise4.exercise2;

import fi.utu.tech.ooj.exercise4.exercise1.Zipper;

import java.io.IOException;
import java.nio.file.Path;

class Book {
}

class TestZipper2 extends Zipper {
    Book[] books = new Book[100];
    int idx = 0;

    TestZipper2(String zipFile) throws IOException {
        super(zipFile);
    }

    @Override
    public void run() throws IOException {
        super.run();

        System.out.printf("""

                Handled %d Books.
                Now we could sort it out a bit.

                """, idx);
    }

    @Override
    protected Handler createHandler(Path file) {
        return new Handler(file) {
            @Override
            public void handle() {
                books[idx++] = new Book();
            }
        };
    }
}
