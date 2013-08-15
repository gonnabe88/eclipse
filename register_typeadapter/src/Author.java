import java.util.HashSet;
import java.util.Set;

public class Author {

  private Set<Book> books = new HashSet<Book>();
  private String name;

  public Author(final String name) {
    this.name = name;
  }

  public void addBook(Book book) {
    books.add(book);
  }

  public Set<Book> getBooks() {
    return books;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return String.format("%s has %d book(s)", name, books.size());
  }
}