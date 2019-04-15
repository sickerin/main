package seedu.giatros.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.giatros.commons.core.Messages.MESSAGE_COMMAND_RESTRICTED;
import static seedu.giatros.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_APPOINTMENT;

import java.util.Set;

import seedu.giatros.commons.core.index.Index;
import seedu.giatros.commons.core.session.UserSession;
import seedu.giatros.commons.exceptions.IllegalValueException;
import seedu.giatros.logic.commands.AddaptCommand;
import seedu.giatros.logic.parser.exceptions.ParseException;
import seedu.giatros.model.appointment.Appointment;

/**
 * Parses input arguments and creates a new {@code AddaptCommand} object.
 */
public class AddaptCommandParser implements Parser<AddaptCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddaptCommand
     * and returns an AddaptCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddaptCommand parse(String args) throws ParseException {
        requireNonNull(args);

        Index index;
        Set<Appointment> appointments;

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_APPOINTMENT);
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException exc) {
            if (UserSession.isAuthenticated()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddaptCommand.MESSAGE_USAGE),
                        exc);
            } else {
                throw new ParseException(String.format(MESSAGE_COMMAND_RESTRICTED, AddaptCommand.MESSAGE_USAGE), exc);
            }
        }

        // At least one appointment must be provided, so this is an invalid format
        if (!argMultimap.getValue(PREFIX_APPOINTMENT).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddaptCommand.MESSAGE_USAGE));
        }

        appointments = ParserUtil.parseAppointments(argMultimap.getAllValues(PREFIX_APPOINTMENT));

        return new AddaptCommand(index, appointments);
    }

}
