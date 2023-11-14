package ivanovvasil.u5d5w2Project.exceptions;

public class NotFoundException extends RuntimeException {
  public NotFoundException(int id) {
    super("There is no item with this id");
  }

  public NotFoundException(String message) {
    super(message);
  }
}
