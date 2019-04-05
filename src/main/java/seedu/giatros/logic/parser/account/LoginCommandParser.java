package seedu.giatros.logic.parser.account;

import static seedu.giatros.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.giatros.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.giatros.logic.commands.account.LoginCommand;
import seedu.giatros.logic.parser.ArgumentMultimap;
import seedu.giatros.logic.parser.ArgumentTokenizer;
import seedu.giatros.logic.parser.Parser;
import seedu.giatros.logic.parser.exceptions.ParseException;
import seedu.giatros.model.account.Account;
import seedu.giatros.model.account.Password;
import seedu.giatros.model.account.Username;

/**
 * Parses input arguments and creates a new {@code LoginCommand} object.
 */
public class LoginCommandParser implements Parser<LoginCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LoginCommand and returns a LoginCommand object
     * for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoginCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ID, PREFIX_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_ID, PREFIX_PASSWORD) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        }

        Username username = AccountParserUtil.parseUsername(argMultimap.getValue(PREFIX_ID).get());
        Password password = AccountParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD).get());

        Account account = new Account(username, password);

        return new LoginCommand(account);
    }
}
