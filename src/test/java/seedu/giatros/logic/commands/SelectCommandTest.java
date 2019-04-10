package seedu.giatros.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.giatros.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.giatros.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.giatros.logic.commands.CommandTestUtil.showPatientAtIndex;
import static seedu.giatros.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static seedu.giatros.testutil.TypicalIndexes.INDEX_SECOND_PATIENT;
import static seedu.giatros.testutil.TypicalIndexes.INDEX_THIRD_PATIENT;
import static seedu.giatros.testutil.TypicalPatients.getTypicalGiatrosBook;

import org.junit.Test;

import seedu.giatros.commons.core.Messages;
import seedu.giatros.commons.core.index.Index;
import seedu.giatros.logic.CommandHistory;
import seedu.giatros.model.Model;
import seedu.giatros.model.ModelManager;
import seedu.giatros.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class SelectCommandTest {
    private Model model = new ModelManager(getTypicalGiatrosBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalGiatrosBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPatientIndex = Index.fromOneBased(model.getFilteredPatientList().size());

        assertExecutionSuccess(INDEX_FIRST_PATIENT);
        assertExecutionSuccess(INDEX_THIRD_PATIENT);
        assertExecutionSuccess(lastPatientIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPatientList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPatientAtIndex(model, INDEX_FIRST_PATIENT);
        showPatientAtIndex(expectedModel, INDEX_FIRST_PATIENT);

        assertExecutionSuccess(INDEX_FIRST_PATIENT);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showPatientAtIndex(model, INDEX_FIRST_PATIENT);
        showPatientAtIndex(expectedModel, INDEX_FIRST_PATIENT);

        Index outOfBoundsIndex = INDEX_SECOND_PATIENT;
        // ensures that outOfBoundIndex is still in bounds of Giatros book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getGiatrosBook().getPatientList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST_PATIENT);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_PATIENT);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST_PATIENT);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different patient -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index},
     * and checks that the model's selected patient is set to the patient at {@code index} in the filtered patient list.
     */
    private void assertExecutionSuccess(Index index) {
        SelectCommand selectCommand = new SelectCommand(index);
        String expectedMessage = String.format(SelectCommand.MESSAGE_SELECT_PATIENT_SUCCESS, index.getOneBased());
        expectedModel.setSelectedPatient(model.getFilteredPatientList().get(index.getZeroBased()));

        assertCommandSuccess(selectCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectCommand selectCommand = new SelectCommand(index);
        assertCommandFailure(selectCommand, model, commandHistory, expectedMessage);
    }
}
