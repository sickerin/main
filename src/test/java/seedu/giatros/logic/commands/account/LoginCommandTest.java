package seedu.giatros.logic.commands.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.giatros.testutil.TypicalAccounts.BABA;
import static seedu.giatros.testutil.TypicalAccounts.getTypicalGiatrosBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.giatros.commons.core.session.UserSession;
import seedu.giatros.logic.CommandHistory;
import seedu.giatros.logic.commands.CommandResult;
import seedu.giatros.logic.commands.exceptions.CommandException;
import seedu.giatros.model.Model;
import seedu.giatros.model.ModelManager;
import seedu.giatros.model.UserPrefs;
import seedu.giatros.model.account.Account;
import seedu.giatros.model.account.Name;
import seedu.giatros.model.account.Password;
import seedu.giatros.model.account.Username;
import seedu.giatros.ui.testutil.AccountCreator;

public class LoginCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalGiatrosBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validAccount() throws CommandException {
        UserSession.destroy();
        Account account = new AccountCreator("manager").build();
        CommandResult result = new LoginCommand(account).execute(model, commandHistory);

        assertEquals(String.format(LoginCommand.MESSAGE_SUCCESS, account), result.getFeedbackToUser());
        assertTrue(UserSession.isAuthenticated());
        assertNotNull(UserSession.getAccount());
    }

    @Test
    public void execute_whenLoggedInAlready_throwsCommandException() throws CommandException {
        thrown.expect(CommandException.class);
        thrown.expectMessage(LoginCommand.MESSAGE_ALREADY_AUTHENTICATED);

        Account account = new AccountCreator("manager").build();
        new LoginCommand(account).execute(model, commandHistory);
        new LoginCommand(account).execute(model, commandHistory);
    }

    @Test
    public void equals() {
        LoginCommand loginCommand = new LoginCommand(BABA);

        assertFalse(loginCommand.equals(null));
        assertTrue(loginCommand.equals(loginCommand));

        LoginCommand loginCommandCopy = new LoginCommand(new Account(new Username("baba"), new Password("1122qq"),
                new Name("baba")));

        assertTrue(loginCommand.equals(loginCommandCopy));
        assertFalse(loginCommand.equals(1));
    }
}
