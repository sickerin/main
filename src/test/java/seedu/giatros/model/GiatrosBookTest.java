package seedu.giatros.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_ALLERGY_AMPICILLIN;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_NAME;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_PASSWORD;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_USERNAME;
import static seedu.giatros.testutil.TypicalAccounts.BABA;
import static seedu.giatros.testutil.TypicalAccounts.MANAGER;
import static seedu.giatros.testutil.TypicalPatients.ALICE;
import static seedu.giatros.testutil.TypicalPatients.getTypicalGiatrosBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.giatros.model.account.Account;
import seedu.giatros.model.account.Name;
import seedu.giatros.model.account.Password;
import seedu.giatros.model.account.Username;
import seedu.giatros.model.account.exceptions.AccountNotFoundException;
import seedu.giatros.model.patient.Patient;
import seedu.giatros.model.patient.exceptions.DuplicatePatientException;
import seedu.giatros.testutil.PatientBuilder;

public class GiatrosBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final GiatrosBook giatrosBook = new GiatrosBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), giatrosBook.getPatientList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        giatrosBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyGiatrosBook_replacesData() {
        GiatrosBook newData = getTypicalGiatrosBook();
        giatrosBook.resetData(newData);
        assertEquals(newData, giatrosBook);
    }

    @Test
    public void resetData_withDuplicatePatients_throwsDuplicatePatientException() {
        // Two patients with the same identity fields
        Patient editedAlice = new PatientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .withAllergies(VALID_ALLERGY_AMPICILLIN).build();
        List<Patient> newPatients = Arrays.asList(ALICE, editedAlice);
        GiatrosBookStub newData = new GiatrosBookStub(newPatients);

        thrown.expect(DuplicatePatientException.class);
        giatrosBook.resetData(newData);
    }

    @Test
    public void hasPatient_nullPatient_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        giatrosBook.hasPatient(null);
    }

    @Test
    public void hasPatient_patientNotInGiatrosBook_returnsFalse() {
        assertFalse(giatrosBook.hasPatient(ALICE));
    }

    @Test
    public void hasPatient_patientInGiatrosBook_returnsTrue() {
        giatrosBook.addPatient(ALICE);
        assertTrue(giatrosBook.hasPatient(ALICE));
    }

    @Test
    public void hasPatient_patientWithSameIdentityFieldsInGiatrosBook_returnsTrue() {
        giatrosBook.addPatient(ALICE);
        Patient editedAlice = new PatientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .withAllergies(VALID_ALLERGY_AMPICILLIN).build();
        assertTrue(giatrosBook.hasPatient(editedAlice));
    }

    @Test
    public void getPatientList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        giatrosBook.getPatientList().remove(0);
    }

    @Test
    public void hasAccount_nullAccount_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        giatrosBook.hasAccount(null);
    }

    @Test
    public void hasAccount_accounttNotInGiatrosBook_returnsFalse() {
        assertFalse(giatrosBook.hasAccount(BABA));
    }

    @Test
    public void hasAccount_accountInGiatrosBook_returnsTrue() {
        giatrosBook.addAccount(BABA);
        assertTrue(giatrosBook.hasAccount(BABA));
    }

    @Test
    public void hasAccount_accountWithSameIdentityFieldsInGiatrosBook_returnsTrue() {
        giatrosBook.addAccount(BABA);
        Account editedBaba = new Account(new Username(VALID_USERNAME), new Password(VALID_PASSWORD),
                new Name(VALID_NAME));
        assertTrue(giatrosBook.hasAccount(editedBaba));
    }

    @Test
    public void getAccount() {
        thrown.expect(AccountNotFoundException.class);
        giatrosBook.getAccount(MANAGER);
    }


    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        giatrosBook.addListener(listener);
        giatrosBook.addPatient(ALICE);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        giatrosBook.addListener(listener);
        giatrosBook.removeListener(listener);
        giatrosBook.addPatient(ALICE);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyGiatrosBook whose patients list can violate interface constraints.
     */
    private static class GiatrosBookStub implements ReadOnlyGiatrosBook {
        private final ObservableList<Patient> patients = FXCollections.observableArrayList();
        private final ObservableList<Account> accounts = FXCollections.observableArrayList();

        GiatrosBookStub(Collection<Patient> patients) {
            this.patients.setAll(patients);
            //this.accounts.setAll(accounts);
        }

        @Override
        public ObservableList<Patient> getPatientList() {
            return patients;
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Account> getAccountList() {
            return accounts;
        }
    }

}
