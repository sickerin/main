package seedu.giatros.logic.parser;

import static seedu.giatros.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.giatros.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.giatros.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.giatros.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;

import org.junit.BeforeClass;
import org.junit.Test;

import seedu.giatros.commons.core.EventsCenter;
import seedu.giatros.commons.events.ui.accounts.LoginEvent;
import seedu.giatros.logic.commands.DeleteCommand;
import seedu.giatros.ui.testutil.AccountCreator;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @BeforeClass
    public static void setupBeforeClass() {
        EventsCenter.getInstance().post(new LoginEvent(new AccountCreator("staff").build()));
    }

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteCommand(INDEX_FIRST_PATIENT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
