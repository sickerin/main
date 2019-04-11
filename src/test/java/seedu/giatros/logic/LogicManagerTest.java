package seedu.giatros.logic;

import static org.junit.Assert.assertEquals;
import static seedu.giatros.commons.core.Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX;
import static seedu.giatros.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.giatros.logic.commands.CommandResult;
import seedu.giatros.logic.commands.HistoryCommand;
import seedu.giatros.logic.commands.ListCommand;
import seedu.giatros.logic.commands.exceptions.CommandException;
import seedu.giatros.logic.parser.exceptions.ParseException;
import seedu.giatros.model.Model;
import seedu.giatros.model.ModelManager;
import seedu.giatros.model.ReadOnlyGiatrosBook;
import seedu.giatros.model.UserPrefs;
import seedu.giatros.storage.JsonGiatrosBookStorage;
import seedu.giatros.storage.JsonUserPrefsStorage;
import seedu.giatros.storage.StorageManager;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private Model model = new ModelManager();
    private Logic logic;

    @Before
    public void setUp() throws Exception {
        JsonGiatrosBookStorage addressBookStorage = new JsonGiatrosBookStorage(temporaryFolder.newFile().toPath());
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.newFile().toPath());
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
        logic.setIsTest(true);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
        assertHistoryCorrect(invalidCommand);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        assertHistoryCorrect(deleteCommand);
    }

    @Test
    public void execute_validCommand_success() {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
        assertHistoryCorrect(listCommand);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() throws Exception {
        // Setup LogicManager with JsonGiatrosBookIoExceptionThrowingStub
        JsonGiatrosBookStorage addressBookStorage =
                new JsonGiatrosBookIoExceptionThrowingStub(temporaryFolder.newFile().toPath());
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.newFile().toPath());
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

    }

    @Test
    public void getFilteredPatientList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        logic.getFilteredPatientList().remove(0);
    }

    /**
     * Executes the command, confirms that no exceptions are thrown and that the result message is correct.
     * Also confirms that {@code expectedModel} is as specified.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage, Model expectedModel) {
        assertCommandBehavior(null, inputCommand, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<?> expectedException, String expectedMessage) {
        Model expectedModel = new ModelManager(model.getGiatrosBook(), new UserPrefs());
        assertCommandBehavior(expectedException, inputCommand, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that the result message is correct and that the expected exception is thrown,
     * and also confirms that the following two parts of the LogicManager object's state are as expected:<br>
     *      - the internal model manager data are same as those in the {@code expectedModel} <br>
     *      - {@code expectedModel}'s Giatros book was saved to the storage file.
     */
    private void assertCommandBehavior(Class<?> expectedException, String inputCommand,
                                           String expectedMessage, Model expectedModel) {

        try {
            CommandResult result = logic.execute(inputCommand);
            assertEquals(expectedException, null);
            assertEquals(expectedMessage, result.getFeedbackToUser());
        } catch (CommandException | ParseException e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }

        assertEquals(expectedModel, model);
    }

    /**
     * Asserts that the result display shows all the {@code expectedCommands} upon the execution of
     * {@code HistoryCommand}.
     */
    private void assertHistoryCorrect(String... expectedCommands) {
        try {
            CommandResult result = logic.execute(HistoryCommand.COMMAND_WORD);
            String expectedMessage = String.format(
                    HistoryCommand.MESSAGE_SUCCESS, String.join("\n", expectedCommands));
            assertEquals(expectedMessage, result.getFeedbackToUser());
        } catch (CommandException | ParseException e) {
            throw new AssertionError("Parsing and execution of HistoryCommand.COMMAND_WORD should succeed.", e);
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonGiatrosBookIoExceptionThrowingStub extends JsonGiatrosBookStorage {
        private JsonGiatrosBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveGiatrosBook(ReadOnlyGiatrosBook addressBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
