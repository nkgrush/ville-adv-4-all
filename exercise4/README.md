# Exercise 4

Let's consider the implementations from exercises 1 and 2 again. It is observed that in exercise 2, the possible creation of a book collection has to be done indirectly using a mutable data structure (lines 12, 13, and 36 in the TestZipper2 class). It would be much more straightforward if each handler directly returned the processed value X. Defining X in one place can cause widespread changes throughout the code.

### A) Make a copy of the code from exercises 1 & 2 (classes Zipper, TestZipper, and TestZipper2) under the folder of exercise 4.

Copies are placed into [exercise4 folder](.). Changes were then made to the copied classes.

### B) Use generics and modify the handlers to return something generic (you need to figure out what that could be and how to express it). For example, in exercise 2, the handler should return a book (Book). In exercise 1, nothing is returned. Hint: In Java, there is a type java.lang.Void (Void with a capital V) for this purpose.

- By generic we will return type value `T`, to make Zipper and its Handler generic so that 
`T` matches a Book for exercise 2 and Void for exercise 1.
- Implementation:
  - [exercise4/Zipper.java](./Zipper.java)
  - [exercise4/TestZipper.java](./TestZipper.java)
  - [exercise4/TestZipper2.java](./TestZipper2.java)

### C) Design appropriate interfaces and methods so that handling book collections, as in exercise 2, can be done easily (you don't have to demonstrate, but you can if you want). It can be assumed that easy means, for example, that a book collection sorter (sections 2B-2E can be seen as four different sorters) receives a collection as input and then sorts and prints the result.

>You can decide whether the sorting happens in place or functionally (the sorted collection is a result value). Of course, such choices depend on the overall definition. The idea of this task is to implement the change caused by generics throughout the entire definition without "training wheels," so you have the freedom to make many decisions here.
Hint: If defining the whole system seems difficult, you can approach the problem by completing a part of exercise 2 from start to finish and thereby verify that the definition seems to work. Generalizing is easier when a single instance works.

We will solve this task by defining a functional interface `BookCollectionSorter` that wraps our previous sorting logic
as a functional interface. We then implement those strategies as lambdas under the same interface.

```java
@FunctionalInterface
public interface BookSorter {
    // @.pre books != null
    // @.post (books are sorted and printed without changing the original collection)
    void sortAndPrint(List<Book> books);

    // 2b) natural order by title
    BookSorter naturalOrder = books -> books.stream().sorted()
            .forEach(System.out::println);

    // 2c) ascending by line count
    BookSorter byLines = books -> books.stream().sorted(Comparator.comparingInt(Book::lineCount))
            .forEach(System.out::println);

    // 2d) descending by unique words
    BookSorter byUnique = books -> books.stream().sorted(Comparator.comparingInt(Book::uniqueWords).reversed())
            .forEach(System.out::println);

    // 2e) by title then line count
    BookSorter byTitleThenLines = books -> books.stream().sorted(Comparator.<Book>naturalOrder().thenComparingInt(Book::lineCount))
            .forEach(System.out::println);
}
```

We can then use this interface as a static class to access different sorting methods for a list of books.

```java
    List<Book> books = zipper.run();   // List<Book>

    BookSorter.naturalOrder.sortAndPrint(books);
    BookSorter.byLinesAsc.sortAndPrint(books);
    BookSorter.byUniqueDesc.sortAndPrint(books);
    BookSorter.titleThenLines.sortAndPrint(books);
}
```

---

- link to README.md for whole part 4: [README.md](../README.md)
