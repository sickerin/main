package systemtests;

import static seedu.giatros.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.giatros.model.Model.PREDICATE_SHOW_ALL_PATIENTS;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.Test;

import seedu.giatros.commons.core.Messages;
import seedu.giatros.logic.commands.ExportCommand;
import seedu.giatros.model.Model;

public class ExportCommandSystemTest extends GiatrosBookSystemTest {

    private static final String MESSAGE_INVALID_EXPORT_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);

    private static final String MESSAGE_INVALID_EXPORT_PATH_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_CSV_FAIL);


    @Test
    public void export() {

        final Path validPath = Paths.get("src", "test", "data", "sandbox");
        Model expectedModel = getModel();

        /* ----------------- Performing export operation while an unfiltered list is being shown -------------------- */

        /* Case: export non-empty Giatros book, command with leading spaces and trailing spaces -> exported
         */
        assertCommandSuccess("   " + ExportCommand.COMMAND_WORD + "   ", expectedModel,
                ExportCommand.MESSAGE_SUCCESS);

        /* Case: export non-empty Giatros book to custom location, command with leading spaces and trailing spaces
         * -> exported
         */
        assertCommandSuccess("     " + ExportCommand.COMMAND_WORD + "      d/" + validPath + "      ",
                expectedModel, ExportCommand.MESSAGE_SUCCESS);
        assertSelectedCardUnchanged();

        /* --------------------------------- Performing invalid export operation ------------------------------------ */

        /* Case: empty destination path ("") -> rejected */
        String command = ExportCommand.COMMAND_WORD + " d/" + Optional.empty();
        assertCommandFailure(command, MESSAGE_INVALID_EXPORT_COMMAND_FORMAT);

        /* Case: invalid destination path -> rejected */
        command = ExportCommand.COMMAND_WORD + " d/" + Optional.empty();
        assertCommandFailure(command, MESSAGE_INVALID_EXPORT_PATH_FORMAT);

        /* Case: invalid arguments (extra argument) default location -> rejected */
        assertCommandFailure("export 1", MESSAGE_INVALID_EXPORT_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) custom location -> rejected */
        assertCommandFailure("export d/" + validPath + " 1298", MESSAGE_INVALID_EXPORT_COMMAND_FORMAT);

        /* Case: mixed case command word default location -> rejected */
        assertCommandFailure("ExPOrt", MESSAGE_UNKNOWN_COMMAND);

        /* Case: mixed case command word custom location -> rejected */
        assertCommandFailure("ExPOrt d/" + validPath, MESSAGE_UNKNOWN_COMMAND);

    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that no card is selected.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code GiatrosBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see GiatrosBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        expectedModel.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertSelectedCardDeselected();
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

