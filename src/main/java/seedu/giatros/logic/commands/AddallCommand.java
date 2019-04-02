package seedu.giatros.logic.commands;

import static seedu.giatros.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.giatros.model.Model.PREDICATE_SHOW_ALL_PATIENTS;

import java.util.List;

import seedu.giatros.commons.core.Messages;
import seedu.giatros.commons.core.index.Index;
import seedu.giatros.logic.CommandHistory;
import seedu.giatros.logic.commands.exceptions.CommandException;
import seedu.giatros.model.Model;
import seedu.giatros.model.allergy.Allergy;
import seedu.giatros.model.patient.Patient;

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

    public static final String MESSAGE_ADD_ALLERGY_SUCCESS = "Added allergy to Patient: %1$s";
    public static final String MESSAGE_DELETE_ALLERGY_SUCCESS = "Removed allergy from Patient: %1$s";

    private Index index;
    private Allergy allergy;

    public AddallCommand(Index index, Allergy allergy) {
        requireAllNonNull(index, allergy);

        this.index = index;
        this.allergy = allergy;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        List<Patient> lastShownList = model.getFilteredPatientList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }

        Patient patientToEdit = lastShownList.get(index.getZeroBased());
        Patient editedPatient = new Patient(patientToEdit.getName(), patientToEdit.getPhone(), patientToEdit.getEmail(),
                patientToEdit.getAddress(), allergy, patientToEdit.getAllergies());

        model.setPatient(patientToEdit, editedPatient);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
        model.commitGiatrosBook();

        return new CommandResult(generateSuccessMessage(editedPatient));
    }

    /**
     * Generates a command execution success message based on whether the allergy is added to or removed from
     * {@code patientToEdit}.
     */
    private String generateSuccessMessage(Patient patientToEdit) {
        String message = !allergy.allergyName.isEmpty() ? MESSAGE_ADD_ALLERGY_SUCCESS : MESSAGE_DELETE_ALLERGY_SUCCESS;
        return String.format(message, patientToEdit);
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
                && allergy.equals(e.allergy);
    }

}
