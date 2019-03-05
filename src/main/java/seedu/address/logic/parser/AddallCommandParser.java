package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddallCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddallCommand object
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

        // Implement a simple parser locally first
        String delims = "[ ]+";
        String[] tokens = args.trim().split(delims);

        index = Index.fromOneBased(Integer.valueOf(tokens[0])); // The first argument is the index
        allergyString = tokens[1]; // The second argument is the allergyString
        allergyString = allergyString.substring(2); // Trim off the tag a/ in the beginning

        return new AddallCommand(index, allergyString);
    }

}
