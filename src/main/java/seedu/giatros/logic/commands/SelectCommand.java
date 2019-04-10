package seedu.giatros.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.giatros.commons.core.Messages;
import seedu.giatros.commons.core.index.Index;
import seedu.giatros.logic.CommandHistory;
import seedu.giatros.logic.commands.exceptions.CommandException;
import seedu.giatros.model.Model;
import seedu.giatros.model.patient.Patient;

/**
 * Selects a patient identified using it's displayed index from the Giatros book.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the patient identified by the index number used in the displayed patient list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_PATIENT_SUCCESS = "Selected Patient: %1$s";

    private final Index targetIndex;

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Patient> filteredPatientList = model.getFilteredPatientList();

        if (targetIndex.getZeroBased() >= filteredPatientList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }

        model.setSelectedPatient(filteredPatientList.get(targetIndex.getZeroBased()));
        return new CommandResult(String.format(MESSAGE_SELECT_PATIENT_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}
