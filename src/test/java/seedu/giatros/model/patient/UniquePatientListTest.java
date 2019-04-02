package seedu.giatros.model.patient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_ALLERGY_AMPICILLIN;
import static seedu.giatros.testutil.TypicalPatients.ALICE;
import static seedu.giatros.testutil.TypicalPatients.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.giatros.model.patient.exceptions.DuplicatePatientException;
import seedu.giatros.model.patient.exceptions.PatientNotFoundException;
import seedu.giatros.testutil.PatientBuilder;

public class UniquePatientListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniquePatientList uniquePatientList = new UniquePatientList();

    @Test
    public void contains_nullPatient_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePatientList.contains(null);
    }

    @Test
    public void contains_patientNotInList_returnsFalse() {
        assertFalse(uniquePatientList.contains(ALICE));
    }

    @Test
    public void contains_patientInList_returnsTrue() {
        uniquePatientList.add(ALICE);
        assertTrue(uniquePatientList.contains(ALICE));
    }

    @Test
    public void contains_patientWithSameIdentityFieldsInList_returnsTrue() {
        uniquePatientList.add(ALICE);
        Patient editedAlice = new PatientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .withAllergies(VALID_ALLERGY_AMPICILLIN).build();
        assertTrue(uniquePatientList.contains(editedAlice));
    }

    @Test
    public void add_nullPatient_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePatientList.add(null);
    }

    @Test
    public void add_duplicatePatient_throwsDuplicatePatientException() {
        uniquePatientList.add(ALICE);
        thrown.expect(DuplicatePatientException.class);
        uniquePatientList.add(ALICE);
    }

    @Test
    public void setPatient_nullTargetPatient_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePatientList.setPatient(null, ALICE);
    }

    @Test
    public void setPatient_nullEditedPatient_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePatientList.setPatient(ALICE, null);
    }

    @Test
    public void setPatient_targetPatientNotInList_throwsPatientNotFoundException() {
        thrown.expect(PatientNotFoundException.class);
        uniquePatientList.setPatient(ALICE, ALICE);
    }

    @Test
    public void setPatient_editedPatientIsSamePatient_success() {
        uniquePatientList.add(ALICE);
        uniquePatientList.setPatient(ALICE, ALICE);
        UniquePatientList expectedUniquePatientList = new UniquePatientList();
        expectedUniquePatientList.add(ALICE);
        assertEquals(expectedUniquePatientList, uniquePatientList);
    }

    @Test
    public void setPatient_editedPatientHasSameIdentity_success() {
        uniquePatientList.add(ALICE);
        Patient editedAlice = new PatientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .withAllergies(VALID_ALLERGY_AMPICILLIN).build();
        uniquePatientList.setPatient(ALICE, editedAlice);
        UniquePatientList expectedUniquePatientList = new UniquePatientList();
        expectedUniquePatientList.add(editedAlice);
        assertEquals(expectedUniquePatientList, uniquePatientList);
    }

    @Test
    public void setPatient_editedPatientHasDifferentIdentity_success() {
        uniquePatientList.add(ALICE);
        uniquePatientList.setPatient(ALICE, BOB);
        UniquePatientList expectedUniquePatientList = new UniquePatientList();
        expectedUniquePatientList.add(BOB);
        assertEquals(expectedUniquePatientList, uniquePatientList);
    }

    @Test
    public void setPatient_editedPatientHasNonUniqueIdentity_throwsDuplicatePatientException() {
        uniquePatientList.add(ALICE);
        uniquePatientList.add(BOB);
        thrown.expect(DuplicatePatientException.class);
        uniquePatientList.setPatient(ALICE, BOB);
    }

    @Test
    public void remove_nullPatient_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePatientList.remove(null);
    }

    @Test
    public void remove_patientDoesNotExist_throwsPatientNotFoundException() {
        thrown.expect(PatientNotFoundException.class);
        uniquePatientList.remove(ALICE);
    }

    @Test
    public void remove_existingPatient_removesPatient() {
        uniquePatientList.add(ALICE);
        uniquePatientList.remove(ALICE);
        UniquePatientList expectedUniquePatientList = new UniquePatientList();
        assertEquals(expectedUniquePatientList, uniquePatientList);
    }

    @Test
    public void setPatients_nullUniquePatientList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePatientList.setPatients((UniquePatientList) null);
    }

    @Test
    public void setPatients_uniquePatientList_replacesOwnListWithProvidedUniquePatientList() {
        uniquePatientList.add(ALICE);
        UniquePatientList expectedUniquePatientList = new UniquePatientList();
        expectedUniquePatientList.add(BOB);
        uniquePatientList.setPatients(expectedUniquePatientList);
        assertEquals(expectedUniquePatientList, uniquePatientList);
    }

    @Test
    public void setPatients_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePatientList.setPatients((List<Patient>) null);
    }

    @Test
    public void setPatients_list_replacesOwnListWithProvidedList() {
        uniquePatientList.add(ALICE);
        List<Patient> patientList = Collections.singletonList(BOB);
        uniquePatientList.setPatients(patientList);
        UniquePatientList expectedUniquePatientList = new UniquePatientList();
        expectedUniquePatientList.add(BOB);
        assertEquals(expectedUniquePatientList, uniquePatientList);
    }

    @Test
    public void setPatients_listWithDuplicatePatients_throwsDuplicatePatientException() {
        List<Patient> listWithDuplicatePatients = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicatePatientException.class);
        uniquePatientList.setPatients(listWithDuplicatePatients);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniquePatientList.asUnmodifiableObservableList().remove(0);
    }
}
