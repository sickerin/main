package seedu.giatros.logic;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.giatros.commons.core.GuiSettings;
import seedu.giatros.logic.commands.Command;
import seedu.giatros.logic.commands.CommandResult;
import seedu.giatros.logic.commands.exceptions.CommandException;
import seedu.giatros.logic.parser.exceptions.ParseException;
import seedu.giatros.model.ReadOnlyGiatrosBook;
import seedu.giatros.model.account.Account;
import seedu.giatros.model.patient.Patient;

/**
 * API of the Logic component
 */
public interface Logic {

    /**
     * Verifies if a {@code Command} is a guest command which can be executed without being authenticated.
     */
    public boolean isGuestCommand(Command command);

    /**
     * Verifies if a {@code Command} is a staff command which can executed without proper head staff account.
     */
    public boolean isStaffCommand(Command command);

    /**
     * Verifies if a {@code Command} is a manager command which can executed without proper manager account.
     */
    public boolean isManagerCommand(Command command);

    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the GiatrosBook.
     *
     * @see seedu.giatros.model.Model#getGiatrosBook()
     */
    ReadOnlyGiatrosBook getGiatrosBook();

    /** Returns an unmodifiable view of the filtered list of patients */
    ObservableList<Patient> getFilteredPatientList();

    /** Returns an unmodifiable view of the filtered list of patients */
    ObservableList<Account> getFilteredAccountList();

    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();

    /**
     * Returns the user prefs' Giatros book file path.
     */
    Path getGiatrosBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Selected patient in the filtered patient list.
     * null if no patient is selected.
     *
     * @see seedu.giatros.model.Model#selectedPatientProperty()
     */
    ReadOnlyProperty<Patient> selectedPatientProperty();

    /**
     * Sets the selected patient in the filtered patient list.
     *
     * @see seedu.giatros.model.Model#setSelectedPatient(Patient)
     */
    void setSelectedPatient(Patient patient);
}
