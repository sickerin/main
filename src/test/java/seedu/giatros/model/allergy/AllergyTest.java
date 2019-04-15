package seedu.giatros.model.allergy;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.giatros.testutil.Assert;

public class AllergyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Allergy(null));
    }

    @Test
    public void constructor_invalidAllergyName_throwsIllegalArgumentException() {
        String invalidAllergyName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Allergy(invalidAllergyName));
    }

    @Test
    public void isValidAllergyName() {
        // null allergy name
        Assert.assertThrows(NullPointerException.class, () -> Allergy.isValidAllergyName(null));
    }

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
