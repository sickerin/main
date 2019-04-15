package seedu.giatros.logic.commands.account;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.giatros.logic.CommandHistory;
import seedu.giatros.logic.commands.CommandResult;
import seedu.giatros.logic.commands.exceptions.CommandException;
import seedu.giatros.model.Model;
import seedu.giatros.model.ModelManager;
import seedu.giatros.model.account.Account;
import seedu.giatros.model.account.Name;
import seedu.giatros.model.account.Password;
import seedu.giatros.model.account.Username;

public class RegisterCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager();

    @Test
    public void execute() throws CommandException {
        thrown.expect(CommandException.class);
        thrown.expectMessage(RegisterCommand.MESSAGE_RESTRICTED_USERNAME);

        assertNotNull(model);

        Account account = new Account(new Username("alices"), new Password("112233"), new Name("alices"));
        CommandResult commandResult = new RegisterCommand(account).execute(model, commandHistory);

        assertEquals(String.format(RegisterCommand.MESSAGE_SUCCESS, account), commandResult.getFeedbackToUser());
        Assert.assertEquals(new CommandHistory(), commandHistory);

        Account invalidAccount = new Account(new Username("MANAGER1"), new Password("1122qq"), new Name("MANAGER1"));
        commandResult = new RegisterCommand(invalidAccount).execute(model, commandHistory);

        assertEquals(String.format(RegisterCommand.MESSAGE_RESTRICTED_USERNAME, invalidAccount),
                commandResult.getFeedbackToUser());
        Assert.assertEquals(new CommandHistory(), commandHistory);
    }

    @Test
    public void equals() {
        Account ababa = new Account(new Username("ababa"), new Password("112233"), new Name("ababa"));
        Account bcbcbc = new Account(new Username("bcbcbc"), new Password("112233"), new Name("bcbcbc"));
        RegisterCommand addAbabaCommand = new RegisterCommand(ababa);
        RegisterCommand addBcbcbcCommand = new RegisterCommand(bcbcbc);

        // same object -> returns true
        assertTrue(addAbabaCommand.equals(addAbabaCommand));

        // same values -> returns true
        RegisterCommand addAbabaCommandCopy = new RegisterCommand(ababa);
        assertTrue(addAbabaCommand.equals(addAbabaCommandCopy));

        // different types -> returns false
        assertFalse(addAbabaCommand.equals(1));

        // null -> returns false
        assertFalse(addAbabaCommand.equals(null));

        // different item -> returns false
        assertFalse(addAbabaCommand.equals(addBcbcbcCommand));
    }
}
