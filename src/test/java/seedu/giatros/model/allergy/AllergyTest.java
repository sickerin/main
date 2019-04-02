package seedu.giatros.model.allergy;

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

}
