package fi.utu.tech.ooj.exercise4.exercise2;

import fi.utu.tech.ooj.exercise4.exercise1.Zipper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

// Book: record storing a book file informaiton
// implements Comparable<Book>
//
record Book(
        Path path, String title, int lineCount, int uniqueWords
) implements Comparable<Book> {
    // @.classInvariant path != null
    // && title != null && title != ""
    // && lineCount >= 0 && uniqueWords >= 0

    // factory method to create new Book from file
    //
    // @.pre file != null
    // && (file is a non-empty text file)
    // @.post (RESULT meets class invariant)
    public static Book fromFile(Path file) throws IOException {
        var lines = Files.readAllLines(file);
        var first = lines.getFirst().trim();

        var regex = Pattern.compile("\\W+");
        int uniq = (int) regex.splitAsStream(String.join("\n", lines))
                .filter(s -> !s.isBlank())
                .map(String::toLowerCase)
                .distinct()
                .count();

        return new Book(file, first, lines.size(), uniq);
    }

    // implement Comparable<Book> to allow natural order by title
    @Override public int compareTo(Book o) {
        return this.title.compareToIgnoreCase(o.title);
    }

    // override toString to print Book information
    @Override public String toString() {
        return "%s (lines %d, unique words %d)".formatted(title, lineCount, uniqueWords);
    }
}

public class TestZipper2 extends Zipper {
    private final List<Book> books = new ArrayList<>();

    public TestZipper2(String zipFile) throws IOException {
        super(zipFile);
    }

    @Override
    protected Handler createHandler(Path file) {
        return new Handler(file) {
            @Override
            public void handle() throws IOException {
                books.add(Book.fromFile(file));
            }
        };
    }

    @Override
    public void run() throws IOException {
        super.run();

        System.out.printf("Read %d Books.\n\n", books.size());

        // b) natural order
        System.out.println("Printing by natural order by title");
        books.stream().sorted().forEach(System.out::println);
        System.out.println();

        // c) ascending by line count
        System.out.println("Printing by natural order by line count");
        Comparator<Book> secondOrderByLines = Comparator.comparingInt(Book::lineCount);
        books.stream().sorted(secondOrderByLines).forEach(System.out::println);
        System.out.println();

        // d) descending by unique words
        System.out.println("Printing by natural order by unique words");
        Comparator<Book> thirdOrderByUnique = Comparator.comparingInt(Book::uniqueWords).reversed();
        books.stream().sorted(thirdOrderByUnique).forEach(System.out::println);
        System.out.println();

        // e) first by title, then by line count
        System.out.println("Printing by natural order by title, then line count");
        Comparator<Book> forthOrderByTitleThenLines = Comparator.<Book>naturalOrder().thenComparing(secondOrderByLines);
        books.stream().sorted(forthOrderByTitleThenLines).forEach(System.out::println);
        System.out.println();
    }
}
