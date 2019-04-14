package seedu.giatros.testutil;

import static seedu.giatros.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Set;

import seedu.giatros.logic.commands.AddCommand;
import seedu.giatros.logic.commands.EditCommand.EditPatientDescriptor;
import seedu.giatros.model.allergy.Allergy;
import seedu.giatros.model.appointment.Appointment;
import seedu.giatros.model.patient.Patient;

/**
 * A utility class for Patient.
 */
public class PatientUtil {

    /**
     * Returns an add command string for adding the {@code patient}.
     */
    public static String getAddCommand(Patient patient) {
        return AddCommand.COMMAND_WORD + " " + getPatientDetails(patient);
    }

    /**
     * Returns the part of command string for the given {@code patient}'s details.
     */
    public static String getPatientDetails(Patient patient) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + patient.getName().fullName + " ");
        sb.append(PREFIX_PHONE + patient.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + patient.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + patient.getAddress().value + " ");
        patient.getAllergies().stream().forEach(
            s -> sb.append(PREFIX_ALLERGY + s.allergyName + " ")
        );
        // ! temporary fix, need space or in test we get y/apt/
        sb.append(" ");
        patient.getAppointments().stream().forEach(
            s -> sb.append(PREFIX_APPOINTMENT + s.appointmentString + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPatientDescriptor}'s details.
     */
    public static String getEditPatientDescriptorDetails(EditPatientDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getAllergies().isPresent()) {
            Set<Allergy> allergies = descriptor.getAllergies().get();
            if (allergies.isEmpty()) {
                sb.append(PREFIX_ALLERGY);
            } else {
                allergies.forEach(s -> sb.append(PREFIX_ALLERGY).append(s.allergyName).append(" "));
            }
        }
        // ! temporary fix, need space or in test we get y/apt/
        sb.append(" ");
        if (descriptor.getAppointments().isPresent()) {
            Set<Appointment> appointments = descriptor.getAppointments().get();
            if (appointments.isEmpty()) {
                sb.append(PREFIX_APPOINTMENT);
            } else {
                appointments.forEach(s -> sb.append(PREFIX_APPOINTMENT).append(s.appointmentString).append(" "));
            }
        }
        return sb.toString();
    }
}
