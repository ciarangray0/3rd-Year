package ct326Assignment3;

/**
 * The {@code Country} enum represents a set of predefined countries.
 * Each country has a corresponding string value to represent its name.
 */
public enum Country {
    IRELAND("ireland"),
    ENGLAND("england"),
    USA("usa"),
    FRANCE("france"),
    SPAIN("spain"),
    BRAZIL("brazil"),
    JAPAN("japan"),
    GERMANY("germany"),
    MORROCCO("morrocco");

    protected String name;

    /**
     * Constructs a {@code Country} enum with the specified name.
     *
     * @param name The name of the country in lowercase form.
     */
    Country(String name) {
        this.name = name;
    }
}
