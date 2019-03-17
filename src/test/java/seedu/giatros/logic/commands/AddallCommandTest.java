package seedu.giatros.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.giatros.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.giatros.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.giatros.commons.core.GuiSettings;
import seedu.giatros.logic.CommandHistory;
import seedu.giatros.logic.commands.exceptions.CommandException;
import seedu.giatros.model.Model;
import seedu.giatros.model.ReadOnlyGiatrosBook;
import seedu.giatros.model.ReadOnlyUserPrefs;
import seedu.giatros.model.person.Person;

public class AddallCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_throwsCommandException() throws Exception {
        AddallCommand addallCommand = new AddallCommand(INDEX_FIRST_PERSON, "ibuprofen");
        Model modelStub = new ModelStub();

        thrown.expect(CommandException.class);
        thrown.expectMessage("ibuprofen");
        addallCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        final AddallCommand standardCommand = new AddallCommand(INDEX_FIRST_PERSON, "ibuprofen");

        // same values -> returns true
        String copyAllergyString = "ibuprofen";
        AddallCommand commandWithSameValues = new AddallCommand(INDEX_FIRST_PERSON, copyAllergyString);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddallCommand(INDEX_SECOND_PERSON, copyAllergyString)));

        // different descriptor -> returns false
        String differentAllergyString = "paracetamol";
        assertFalse(standardCommand.equals(new AddallCommand(INDEX_FIRST_PERSON, differentAllergyString)));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getGiatrosBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGiatrosBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGiatrosBook(ReadOnlyGiatrosBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyGiatrosBook getGiatrosBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoGiatrosBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoGiatrosBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoGiatrosBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoGiatrosBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitGiatrosBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Person> selectedPersonProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Person getSelectedPerson() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }
    }

}
