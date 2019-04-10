package seedu.giatros.logic.commands;

import static seedu.giatros.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.giatros.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.giatros.logic.commands.CommandTestUtil.deleteFirstPatient;
import static seedu.giatros.testutil.TypicalPatients.getTypicalGiatrosBook;

import org.junit.Before;
import org.junit.Test;

import seedu.giatros.logic.CommandHistory;
import seedu.giatros.model.Model;
import seedu.giatros.model.ModelManager;
import seedu.giatros.model.UserPrefs;

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalGiatrosBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalGiatrosBook(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstPatient(model);
        deleteFirstPatient(model);
        model.undoGiatrosBook();
        model.undoGiatrosBook();

        deleteFirstPatient(expectedModel);
        deleteFirstPatient(expectedModel);
        expectedModel.undoGiatrosBook();
        expectedModel.undoGiatrosBook();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoGiatrosBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redoGiatrosBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
}
