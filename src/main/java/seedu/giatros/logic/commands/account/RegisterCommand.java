package seedu.giatros.logic.commands.account;

import static java.util.Objects.requireNonNull;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_PASSWORD;

import seedu.giatros.commons.core.EventsCenter;
import seedu.giatros.commons.events.ui.accounts.DisplayAccountListRequestEvent;
import seedu.giatros.logic.CommandHistory;
import seedu.giatros.logic.commands.Command;
import seedu.giatros.logic.commands.CommandResult;
import seedu.giatros.logic.commands.exceptions.CommandException;
import seedu.giatros.model.Model;
import seedu.giatros.model.account.Account;

/**
 * Adds a new {@code Account}.
 */
public class RegisterCommand extends Command {

    public static final String COMMAND_WORD = "register";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Registers a new user account. "
            + "Parameters: "
            + PREFIX_ID + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD "
            + PREFIX_NAME + "NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "chuaes "
            + PREFIX_PASSWORD + "1122qq "
            + PREFIX_NAME + "Chua Eng Soon";

    public static final String MESSAGE_SUCCESS = "New account registered: %1$s";
    public static final String MESSAGE_DUPLICATE_USERNAME = "This username already exists. Please choose another "
            + "username";
    public static final String MESSAGE_RESTRICTED_USERNAME =
            "Restricted word for username: manager. Please use a different username!";

    private final Account account;

    public RegisterCommand(Account account) {
        requireNonNull(account);
        this.account = account;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (account.getUsername().toString().length() >= 7 && account.getUsername().toString().toLowerCase()
                .substring(0, 7).equals("manager")) {
            throw new CommandException(MESSAGE_RESTRICTED_USERNAME);
        }
        if (model.hasAccount(account)) {
            throw new CommandException(MESSAGE_DUPLICATE_USERNAME);
        }

        model.addAccount(account);
        model.commitGiatrosBook();
        EventsCenter.getInstance().post(new DisplayAccountListRequestEvent());
        return new CommandResult(String.format(MESSAGE_SUCCESS, account));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RegisterCommand // instanceof handles nulls
                    && account.equals(((RegisterCommand) other).account));
    }
}
