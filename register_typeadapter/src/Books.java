import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Books {

  // A map of authors index by their name.
  private Map<String, Author> authors = new HashMap<String, Author>();
  private Set<Book> books = new HashSet<Book>();

  public void addAuthor(Author author) {
    authors.put(author.getName(), author);
  }

  public void addBook(Book book) {
    books.add(book);
  }

  public Author getAuthorWithName(String name) {
    return authors.get(name);
  }

  @Override
  public String toString() {
    StringBuilder formattedString = new StringBuilder();
    for (Author author : authors.values()) {
      formattedString.append(author).append("\n");
      for (Book book : author.getBooks()) {
        formattedString.append("  ").append(book).append("\n");
      }
      formattedString.append("\n");
    }

    return formattedString.toString();
  }
}