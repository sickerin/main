package seedu.giatros.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.giatros.ui.testutil.GuiTestAssert.assertCardDisplaysPatient;

import org.junit.Test;

import guitests.guihandles.PatientCardHandle;
import seedu.giatros.model.patient.Patient;
import seedu.giatros.testutil.PatientBuilder;

public class PatientCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no allergies
        Patient patientWithNoAllergies = new PatientBuilder().withAllergies(new String[0]).build();
        PatientCard patientCard = new PatientCard(patientWithNoAllergies, 1);
        uiPartRule.setUiPart(patientCard);
        assertCardDisplay(patientCard, patientWithNoAllergies, 1);

        // with allergies
        Patient patientWithAllergies = new PatientBuilder().build();
        patientCard = new PatientCard(patientWithAllergies, 2);
        uiPartRule.setUiPart(patientCard);
        assertCardDisplay(patientCard, patientWithAllergies, 2);
    }

    @Test
    public void equals() {
        Patient patient = new PatientBuilder().build();
        PatientCard patientCard = new PatientCard(patient, 0);

        // same patient, same index -> returns true
        PatientCard copy = new PatientCard(patient, 0);
        assertTrue(patientCard.equals(copy));

        // same object -> returns true
        assertTrue(patientCard.equals(patientCard));

        // null -> returns false
        assertFalse(patientCard.equals(null));

        // different types -> returns false
        assertFalse(patientCard.equals(0));

        // different patient, same index -> returns false
        Patient differentPatient = new PatientBuilder().withName("differentName").build();
        assertFalse(patientCard.equals(new PatientCard(differentPatient, 0)));

        // same patient, different index -> returns false
        assertFalse(patientCard.equals(new PatientCard(patient, 1)));
    }

    /**
     * Asserts that {@code patientCard} displays the details of {@code expectedPatient} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PatientCard patientCard, Patient expectedPatient, int expectedId) {
        guiRobot.pauseForHuman();

        PatientCardHandle patientCardHandle = new PatientCardHandle(patientCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", patientCardHandle.getId());

        // verify patient details are displayed correctly
        assertCardDisplaysPatient(expectedPatient, patientCardHandle);
    }
}
