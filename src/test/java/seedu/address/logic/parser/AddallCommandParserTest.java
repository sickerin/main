package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddallCommand;

public class AddallCommandParserTest {

    private AddallCommandParser parser = new AddallCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // valid tag specified
        assertParseSuccess(parser, "1 a/ibuprofen",
                new AddallCommand(Index.fromOneBased(1), "ibuprofen"));

        // spaces in-between
        assertParseSuccess(parser, "1          a/paracetamol",
                new AddallCommand(Index.fromOneBased(1), "paracetamol"));
    }

    @Test
    public void parse_invalidFormat_failure() {
        // invalid tag specified
        assertParseFailure(parser, "1 ibuprofen", "Invalid format");
    }

}
