package seedu.giatros.testutil;

import seedu.giatros.model.GiatrosBook;
import seedu.giatros.model.patient.Patient;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code GiatrosBook ab = new GiatrosBookBuilder().withPatient("John", "Doe").build();}
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
     * Adds a new {@code Patient} to the {@code GiatrosBook} that we are building.
     */
    public GiatrosBookBuilder withPatient(Patient patient) {
        giatrosBook.addPatient(patient);
        return this;
    }

    public GiatrosBook build() {
        return giatrosBook;
    }
}
