package seedu.giatros.logic.commands;

import static seedu.giatros.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.giatros.logic.CommandHistory;
import seedu.giatros.logic.commands.exceptions.CommandException;

import seedu.giatros.model.Model;
import seedu.giatros.model.ModelManager;

public class ExportCommandTest {

    private ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    public ExpectedException getThrown() {
        return this.thrown;
    }

    @Test
    public void execute_nonemptyGiatrosBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitGiatrosBook();

        assertCommandSuccess(new ExportCommand(), model, commandHistory, ExportCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_invalidDestination_throwsCommandException() {
        ExportCommand exportCommand = new ExportCommand("");
        Model expectedModel = new ModelManager();
        expectedModel.commitGiatrosBook();
        getThrown().expect(CommandException.class);
        getThrown().expectMessage(ExportCommand.MESSAGE_CSV_FAIL);
        exportCommand.execute(expectedModel, commandHistory);
    }

}
