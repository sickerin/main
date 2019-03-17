package seedu.giatros.testutil;

import seedu.giatros.model.GiatrosBook;
import seedu.giatros.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code GiatrosBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private GiatrosBook giatrosBook;

    public AddressBookBuilder() {
        giatrosBook = new GiatrosBook();
    }

    public AddressBookBuilder(GiatrosBook giatrosBook) {
        this.giatrosBook = giatrosBook;
    }

    /**
     * Adds a new {@code Person} to the {@code GiatrosBook} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        giatrosBook.addPerson(person);
        return this;
    }

    public GiatrosBook build() {
        return giatrosBook;
    }
}
