package seedu.giatros.logic.parser.account;

import static seedu.giatros.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.giatros.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.giatros.commons.core.session.UserSession;
import seedu.giatros.logic.commands.account.RegisterCommand;
import seedu.giatros.logic.parser.ArgumentMultimap;
import seedu.giatros.logic.parser.ArgumentTokenizer;
import seedu.giatros.logic.parser.Parser;
import seedu.giatros.logic.parser.exceptions.ParseException;

import seedu.giatros.model.account.Account;
import seedu.giatros.model.account.Name;
import seedu.giatros.model.account.Password;
import seedu.giatros.model.account.Username;


/**
 * Parses input arguments and creates a new {@code RegisterCommand} object.
 */
public class RegisterCommandParser implements Parser<RegisterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RegisterCommand and returns a RegisterCommand
     * object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RegisterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ID, PREFIX_PASSWORD, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_ID, PREFIX_PASSWORD, PREFIX_NAME) || !argMultimap.getPreamble()
                .isEmpty()) {
            if (UserSession.isAuthenticated()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RegisterCommand.MESSAGE_USAGE));
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RegisterCommand.MESSAGE_USAGE));
            }
        }

        Username username = AccountParserUtil.parseUsername(argMultimap.getValue(PREFIX_ID).get());
        Password password = AccountParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD).get());
        Name name = AccountParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());

        Account account = new Account(username, password, name);

        return new RegisterCommand(account);
    }
}
