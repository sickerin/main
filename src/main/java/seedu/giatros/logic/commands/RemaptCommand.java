package seedu.giatros.logic.commands;

import static seedu.giatros.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.giatros.model.Model.PREDICATE_SHOW_ALL_PATIENTS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.giatros.commons.core.Messages;
import seedu.giatros.commons.core.index.Index;
import seedu.giatros.logic.CommandHistory;
import seedu.giatros.logic.commands.exceptions.CommandException;
import seedu.giatros.model.Model;
import seedu.giatros.model.appointment.Appointment;
import seedu.giatros.model.patient.Patient;

/**
 * Removes an appointment of the existing patient in the giatros book.
 */
public class RemaptCommand extends Command {

    public static final String COMMAND_WORD = "remapt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes the appointment from the patient identified "
            + "by the index number used in the patient listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_APPOINTMENT + "APPOINTMENT [" + PREFIX_APPOINTMENT + "APPOINTMENT]\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_APPOINTMENT
            + "2019-03-19 10:30:00.";

    public static final String MESSAGE_REMOVE_APPOINTMENT_SUCCESS = "Removed appointment from Patient: %1$s";
    public static final String MESSAGE_REMOVE_APPOINTMENT_FAILURE = "Such appointment is not found in Patient: %1$s";

    private Index index;
    private Set<Appointment> appointments;

    public RemaptCommand(Index index, Set<Appointment> appointments) {
        requireAllNonNull(index, appointments);

        this.index = index;
        this.appointments = appointments;
    }

    // Overloaded constructor if only one appointment is given
    public RemaptCommand(Index index, Appointment appointment) {
        requireAllNonNull(index, appointment);

        Set<Appointment> appointments = new HashSet<>();
        appointments.add(appointment);

        this.index = index;
        this.appointments = appointments;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        List<Patient> lastShownList = model.getFilteredPatientList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }

        Patient patientToEdit = lastShownList.get(index.getZeroBased());

        Set<Appointment> newAppointment = new HashSet<>();
        newAppointment.addAll(patientToEdit.getAppointments());
        newAppointment.removeAll(appointments);

        Patient editedPatient = new Patient(patientToEdit.getName(), patientToEdit.getPhone(), patientToEdit.getEmail(),
                patientToEdit.getAddress(), patientToEdit.getAllergies(), newAppointment);

        // No appointment has been removed because it does not exist in the set
        if (patientToEdit.equals(editedPatient)) {
            throw new CommandException(String.format(MESSAGE_REMOVE_APPOINTMENT_FAILURE, patientToEdit));
        }

        model.setPatient(patientToEdit, editedPatient);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
        model.commitGiatrosBook();

        return new CommandResult(generateSuccessMessage(editedPatient));
    }

    /**
     * Generates a command execution success message when the appointment has been removed from {@code patientToEdit}.
     */
    private String generateSuccessMessage(Patient patientToEdit) {
        String message = MESSAGE_REMOVE_APPOINTMENT_SUCCESS;
        return String.format(message, patientToEdit);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemaptCommand)) {
            return false;
        }

        // state check
        RemaptCommand e = (RemaptCommand) other;
        return index.equals(e.index)
                && appointments.equals(e.appointments);
    }

}
