package seedu.giatros.model.patient;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Patient's allergy in the Giatros book.
 * Guarantees: immutable; is always valid
 */
public class Allergy {

    public final String value;

    /**
     * Constructs an {@code Allergy}.
     */
    public Allergy(String allergyString) {
        requireNonNull(allergyString);
        value = allergyString;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Allergy // instanceof handles nulls
                && value.equals(((Allergy) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
