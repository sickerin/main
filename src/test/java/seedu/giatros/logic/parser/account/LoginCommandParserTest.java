package seedu.giatros.logic.parser.account;

import static seedu.giatros.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.giatros.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.giatros.logic.commands.CommandTestUtil.PREFIX_WITH_INVALID_PASSWORD;
import static seedu.giatros.logic.commands.CommandTestUtil.PREFIX_WITH_INVALID_USERNAME;
import static seedu.giatros.logic.commands.CommandTestUtil.PREFIX_WITH_VALID_PASSWORD;
import static seedu.giatros.logic.commands.CommandTestUtil.PREFIX_WITH_VALID_USERNAME;
import static seedu.giatros.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.giatros.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.giatros.ui.testutil.AccountCreator.DEFAULT_MANAGER_PASSWORD;
import static seedu.giatros.ui.testutil.AccountCreator.DEFAULT_MANAGER_USERNAME;

import org.junit.Before;
import org.junit.Test;

import seedu.giatros.logic.commands.account.LoginCommand;
import seedu.giatros.model.account.Account;
import seedu.giatros.model.account.Name;
import seedu.giatros.model.account.Password;
import seedu.giatros.model.account.Username;

public class LoginCommandParserTest {
    private LoginCommandParser parser = new LoginCommandParser();
    private Account expectedAccount;

    @Before
    public void setUp() {
        expectedAccount = new Account(new Username("baba"), new Password("1122qq"), new Name("baba"));
    }

    @Test
    public void parse_allPresent_success() {
        assertParseSuccess(parser, PREFIX_WITH_VALID_USERNAME + PREFIX_WITH_VALID_PASSWORD,
                new LoginCommand(expectedAccount));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE);

        assertParseFailure(parser, PREFIX_WITH_VALID_PASSWORD, expectedMessage);
        assertParseFailure(parser, PREFIX_WITH_VALID_USERNAME, expectedMessage);
        assertParseFailure(parser, "", expectedMessage);

        // missing 1 prefix


        assertParseFailure(parser, PREFIX_WITH_VALID_USERNAME + DEFAULT_MANAGER_PASSWORD, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, DEFAULT_MANAGER_USERNAME + DEFAULT_MANAGER_PASSWORD, expectedMessage);
    }


    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, PREFIX_WITH_INVALID_USERNAME + PREFIX_WITH_VALID_PASSWORD,
                Username.MESSAGE_USERNAME_CONSTRAINT);
        assertParseFailure(parser, PREFIX_WITH_VALID_USERNAME + PREFIX_WITH_INVALID_PASSWORD,
                Password.MESSAGE_PASSWORD_CONSTRAINT);
        assertParseFailure(parser, PREFIX_WITH_INVALID_USERNAME + PREFIX_WITH_INVALID_PASSWORD,
                Username.MESSAGE_USERNAME_CONSTRAINT);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + PREFIX_WITH_VALID_USERNAME
                + PREFIX_WITH_VALID_PASSWORD, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                LoginCommand.MESSAGE_USAGE));
    }
}
