package seedu.giatros.logic.commands;

import static seedu.giatros.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.giatros.testutil.TypicalPatients.getTypicalGiatrosBook;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.giatros.logic.CommandHistory;
import seedu.giatros.logic.commands.exceptions.CommandException;
import seedu.giatros.model.Model;
import seedu.giatros.model.ModelManager;
import seedu.giatros.model.UserPrefs;

public class ExportCommandTest {

    private Model model = new ModelManager(getTypicalGiatrosBook(), new UserPrefs());

    private ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    public ExpectedException getThrown() {
        return this.thrown;
    }

    @Test
    public void execute_nonemptyGiatrosBookDefault_success() {
        ExportCommand exportCommand = new ExportCommand();
        ModelManager expectedModel = new ModelManager(model.getGiatrosBook(), new UserPrefs());
        assertCommandSuccess(exportCommand, model, commandHistory, ExportCommand.MESSAGE_SUCCESS
                + ExportCommand.getCurLocation(), expectedModel);

    }

    @Test
    public void execute_nonemptyGiatrosBookCustom_success() {
        Path validPath = Paths.get("src", "test", "data", "sandbox");
        ExportCommand exportCommand = new ExportCommand(validPath.toString());
        ModelManager expectedModel = new ModelManager(model.getGiatrosBook(), new UserPrefs());
        assertCommandSuccess(exportCommand, model, commandHistory, ExportCommand.MESSAGE_SUCCESS
                + ExportCommand.getCurLocation(), expectedModel);

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
