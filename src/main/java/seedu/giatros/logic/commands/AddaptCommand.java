package seedu.giatros.logic.commands;

import static seedu.giatros.commons.util.CollectionUtil.requireAllNonNull;
// TODO the import below does not exist
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

public class AddaptCommand extends Command {

    public static final String COMMAND_WORD = "addapt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds the appointment to the patient identified "
            + "by the index number used in the patient listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_APPOINTMENT + "[APPOINTMENT]\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_APPOINTMENT + "ibuprofen.";

    public static final String MESSAGE_ADD_APPOINTMENT_SUCCESS = "Added appointment to Patient: %1$s";
    public static final String MESSAGE_ADD_APPOINTMENT_FAILURE = "Such appointment is already associated with Patient: %1$s";
    public static final String MESSAGE_INCORRECT_APPOINTMENT = "At least one appointment must be provided";

    private Index index;
    private Set<Appointment> appointments;

    public AddaptCommand(Index index, Set<Appointment> appointments) {
        requireAllNonNull(index, appointments);

        this.index = index;
        this.appointments = appointments;
    }

    public AddaptCommand(Index index, Appointment appointment) {
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
        // TODO add getAppointment method to patient class
        newAppointment.addAll(patientToEdit.getAppointments());
        newAppointment.addAll(appointments);

        Patient editedPatient = new Patient(patientToEdit.getName(), patientToEdit.getPhone(), patientToEdit.getEmail(),
                patientToEdit.getAddress(), patientToEdit.getAllergies(), newAppointment);

        model.setPatient(patientToEdit, editedPatient);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
        model.commitGiatrosBook();

        if (!editedPatient.equals(patientToEdit)) {
            return new CommandResult(generateSuccessMessage(editedPatient));
        } else {
            return new CommandResult(generateFailureMessage(editedPatient));
        }
    }

    /**
     * Generates a command execution success message when the appointment has been added to {@code patientToEdit}.
     */
    private String generateSuccessMessage(Patient patientToEdit) {
        String message = MESSAGE_ADD_APPOINTMENT_SUCCESS;
        return String.format(message, patientToEdit);
    }

    /**
     * Generates a command execution failure message when the appointment is not added to {@code patientToEdit}.
     */
    private String generateFailureMessage(Patient patientToEdit) {
        String message = MESSAGE_ADD_APPOINTMENT_FAILURE;
        return String.format(message, patientToEdit);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddaptCommand)) {
            return false;
        }

        // state check
        AddaptCommand e = (AddaptCommand) other;
        return index.equals(e.index)
                && appointments.equals(e.appointments);
    }

}

