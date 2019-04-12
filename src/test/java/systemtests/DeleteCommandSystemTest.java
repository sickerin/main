package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.giatros.commons.core.Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX;
import static seedu.giatros.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.giatros.logic.commands.DeleteCommand.MESSAGE_DELETE_PATIENT_SUCCESS;
import static seedu.giatros.testutil.TestUtil.getLastIndex;
import static seedu.giatros.testutil.TestUtil.getMidIndex;
import static seedu.giatros.testutil.TestUtil.getPatient;
import static seedu.giatros.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static seedu.giatros.testutil.TypicalPatients.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.giatros.commons.core.Messages;
import seedu.giatros.commons.core.index.Index;
import seedu.giatros.logic.commands.DeleteCommand;
import seedu.giatros.logic.commands.RedoCommand;
import seedu.giatros.logic.commands.UndoCommand;
import seedu.giatros.model.Model;
import seedu.giatros.model.patient.Patient;

public class DeleteCommandSystemTest extends GiatrosBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first patient in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteCommand.COMMAND_WORD + "      " + INDEX_FIRST_PATIENT.getOneBased() + "      ";
        Patient deletedPatient = removePatient(expectedModel, INDEX_FIRST_PATIENT);
        String expectedResultMessage = String.format(MESSAGE_DELETE_PATIENT_SUCCESS, deletedPatient);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last patient in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastPatientIndex = getLastIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastPatientIndex);

        /* Case: undo deleting the last patient in the list -> last patient restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last patient in the list -> last patient deleted again */
        command = RedoCommand.COMMAND_WORD;
        removePatient(modelBeforeDeletingLast, lastPatientIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle patient in the list -> deleted */
        Index middlePatientIndex = getMidIndex(getModel());
        assertCommandSuccess(middlePatientIndex);

        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        /* Case: filtered patient list, delete index within bounds of Giatros book and patient list -> deleted */
        showPatientsWithName(KEYWORD_MATCHING_MEIER);
        Index index = INDEX_FIRST_PATIENT;
        assertTrue(index.getZeroBased() < getModel().getFilteredPatientList().size());
        assertCommandSuccess(index);

        /* Case: filtered patient list, delete index within bounds of Giatros book but out of bounds of patient list
         * -> rejected
         */
        showPatientsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getGiatrosBook().getPatientList().size();
        command = DeleteCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);

        /* --------------------- Performing delete operation while a patient card is selected ------------------------
        */

        /* Case: delete the selected patient -> patient list panel selects the patient before the deleted patient */
        showAllPatients();
        expectedModel = getModel();
        Index selectedIndex = getLastIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectPatient(selectedIndex);
        command = DeleteCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        deletedPatient = removePatient(expectedModel, selectedIndex);
        expectedResultMessage = String.format(MESSAGE_DELETE_PATIENT_SUCCESS, deletedPatient);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getGiatrosBook().getPatientList().size() + 1);
        command = DeleteCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Patient} at the specified {@code index} in {@code model}'s Giatros book.
     * @return the removed patient
     */
    private Patient removePatient(Model model, Index index) {
        Patient targetPatient = getPatient(model, index);
        model.deletePatient(targetPatient);
        return targetPatient;
    }

    /**
     * Deletes the patient at {@code toDelete} by creating a default {@code DeleteCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Patient deletedPatient = removePatient(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_PATIENT_SUCCESS, deletedPatient);

        assertCommandSuccess(
                DeleteCommand.COMMAND_WORD + " " + toDelete.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card remains unchanged.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code GiatrosBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see GiatrosBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see GiatrosBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code GiatrosBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see GiatrosBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
