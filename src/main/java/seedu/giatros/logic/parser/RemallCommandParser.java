package seedu.giatros.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.giatros.commons.core.Messages.MESSAGE_COMMAND_RESTRICTED;
import static seedu.giatros.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_ALLERGY;

import java.util.Set;

import seedu.giatros.commons.core.index.Index;
import seedu.giatros.commons.core.session.UserSession;
import seedu.giatros.commons.exceptions.IllegalValueException;
import seedu.giatros.logic.commands.RemallCommand;
import seedu.giatros.logic.parser.exceptions.ParseException;
import seedu.giatros.model.allergy.Allergy;

/**
 * Parses input arguments and creates a new {@code RemallCommand} object.
 */
public class RemallCommandParser implements Parser<RemallCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemallCommand
     * and returns a RemallCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemallCommand parse(String args) throws ParseException {
        requireNonNull(args);

        Index index;
        Set<Allergy> allergies;

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ALLERGY);
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException exc) {
            if (UserSession.isAuthenticated()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemallCommand.MESSAGE_USAGE),
                        exc);
            } else {
                throw new ParseException(String.format(MESSAGE_COMMAND_RESTRICTED, RemallCommand.MESSAGE_USAGE), exc);
            }
        }

        // At least one allergy must be provided, so this is invalid format
        if (!argMultimap.getValue(PREFIX_ALLERGY).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemallCommand.MESSAGE_USAGE));
        }

        allergies = ParserUtil.parseAllergies(argMultimap.getAllValues(PREFIX_ALLERGY));

        return new RemallCommand(index, allergies);
    }

}
