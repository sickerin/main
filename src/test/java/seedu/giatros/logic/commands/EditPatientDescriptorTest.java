package seedu.giatros.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.giatros.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.giatros.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_ALLERGY_AMPICILLIN;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import org.junit.Test;

import seedu.giatros.logic.commands.EditCommand.EditPatientDescriptor;
import seedu.giatros.testutil.EditPatientDescriptorBuilder;

public class EditPatientDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditPatientDescriptor descriptorWithSameValues = new EditPatientDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditPatientDescriptor editedAmy = new EditPatientDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditPatientDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditPatientDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditPatientDescriptorBuilder(DESC_AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different allergies -> returns false
        editedAmy = new EditPatientDescriptorBuilder(DESC_AMY).withAllergies(VALID_ALLERGY_AMPICILLIN).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
