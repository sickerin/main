package seedu.giatros.model.patient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.giatros.model.allergy.Allergy;

public class AllergyTest {

    @Test
    public void equals() {
        Allergy allergy = new Allergy("someAllergy");

        // same values -> returns true
        Allergy allergyCopy = new Allergy(allergy.allergyName);
        assertTrue(allergy.equals(allergyCopy));

        // same object -> returns true
        assertTrue(allergy.equals(allergy));

        // null -> returns false
        assertFalse(allergy.equals(null));

        // different types -> returns false
        assertFalse(allergy.equals("someAllergy"));

        // different allergy -> returns false
        Allergy differentAllergy = new Allergy("otherAllergy");
        assertFalse(allergy.equals(differentAllergy));
    }
}
