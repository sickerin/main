package seedu.giatros.model.patient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_ALLERGY_AMPICILLIN;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.giatros.testutil.TypicalPatients.ALICE;
import static seedu.giatros.testutil.TypicalPatients.BOB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.giatros.testutil.PatientBuilder;

public class PatientTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Patient patient = new PatientBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        patient.getAllergies().remove(0);
    }

    @Test
    public void isSamePatient() {
        // same object -> returns true
        assertTrue(ALICE.isSamePatient(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePatient(null));

        // different phone and email -> returns false
        Patient editedAlice = new PatientBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSamePatient(editedAlice));

        // different name -> returns false
        editedAlice = new PatientBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePatient(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new PatientBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withAllergies(VALID_ALLERGY_AMPICILLIN).build();
        assertTrue(ALICE.isSamePatient(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new PatientBuilder(ALICE).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withAllergies(VALID_ALLERGY_AMPICILLIN).build();
        assertTrue(ALICE.isSamePatient(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new PatientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .withAllergies(VALID_ALLERGY_AMPICILLIN).build();
        assertTrue(ALICE.isSamePatient(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Patient aliceCopy = new PatientBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different patient -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Patient editedAlice = new PatientBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PatientBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PatientBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PatientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different allergies -> returns false
        editedAlice = new PatientBuilder(ALICE).withAllergies(VALID_ALLERGY_AMPICILLIN).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
