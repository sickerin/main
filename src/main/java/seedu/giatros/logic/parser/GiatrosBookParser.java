package seedu.giatros.logic.parser;

import static seedu.giatros.commons.core.Messages.MESSAGE_COMMAND_RESTRICTED;
import static seedu.giatros.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.giatros.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.giatros.commons.core.session.UserSession;
import seedu.giatros.logic.commands.AddCommand;
import seedu.giatros.logic.commands.AddallCommand;
import seedu.giatros.logic.commands.AddaptCommand;
import seedu.giatros.logic.commands.ClearCommand;
import seedu.giatros.logic.commands.Command;
import seedu.giatros.logic.commands.DeleteCommand;
import seedu.giatros.logic.commands.EditCommand;
import seedu.giatros.logic.commands.ExitCommand;
import seedu.giatros.logic.commands.ExportCommand;
import seedu.giatros.logic.commands.FindCommand;
import seedu.giatros.logic.commands.HelpCommand;
import seedu.giatros.logic.commands.HistoryCommand;
import seedu.giatros.logic.commands.ListCommand;
import seedu.giatros.logic.commands.RedoCommand;
import seedu.giatros.logic.commands.RemallCommand;
import seedu.giatros.logic.commands.RemaptCommand;
import seedu.giatros.logic.commands.SelectCommand;
import seedu.giatros.logic.commands.UndoCommand;
import seedu.giatros.logic.commands.account.LoginCommand;
import seedu.giatros.logic.commands.account.LogoutCommand;
import seedu.giatros.logic.commands.account.RegisterCommand;
import seedu.giatros.logic.parser.account.LoginCommandParser;
import seedu.giatros.logic.parser.account.RegisterCommandParser;
import seedu.giatros.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class GiatrosBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());

        if (!matcher.matches() && UserSession.isAuthenticated()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        if (!UserSession.isAuthenticated() && !(commandWord.equals("login") || commandWord.equals("help")
                || commandWord.equals("exit"))) {
            throw new ParseException(String.format(MESSAGE_COMMAND_RESTRICTED, HelpCommand.MESSAGE_USAGE));
        }

        switch (commandWord) {

        case LoginCommand.COMMAND_WORD:
            return new LoginCommandParser().parse(arguments);

        case RegisterCommand.COMMAND_WORD:
            return new RegisterCommandParser().parse(arguments);

        case LogoutCommand.COMMAND_WORD:
            return new LogoutCommand();

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case ExportCommand.COMMAND_WORD:
            return new ExportCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case AddallCommand.COMMAND_WORD:
            return new AddallCommandParser().parse(arguments);

        case RemallCommand.COMMAND_WORD:
            return new RemallCommandParser().parse(arguments);

        case AddaptCommand.COMMAND_WORD:
            return new AddaptCommandParser().parse(arguments);

        case RemaptCommand.COMMAND_WORD:
            return new RemaptCommandParser().parse(arguments);


        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
