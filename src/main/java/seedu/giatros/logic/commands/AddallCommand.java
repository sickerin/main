package seedu.giatros.logic.commands;

import static seedu.giatros.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_ALLERGY;

import seedu.giatros.commons.core.index.Index;
import seedu.giatros.logic.CommandHistory;
import seedu.giatros.logic.commands.exceptions.CommandException;
import seedu.giatros.model.Model;

/**
 * Changes the allergy of an existing patient in the giatros book.
 */
public class AddallCommand extends Command {

    public static final String COMMAND_WORD = "addall";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the allergy of a patient identified "
            + "by the index number used in the patient listing. "
            + "Existing allergy will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ALLERGY + "[ALLERGY]\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_ALLERGY + "ibuprofen.";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Allergy: %2$s";

    private Index index;
    private String allergyString;

    public AddallCommand(Index index, String allergyString) {
        requireAllNonNull(index, allergyString);

        this.index = index;
        this.allergyString = allergyString;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, index.getOneBased(), allergyString));
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
