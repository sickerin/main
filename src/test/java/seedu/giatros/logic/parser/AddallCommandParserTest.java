package seedu.giatros.logic.parser;

import static seedu.giatros.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.giatros.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.giatros.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.giatros.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.giatros.commons.core.index.Index;
import seedu.giatros.logic.commands.AddallCommand;
import seedu.giatros.model.person.Allergy;

public class AddallCommandParserTest {

    private AddallCommandParser parser = new AddallCommandParser();
    private final String nonEmptyAllergy = "One allergy, and some other allergy";

    @Test
    public void parse_allFieldsPresent_success() {
        // adding non-empty allergy
        Index index = INDEX_FIRST_PERSON;
        String input = index.getOneBased() + " " + PREFIX_ALLERGY + nonEmptyAllergy;
        assertParseSuccess(parser, input, new AddallCommand(INDEX_FIRST_PERSON, new Allergy(nonEmptyAllergy)));

        // adding an empty allergy
        input = index.getOneBased() + " " + PREFIX_ALLERGY + "";
        assertParseSuccess(parser, input, new AddallCommand(INDEX_FIRST_PERSON, new Allergy("")));
    }

    @Test
    public void parse_invalidFormat_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddallCommand.MESSAGE_USAGE);

        // no parameters provided
        assertParseFailure(parser, "", expectedMessage);

        // no index provided
        String input = PREFIX_ALLERGY + " " + nonEmptyAllergy;
        assertParseFailure(parser, input, expectedMessage);

        // no tag provided
        input = INDEX_FIRST_PERSON.getOneBased() + " " + nonEmptyAllergy;
        assertParseFailure(parser, input, expectedMessage);

        // invalid tag specified
        input = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_ADDRESS + " " + nonEmptyAllergy;
        assertParseFailure(parser, input, expectedMessage);

    }

}
