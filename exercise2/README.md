# Exercise 2

Use the implementation provided in Exercise 1. The folder for exercise2 also contains code that can give you ideas for the solution.

>Hint: In this task, it is advisable to use collection classes instead of arrays, as the fixed-size array in the example code can be problematic in many ways if its initialized size differs from the number of books.


### A) Define and implement a class Book that represents a book, such that the class models a file representing the book (there are many ways to do this), the book's name (the entire first line of the file, e.g., "The Project Gutenberg eBook of Moby Dick; Or, The Whale"), and the number of lines in the book. The class only needs to be defined to the extent that it is usable in the subsequent parts of the task B--E.

[Book implemented in Exercise2 file](Exercise2.java)


### B) Implement a natural order for the books using the basic features of objects. The natural order of books is in ascending alphabetical order of the book's name. Demonstrate this order by printing the books in their natural order. Use Java's standard library sorting routine for sorting.

We would define the books order by implementing `Comparable<Book>`.
Then through compareTo we print books using a stream.

```java
        System.out.println("Printing by natural order by title");
        books.stream().sorted().forEach(System.out::println);
        System.out.println();
```

(see the Excercise2 class execution under main for output).

### C) Implement a secondary order for the books, which is in ascending order of their line count. Demonstrate this implementation by printing the books in this order. Use Java's standard library sorting routine for sorting.

```java
        System.out.println("Printing by natural order by line count");
        Comparator<Book> secondOrderByLines = Comparator.comparingInt(Book::lineCount);
        books.stream().sorted(secondOrderByLines).forEach(System.out::println);
        System.out.println();
```

### D) Implement a third order for the books, which is in descending order of the number of unique words they contain. The implementation in Exercise 1 already lists all the words in the book. Now, it is sufficient to count the unique words. For example, in the word list ['aa', 'bb', 'aa', 'cc'], there are three unique words: ['aa', 'bb', 'cc']. Demonstrate this implementation by printing the books in this order. Use Java's standard library sorting routine for sorting.

```java
        System.out.println("Printing by natural order by unique words");
        Comparator<Book> thirdOrderByUnique = Comparator.comparingInt(Book::uniqueWords).reversed();
        books.stream().sorted(thirdOrderByUnique).forEach(System.out::println);
        System.out.println();
```

### E) Implement a fourth order for the books, which is primarily the order defined in B) and secondarily the order defined in either C) or D) (your choice). Demonstrate this implementation by printing the books in this order. Use Java's standard library sorting routine for sorting.

```java
        System.out.println("Printing by natural order by title, then line count");
        Comparator<Book> forthOrderByTitleThenLines = Comparator.<Book>naturalOrder().thenComparing(secondOrderByLines);
        books.stream().sorted(forthOrderByTitleThenLines).forEach(System.out::println);
        System.out.println();
```

---

- link to README.md for whole part 4: [README.md](../README.md)