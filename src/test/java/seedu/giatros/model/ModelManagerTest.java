package seedu.giatros.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_NAME;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_PASSWORD;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_USERNAME;
import static seedu.giatros.model.Model.PREDICATE_SHOW_ALL_PATIENTS;
import static seedu.giatros.testutil.TypicalAccounts.BABA;
import static seedu.giatros.testutil.TypicalAccounts.MANAGER;
import static seedu.giatros.testutil.TypicalPatients.ALICE;
import static seedu.giatros.testutil.TypicalPatients.BENSON;
import static seedu.giatros.testutil.TypicalPatients.BOB;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.giatros.commons.core.GuiSettings;
import seedu.giatros.model.account.Account;
import seedu.giatros.model.account.Name;
import seedu.giatros.model.account.Password;
import seedu.giatros.model.account.Username;
import seedu.giatros.model.account.exceptions.AccountNotFoundException;
import seedu.giatros.model.patient.NameContainsKeywordsPredicate;
import seedu.giatros.model.patient.Patient;
import seedu.giatros.model.patient.exceptions.PatientNotFoundException;
import seedu.giatros.testutil.GiatrosBookBuilder;
import seedu.giatros.testutil.PatientBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new GiatrosBook(), new GiatrosBook(modelManager.getGiatrosBook()));
        assertEquals(null, modelManager.getSelectedPatient());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setUserPrefs(null);
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setGiatrosBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setGiatrosBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setGuiSettings(null);
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setGiatrosBookFilePath(null);
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setGiatrosBookFilePath(path);
        assertEquals(path, modelManager.getGiatrosBookFilePath());
    }

    @Test
    public void hasPatient_nullPatient_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasPatient(null);
    }

    @Test
    public void hasPatient_patientNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPatient(ALICE));
    }

    @Test
    public void hasPatient_patientInAddressBook_returnsTrue() {
        modelManager.addPatient(ALICE);
        assertTrue(modelManager.hasPatient(ALICE));
    }

    @Test
    public void deletePatient_patientIsSelectedAndFirstPatientInFilteredPatientList_selectionCleared() {
        modelManager.addPatient(ALICE);
        modelManager.setSelectedPatient(ALICE);
        modelManager.deletePatient(ALICE);
        assertEquals(null, modelManager.getSelectedPatient());
    }

    @Test
    public void deletePatient_patientIsSelectedAndSecondPatientInFilteredPatientList_firstPatientSelected() {
        modelManager.addPatient(ALICE);
        modelManager.addPatient(BOB);
        assertEquals(Arrays.asList(ALICE, BOB), modelManager.getFilteredPatientList());
        modelManager.setSelectedPatient(BOB);
        modelManager.deletePatient(BOB);
        assertEquals(ALICE, modelManager.getSelectedPatient());
    }

    @Test
    public void setPatient_patientIsSelected_selectedPatientUpdated() {
        modelManager.addPatient(ALICE);
        modelManager.setSelectedPatient(ALICE);
        Patient updatedAlice = new PatientBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        modelManager.setPatient(ALICE, updatedAlice);
        assertEquals(updatedAlice, modelManager.getSelectedPatient());
    }

    @Test
    public void getFilteredPatientList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPatientList().remove(0);
    }

    @Test
    public void setSelectedPatient_patientNotInFilteredPatientList_throwsPatientNotFoundException() {
        thrown.expect(PatientNotFoundException.class);
        modelManager.setSelectedPatient(ALICE);
    }

    @Test
    public void setSelectedPatient_patientInFilteredPatientList_setsSelectedPatient() {
        modelManager.addPatient(ALICE);
        assertEquals(Collections.singletonList(ALICE), modelManager.getFilteredPatientList());
        modelManager.setSelectedPatient(ALICE);
        assertEquals(ALICE, modelManager.getSelectedPatient());
    }

    @Test
    public void hasAccount_nullAccount_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasAccount(null);
    }

    @Test
    public void hasAccount_accounttNotInGiatrosBook_returnsFalse() {
        assertFalse(modelManager.hasAccount(BABA));
    }

    @Test
    public void hasAccount_accountInGiatrosBook_returnsTrue() {
        modelManager.addAccount(BABA);
        assertTrue(modelManager.hasAccount(BABA));
    }

    @Test
    public void hasAccount_accountWithSameIdentityFieldsInGiatrosBook_returnsTrue() {
        modelManager.addAccount(BABA);
        Account editedBaba = new Account(new Username(VALID_USERNAME), new Password(VALID_PASSWORD),
                new Name(VALID_NAME));
        assertTrue(modelManager.hasAccount(editedBaba));
    }

    @Test
    public void getAccount() {
        thrown.expect(AccountNotFoundException.class);
        modelManager.getAccount(MANAGER);
    }


    @Test
    public void equals() {
        GiatrosBook giatrosBook = new GiatrosBookBuilder().withPatient(ALICE).withPatient(BENSON).build();
        GiatrosBook differentGiatrosBook = new GiatrosBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(giatrosBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(giatrosBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different giatrosBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentGiatrosBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPatientList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(giatrosBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setGiatrosBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(giatrosBook, differentUserPrefs)));
    }
}
