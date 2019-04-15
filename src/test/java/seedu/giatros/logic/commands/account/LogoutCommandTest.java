package seedu.giatros.logic.commands.account;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.giatros.testutil.TypicalAccounts.MANAGER;
import static seedu.giatros.testutil.TypicalAccounts.getTypicalGiatrosBook;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import seedu.giatros.commons.core.EventsCenter;
import seedu.giatros.commons.core.session.UserSession;
import seedu.giatros.commons.events.ui.accounts.LoginEvent;
import seedu.giatros.logic.CommandHistory;
import seedu.giatros.logic.commands.Command;
import seedu.giatros.logic.commands.exceptions.CommandException;
import seedu.giatros.model.Model;
import seedu.giatros.model.ModelManager;
import seedu.giatros.model.UserPrefs;

public class LogoutCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalGiatrosBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_withoutAnyExistingSession_failure() throws CommandException {
        UserSession.destroy();
        thrown.expect(CommandException.class);
        thrown.expectMessage(LogoutCommand.MESSAGE_NOT_AUTHENTICATED);

        Command logoutCommand = new LogoutCommand();
        logoutCommand.execute(model, commandHistory);
    }

    @Test
    public void logout_clearVersionedRestaurantBook_loginStateReset() throws CommandException {
        EventsCenter.getInstance().post(new LoginEvent(MANAGER));
        model.commitGiatrosBook();

        Assert.assertTrue(model.canUndoGiatrosBook());
        model.undoGiatrosBook();
        assertFalse(model.canUndoGiatrosBook());

        Assert.assertTrue(model.canRedoGiatrosBook());
        model.redoGiatrosBook();
        assertFalse(model.canRedoGiatrosBook());

        new LogoutCommand().execute(model, commandHistory);
        EventsCenter.getInstance().post(new LoginEvent(MANAGER));
        assertFalse(model.canUndoGiatrosBook());
        assertFalse(model.canRedoGiatrosBook());

        model.resetGiatrosBookVersion();
        assertEquals(FXCollections.emptyObservableList(), model.getGiatrosBook().getPatientList());
    }
}
