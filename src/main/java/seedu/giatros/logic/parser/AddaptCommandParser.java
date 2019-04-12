package seedu.giatros.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.giatros.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
// import static seedu.giatros.logic.parser.CliSyntax.PREFIX_APPOINTMENT;

import java.util.Set;

import seedu.giatros.commons.core.index.Index;
import seedu.giatros.commons.exceptions.IllegalValueException;
import seedu.giatros.logic.commands.AddaptCommand;
import seedu.giatros.logic.parser.exceptions.ParseException;
import seedu.giatros.model.appointment.Appointment;

public class AddaptCommandParser implements Parser<AddaptCommand> { 
    
    public AddaptCommand parse(Strings args) throws ParseException {
        requireNonNull(args);
        Index index;
        Set<Appointment> appointments;
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_APPOINTMENT);
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException exc) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddaptCommand.MESSAGE_USAGE), exc);
        }

        if (!argMultimap.getValue(PREFIX_APPOINTMENT).isPresent()) {
            throw new ParseException(AddaptCommand.MESSAGE_INCORRECT_APPOINTMENT);
        }

        // TODO make parse appointment
        aptergies = ParserUtil.parseAppointment(argMultimap.getAllValues(PREFIX_APPOINTMENT));

        return new AddaptCommand(index, appointments);
    }

}