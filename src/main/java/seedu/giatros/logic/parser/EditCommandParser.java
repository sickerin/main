package seedu.giatros.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.giatros.commons.core.Messages.MESSAGE_COMMAND_RESTRICTED;
import static seedu.giatros.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.giatros.commons.core.index.Index;
import seedu.giatros.commons.core.session.UserSession;
import seedu.giatros.logic.commands.EditCommand;
import seedu.giatros.logic.commands.EditCommand.EditPatientDescriptor;
import seedu.giatros.logic.parser.exceptions.ParseException;
import seedu.giatros.model.allergy.Allergy;
import seedu.giatros.model.appointment.Appointment;

/**
 * Parses input arguments and creates a new EditCommand object.
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_ALLERGY, PREFIX_APPOINTMENT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            if (UserSession.isAuthenticated()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
            } else {
                throw new ParseException(String.format(MESSAGE_COMMAND_RESTRICTED, EditCommand.MESSAGE_USAGE), pe);
            }
        }

        EditPatientDescriptor editPatientDescriptor = new EditPatientDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPatientDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPatientDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPatientDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editPatientDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        parseAllergiesForEdit(argMultimap.getAllValues(PREFIX_ALLERGY))
                .ifPresent(editPatientDescriptor::setAllergies);
        parseAppointmentsForEdit(argMultimap.getAllValues(PREFIX_APPOINTMENT))
                .ifPresent(editPatientDescriptor::setAppointments);

        if (!editPatientDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPatientDescriptor);
    }

    /**
     * Parses {@code Collection<String> allergies} into a {@code Set<Allergy>} if {@code allergies} is non-empty.
     * If {@code allergies} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Allergy>} containing zero allergies.
     */
    private Optional<Set<Allergy>> parseAllergiesForEdit(Collection<String> allergies) throws ParseException {
        assert allergies != null;

        if (allergies.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> allergySet =
                allergies.size() == 1 && allergies.contains("") ? Collections.emptySet() : allergies;
        return Optional.of(ParserUtil.parseAllergies(allergySet));
    }

    /**
     * Parses {@code Collection<String> appointments} into a {@code Set<Appointment>}
     * if {@code appointments} is non-empty.
     * If {@code appointments} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Appointment>} containing zero appointments.
     */
    private Optional<Set<Appointment>> parseAppointmentsForEdit(Collection<String> appointments) throws ParseException {
        assert appointments != null;

        if (appointments.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> appointmentSet =
                appointments.size() == 1 && appointments.contains("") ? Collections.emptySet() : appointments;
        return Optional.of(ParserUtil.parseAppointments(appointmentSet));
    }

}
