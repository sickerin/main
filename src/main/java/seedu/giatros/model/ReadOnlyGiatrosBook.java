package seedu.giatros.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.giatros.model.account.Account;
import seedu.giatros.model.patient.Patient;

/**
 * Unmodifiable view of an Giatros book
 */
public interface ReadOnlyGiatrosBook extends Observable {

    /**
     * Returns an unmodifiable view of the patients list.
     * This list will not contain any duplicate patients.
     */
    ObservableList<Patient> getPatientList();

    /**
     * Returns an unmodifiable view of the accounts list.
     * This list will not contain any duplicate accounts.
     */
    ObservableList<Account> getAccountList();

}
