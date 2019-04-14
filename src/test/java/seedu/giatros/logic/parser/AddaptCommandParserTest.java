package seedu.giatros.logic.parser;

import static seedu.giatros.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.giatros.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.giatros.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.giatros.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;

import org.junit.Test;

import seedu.giatros.commons.core.index.Index;
import seedu.giatros.logic.commands.AddaptCommand;
import seedu.giatros.model.appointment.Appointment;

public class AddaptCommandParserTest {

    private AddaptCommandParser parser = new AddaptCommandParser();
    private final String nonEmptyAppointment = "2019-01-01 10:00";

    @Test
    public void parse_allFieldsPresent_success() {
        // adding non-empty appointment
        Index index = INDEX_FIRST_PATIENT;
        String input = index.getOneBased() + " " + PREFIX_APPOINTMENT + nonEmptyAppointment;
        assertParseSuccess(parser, input, new AddaptCommand(INDEX_FIRST_PATIENT, new Appointment(nonEmptyAppointment)));
    }

    @Test
    public void parse_invalidFormat_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddaptCommand.MESSAGE_USAGE);

        // no parameters provided
        assertParseFailure(parser, "", expectedMessage);

        // no index provided
        String input = PREFIX_APPOINTMENT + " " + nonEmptyAppointment;
        assertParseFailure(parser, input, expectedMessage);

        // no prefix provided
        input = INDEX_FIRST_PATIENT.getOneBased() + " " + nonEmptyAppointment;
        assertParseFailure(parser, input, expectedMessage);

        // invalid prefix specified
        input = INDEX_FIRST_PATIENT.getOneBased() + " " + PREFIX_ADDRESS + " " + nonEmptyAppointment;
        assertParseFailure(parser, input, expectedMessage);
    }
}
