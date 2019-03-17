package seedu.giatros.logic;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.giatros.commons.core.GuiSettings;
import seedu.giatros.logic.commands.CommandResult;
import seedu.giatros.logic.commands.exceptions.CommandException;
import seedu.giatros.logic.parser.exceptions.ParseException;
import seedu.giatros.model.ReadOnlyGiatrosBook;
import seedu.giatros.model.person.Person;

/**
 * API of the Logic component
 */
public interface Logic {
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

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

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
     * Selected person in the filtered person list.
     * null if no person is selected.
     *
     * @see seedu.giatros.model.Model#selectedPersonProperty()
     */
    ReadOnlyProperty<Person> selectedPersonProperty();

    /**
     * Sets the selected person in the filtered person list.
     *
     * @see seedu.giatros.model.Model#setSelectedPerson(Person)
     */
    void setSelectedPerson(Person person);
}
