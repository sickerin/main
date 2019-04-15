package seedu.giatros.logic.parser;

import static seedu.giatros.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.giatros.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.giatros.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.giatros.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;

import org.junit.BeforeClass;
import org.junit.Test;

import seedu.giatros.commons.core.EventsCenter;
import seedu.giatros.commons.core.index.Index;
import seedu.giatros.commons.events.ui.accounts.LoginEvent;
import seedu.giatros.logic.commands.RemallCommand;
import seedu.giatros.model.allergy.Allergy;
import seedu.giatros.ui.testutil.AccountCreator;

public class RemallCommandParserTest {

    private RemallCommandParser parser = new RemallCommandParser();
    private final String nonEmptyAllergy = "someAllergy";

    @BeforeClass
    public static void setupBeforeClass() {
        EventsCenter.getInstance().post(new LoginEvent(new AccountCreator("staff").build()));
    }

    @Test
    public void parse_allFieldsPresent_success() {
        // adding non-empty allergy
        Index index = INDEX_FIRST_PATIENT;
        String input = index.getOneBased() + " " + PREFIX_ALLERGY + nonEmptyAllergy;
        assertParseSuccess(parser, input, new RemallCommand(INDEX_FIRST_PATIENT, new Allergy(nonEmptyAllergy)));
    }

    @Test
    public void parse_invalidFormat_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemallCommand.MESSAGE_USAGE);

        // no parameters provided
        assertParseFailure(parser, "", expectedMessage);

        // no index provided
        String input = PREFIX_ALLERGY + " " + nonEmptyAllergy;
        assertParseFailure(parser, input, expectedMessage);

        // no allergy provided
        input = INDEX_FIRST_PATIENT.getOneBased() + " ";
        assertParseFailure(parser, input, expectedMessage);

        // no prefix provided
        input = INDEX_FIRST_PATIENT.getOneBased() + " " + nonEmptyAllergy;
        assertParseFailure(parser, input, expectedMessage);

        // invalid prefix specified
        input = INDEX_FIRST_PATIENT.getOneBased() + " " + PREFIX_ADDRESS + " " + nonEmptyAllergy;
        assertParseFailure(parser, input, expectedMessage);

    }

}
