package seedu.giatros.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.giatros.model.Model.PREDICATE_SHOW_ALL_PATIENTS;

import seedu.giatros.logic.CommandHistory;
import seedu.giatros.model.Model;

/**
 * Lists all patients in the Giatros book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all patients";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
