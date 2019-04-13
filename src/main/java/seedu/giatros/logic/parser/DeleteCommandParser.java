package seedu.giatros.logic.parser;

import static seedu.giatros.commons.core.Messages.MESSAGE_COMMAND_RESTRICTED;
import static seedu.giatros.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.giatros.commons.core.index.Index;
import seedu.giatros.commons.core.session.UserSession;
import seedu.giatros.logic.commands.DeleteCommand;
import seedu.giatros.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCommand(index);
        } catch (ParseException pe) {
            if (UserSession.isAuthenticated()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE),
                        pe);
            } else {
                throw new ParseException(String.format(MESSAGE_COMMAND_RESTRICTED, DeleteCommand.MESSAGE_USAGE), pe);
            }
        }
    }

}
