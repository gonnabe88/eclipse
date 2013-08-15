
public enum Category {

  FRUIT("FRT", "Fruit"), VEGETABLES("VEG", "Vegetables");

  private final String code;
  private final String name;

  private Category(final String code, final String name) {
    this.code = code;
    this.name = name;
  }
  // Methods removed for brevity
}
