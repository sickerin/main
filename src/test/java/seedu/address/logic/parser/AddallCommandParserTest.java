package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

public class AddallCommandParserTest {

    private AddallCommandParser parser = new AddallCommandParser();

    @Test
    public void parse_invalidFormat_failure() {
        // invalid tag specified
        assertParseFailure(parser, "1 ibuprofen", "Invalid format");
    }

}
