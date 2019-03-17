package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddallCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code AddallCommand} object
 */
public class AddallCommandParser implements Parser<AddallCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddallCommand
     * and returns an AddallCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddallCommand parse(String args) throws ParseException {
        requireNonNull(args);

        Index index;
        String allergyString;

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ALLERGY);
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException exc) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddallCommand.MESSAGE_USAGE), exc);
        }

        allergyString = argMultimap.getValue(PREFIX_ALLERGY).orElse("");

        return new AddallCommand(index, allergyString);
    }

}
