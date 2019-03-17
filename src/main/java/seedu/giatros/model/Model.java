package seedu.giatros.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.giatros.commons.core.GuiSettings;
import seedu.giatros.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' Giatros book file path.
     */
    Path getGiatrosBookFilePath();

    /**
     * Sets the user prefs' Giatros book file path.
     */
    void setGiatrosBookFilePath(Path giatrosBookFilePath);

    /**
     * Replaces Giatros book data with the data in {@code giatrosBook}.
     */
    void setGiatrosBook(ReadOnlyGiatrosBook giatrosBook);

    /** Returns the GiatrosBook */
    ReadOnlyGiatrosBook getGiatrosBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the Giatros book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the Giatros book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the Giatros book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the Giatros book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the Giatros book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Returns true if the model has previous Giatros book states to restore.
     */
    boolean canUndoGiatrosBook();

    /**
     * Returns true if the model has undone Giatros book states to restore.
     */
    boolean canRedoGiatrosBook();

    /**
     * Restores the model's Giatros book to its previous state.
     */
    void undoGiatrosBook();

    /**
     * Restores the model's Giatros book to its previously undone state.
     */
    void redoGiatrosBook();

    /**
     * Saves the current Giatros book state for undo/redo.
     */
    void commitGiatrosBook();

    /**
     * Selected person in the filtered person list.
     * null if no person is selected.
     */
    ReadOnlyProperty<Person> selectedPersonProperty();

    /**
     * Returns the selected person in the filtered person list.
     * null if no person is selected.
     */
    Person getSelectedPerson();

    /**
     * Sets the selected person in the filtered person list.
     */
    void setSelectedPerson(Person person);
}
