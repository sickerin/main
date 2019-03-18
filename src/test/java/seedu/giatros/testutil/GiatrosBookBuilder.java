package seedu.giatros.testutil;

import seedu.giatros.model.GiatrosBook;
import seedu.giatros.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code GiatrosBook ab = new GiatrosBookBuilder().withPerson("John", "Doe").build();}
 */
public class GiatrosBookBuilder {

    private GiatrosBook giatrosBook;

    public GiatrosBookBuilder() {
        giatrosBook = new GiatrosBook();
    }

    public GiatrosBookBuilder(GiatrosBook giatrosBook) {
        this.giatrosBook = giatrosBook;
    }

    /**
     * Adds a new {@code Person} to the {@code GiatrosBook} that we are building.
     */
    public GiatrosBookBuilder withPerson(Person person) {
        giatrosBook.addPerson(person);
        return this;
    }

    public GiatrosBook build() {
        return giatrosBook;
    }
}
