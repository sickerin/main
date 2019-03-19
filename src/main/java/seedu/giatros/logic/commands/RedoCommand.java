package seedu.giatros.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.giatros.model.Model.PREDICATE_SHOW_ALL_PATIENTS;

import seedu.giatros.logic.CommandHistory;
import seedu.giatros.logic.commands.exceptions.CommandException;
import seedu.giatros.model.Model;

/**
 * Reverts the {@code model}'s giatros book to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoGiatrosBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoGiatrosBook();
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
