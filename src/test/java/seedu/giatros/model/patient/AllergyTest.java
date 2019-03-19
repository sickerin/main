package seedu.giatros.model.patient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AllergyTest {

    @Test
    public void equals() {
        Allergy allergy = new Allergy("Some allergy");

        // same values -> returns true
        Allergy allergyCopy = new Allergy(allergy.value);
        assertTrue(allergy.equals(allergyCopy));

        // same object -> returns true
        assertTrue(allergy.equals(allergy));

        // null -> returns false
        assertFalse(allergy.equals(null));

        // different types -> returns false
        assertFalse(allergy.equals("Some allergy"));

        // different allergy -> returns false
        Allergy differentAllergy = new Allergy("Other allergy");
        assertFalse(allergy.equals(differentAllergy));
    }
}
