package seedu.giatros.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.giatros.model.Model.PREDICATE_SHOW_ALL_PATIENTS;

import seedu.giatros.logic.CommandHistory;
import seedu.giatros.logic.commands.exceptions.CommandException;
import seedu.giatros.model.Model;

/**
 * Reverts the {@code model}'s Giatros book to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoGiatrosBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoGiatrosBook();
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
