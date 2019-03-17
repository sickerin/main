package seedu.giatros.model;

import static java.util.Objects.requireNonNull;
import static seedu.giatros.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.giatros.commons.core.GuiSettings;
import seedu.giatros.commons.core.LogsCenter;
import seedu.giatros.model.person.Person;
import seedu.giatros.model.person.exceptions.PersonNotFoundException;

/**
 * Represents the in-memory model of the giatros book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedGiatrosBook versionedGiatrosBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final SimpleObjectProperty<Person> selectedPerson = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given giatrosBook and userPrefs.
     */
    public ModelManager(ReadOnlyGiatrosBook giatrosBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(giatrosBook, userPrefs);

        logger.fine("Initializing with giatros book: " + giatrosBook + " and user prefs " + userPrefs);

        versionedGiatrosBook = new VersionedGiatrosBook(giatrosBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(versionedGiatrosBook.getPersonList());
        filteredPersons.addListener(this::ensureSelectedPersonIsValid);
    }

    public ModelManager() {
        this(new GiatrosBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getGiatrosBookFilePath() {
        return userPrefs.getGiatrosBookFilePath();
    }

    @Override
    public void setGiatrosBookFilePath(Path giatrosBookFilePath) {
        requireNonNull(giatrosBookFilePath);
        userPrefs.setGiatrosBookFilePath(giatrosBookFilePath);
    }

    //=========== GiatrosBook ================================================================================

    @Override
    public void setGiatrosBook(ReadOnlyGiatrosBook giatrosBook) {
        versionedGiatrosBook.resetData(giatrosBook);
    }

    @Override
    public ReadOnlyGiatrosBook getGiatrosBook() {
        return versionedGiatrosBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedGiatrosBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        versionedGiatrosBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        versionedGiatrosBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedGiatrosBook.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedGiatrosBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoGiatrosBook() {
        return versionedGiatrosBook.canUndo();
    }

    @Override
    public boolean canRedoGiatrosBook() {
        return versionedGiatrosBook.canRedo();
    }

    @Override
    public void undoGiatrosBook() {
        versionedGiatrosBook.undo();
    }

    @Override
    public void redoGiatrosBook() {
        versionedGiatrosBook.redo();
    }

    @Override
    public void commitGiatrosBook() {
        versionedGiatrosBook.commit();
    }

    //=========== Selected person ===========================================================================

    @Override
    public ReadOnlyProperty<Person> selectedPersonProperty() {
        return selectedPerson;
    }

    @Override
    public Person getSelectedPerson() {
        return selectedPerson.getValue();
    }

    @Override
    public void setSelectedPerson(Person person) {
        if (person != null && !filteredPersons.contains(person)) {
            throw new PersonNotFoundException();
        }
        selectedPerson.setValue(person);
    }

    /**
     * Ensures {@code selectedPerson} is a valid person in {@code filteredPersons}.
     */
    private void ensureSelectedPersonIsValid(ListChangeListener.Change<? extends Person> change) {
        while (change.next()) {
            if (selectedPerson.getValue() == null) {
                // null is always a valid selected person, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedPersonReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedPerson.getValue());
            if (wasSelectedPersonReplaced) {
                // Update selectedPerson to its new value.
                int index = change.getRemoved().indexOf(selectedPerson.getValue());
                selectedPerson.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedPersonRemoved = change.getRemoved().stream()
                    .anyMatch(removedPerson -> selectedPerson.getValue().isSamePerson(removedPerson));
            if (wasSelectedPersonRemoved) {
                // Select the person that came before it in the list,
                // or clear the selection if there is no such person.
                selectedPerson.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedGiatrosBook.equals(other.versionedGiatrosBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons)
                && Objects.equals(selectedPerson.get(), other.selectedPerson.get());
    }

}
