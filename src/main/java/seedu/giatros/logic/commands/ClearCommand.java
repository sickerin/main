package seedu.giatros.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.giatros.logic.CommandHistory;
import seedu.giatros.model.GiatrosBook;
import seedu.giatros.model.Model;

/**
 * Clears the giatros book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Giatros book has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.setGiatrosBook(new GiatrosBook());
        model.commitGiatrosBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
