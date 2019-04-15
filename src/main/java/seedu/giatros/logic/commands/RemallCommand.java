package seedu.giatros.logic.commands;

import static seedu.giatros.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.giatros.model.Model.PREDICATE_SHOW_ALL_PATIENTS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.giatros.commons.core.Messages;
import seedu.giatros.commons.core.index.Index;
import seedu.giatros.logic.CommandHistory;
import seedu.giatros.logic.commands.exceptions.CommandException;
import seedu.giatros.model.Model;
import seedu.giatros.model.allergy.Allergy;
import seedu.giatros.model.patient.Patient;

/**
 * Removes an allergy of the existing patient in the giatros book.
 */
public class RemallCommand extends Command {

    public static final String COMMAND_WORD = "remall";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes the allergy from the patient identified "
            + "by the index number used in the patient listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ALLERGY + "ALLERGY [" + PREFIX_ALLERGY + "ALLERGY]\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_ALLERGY + "ibuprofen.";

    public static final String MESSAGE_REMOVE_ALLERGY_SUCCESS = "Removed allergy from Patient: %1$s";
    public static final String MESSAGE_REMOVE_ALLERGY_FAILURE = "Such allergy is not found in Patient: %1$s";

    private Index index;
    private Set<Allergy> allergies;

    public RemallCommand(Index index, Set<Allergy> allergies) {
        requireAllNonNull(index, allergies);

        this.index = index;
        this.allergies = allergies;
    }

    // Overloaded constructor if only one allergy is given
    public RemallCommand(Index index, Allergy allergy) {
        requireAllNonNull(index, allergy);

        Set<Allergy> allergies = new HashSet<>();
        allergies.add(allergy);

        this.index = index;
        this.allergies = allergies;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        List<Patient> lastShownList = model.getFilteredPatientList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }

        Patient patientToEdit = lastShownList.get(index.getZeroBased());

        Set<Allergy> newAllergy = new HashSet<>();
        newAllergy.addAll(patientToEdit.getAllergies());
        newAllergy.removeAll(allergies);

        Patient editedPatient = new Patient(patientToEdit.getName(), patientToEdit.getPhone(), patientToEdit.getEmail(),
                patientToEdit.getAddress(), newAllergy, patientToEdit.getAppointments());

        // No allergy has been removed because it does not exist in the set
        if (patientToEdit.equals(editedPatient)) {
            throw new CommandException(String.format(MESSAGE_REMOVE_ALLERGY_FAILURE, patientToEdit));
        }

        model.setPatient(patientToEdit, editedPatient);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
        model.commitGiatrosBook();

        return new CommandResult(generateSuccessMessage(editedPatient));
    }

    /**
     * Generates a command execution success message when the allergy has been removed from {@code patientToEdit}.
     */
    private String generateSuccessMessage(Patient patientToEdit) {
        String message = MESSAGE_REMOVE_ALLERGY_SUCCESS;
        return String.format(message, patientToEdit);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemallCommand)) {
            return false;
        }

        // state check
        RemallCommand e = (RemallCommand) other;
        return index.equals(e.index)
                && allergies.equals(e.allergies);
    }

}
