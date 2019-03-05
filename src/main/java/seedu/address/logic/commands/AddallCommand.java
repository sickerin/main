package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Adds a person to the address book.
 */
public class AddallCommand extends Command {

    public static final String COMMAND_WORD = "addall";

    private Index index;
    private String allergyString;

    public AddallCommand(Index index, String allergyString) {
        this.index = index;
        this.allergyString = allergyString;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(index + " " + allergyString);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddallCommand)) {
            return false;
        }

        // state check
        AddallCommand e = (AddallCommand) other;
        return index.equals(e.index)
                && allergyString.equals(e.allergyString);
    }

}
