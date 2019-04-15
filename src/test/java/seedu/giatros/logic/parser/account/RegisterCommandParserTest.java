package seedu.giatros.logic.parser.account;

import static seedu.giatros.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.giatros.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.giatros.logic.commands.CommandTestUtil.PREFIX_WITH_INVALID_NAME;
import static seedu.giatros.logic.commands.CommandTestUtil.PREFIX_WITH_INVALID_PASSWORD;
import static seedu.giatros.logic.commands.CommandTestUtil.PREFIX_WITH_INVALID_USERNAME;
import static seedu.giatros.logic.commands.CommandTestUtil.PREFIX_WITH_VALID_NAME;
import static seedu.giatros.logic.commands.CommandTestUtil.PREFIX_WITH_VALID_PASSWORD;
import static seedu.giatros.logic.commands.CommandTestUtil.PREFIX_WITH_VALID_USERNAME;
import static seedu.giatros.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.giatros.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.giatros.testutil.TypicalAccounts.BABA;

import org.junit.BeforeClass;
import org.junit.Test;

import seedu.giatros.commons.core.EventsCenter;
import seedu.giatros.commons.events.ui.accounts.LoginEvent;
import seedu.giatros.logic.commands.account.RegisterCommand;
import seedu.giatros.model.account.Account;
import seedu.giatros.model.account.Name;
import seedu.giatros.model.account.Password;
import seedu.giatros.model.account.Username;
import seedu.giatros.ui.testutil.AccountCreator;

public class RegisterCommandParserTest {
    private RegisterCommandParser parser = new RegisterCommandParser();

    @BeforeClass
    public static void setupBeforeClass() {
        EventsCenter.getInstance().post(new LoginEvent(new AccountCreator("manager").build()));
    }

    @Test
    public void parse_allFieldsPresent_success() {
        Account expectedStaff = BABA;

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PREFIX_WITH_VALID_USERNAME
                + PREFIX_WITH_VALID_PASSWORD + PREFIX_WITH_VALID_NAME, new RegisterCommand(expectedStaff));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RegisterCommand.MESSAGE_USAGE);

        // missing username prefix
        assertParseFailure(parser, PREFIX_WITH_VALID_PASSWORD + PREFIX_WITH_VALID_NAME, expectedMessage);

        // missing password prefix
        assertParseFailure(parser, PREFIX_WITH_VALID_USERNAME + PREFIX_WITH_VALID_NAME, expectedMessage);

        // missing name prefix
        assertParseFailure(parser, PREFIX_WITH_VALID_USERNAME + PREFIX_WITH_VALID_PASSWORD, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid username
        assertParseFailure(parser, PREFIX_WITH_INVALID_USERNAME + PREFIX_WITH_VALID_PASSWORD
                + PREFIX_WITH_VALID_NAME, Username.MESSAGE_USERNAME_CONSTRAINT);

        // invalid password
        assertParseFailure(parser, PREFIX_WITH_VALID_USERNAME + PREFIX_WITH_INVALID_PASSWORD
                + PREFIX_WITH_VALID_NAME, Password.MESSAGE_PASSWORD_CONSTRAINT);

        // invalid name
        assertParseFailure(parser, PREFIX_WITH_VALID_USERNAME + PREFIX_WITH_VALID_PASSWORD
                + PREFIX_WITH_INVALID_NAME, Name.MESSAGE_CONSTRAINTS);
    }
}
