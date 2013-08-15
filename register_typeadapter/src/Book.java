import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Book {

  private Set<Author> authors;
  private String isbn;
  private String title;

  public Book(String title, String isbn, Author... authors) {
    this.title = title;
    this.isbn = isbn;
    this.authors = new HashSet<Author>(Arrays.asList(authors));
  }

  public Set<Author> getAuthors() {
    return authors;
  }

  @Override
  public String toString() {
    StringBuilder fomrattedString = new StringBuilder();
    fomrattedString.append(title).append(" (").append(isbn).append(")");

    fomrattedString.append(" by: ");
    for (Author author : authors) {
      fomrattedString.append(author.getName()).append(", ");
    }

    // To remove the last comma followed by a space (that's why less 2)
    return fomrattedString.substring(0, fomrattedString.length() - 2);
  }
}