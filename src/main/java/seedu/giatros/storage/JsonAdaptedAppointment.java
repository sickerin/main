package seedu.giatros.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.giatros.commons.exceptions.IllegalValueException;
import seedu.giatros.model.appointment.Appointment;

/**
 * Jackson-friendly version of {@link Appointment}
 */
public class JsonAdaptedAppointment {

    private final String appointment;

    /**
     * Constructs a {@code JsonAdaptedAppointment} with the given {@code appointment}.
     */
    @JsonCreator
    public JsonAdaptedAppointment(String appointment) {
        this.appointment = appointment;
    }

    /**
     * Converts a given {@code Appointment} into this class for Jackson use.
     */
    public JsonAdaptedAppointment(Appointment source) {
        appointment = source.appointmentString;
    }

    @JsonValue
    public String getAppointment() {
        return appointment;
    }

    /**
     * Converts this Jackson-friendly adapted appointment object into the model's {@code Appointment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted appointment.
     */
    public Appointment toModelType() throws IllegalValueException {
        if (!Appointment.isValidAppointment(appointment)) {
            throw new IllegalValueException(Appointment.MESSAGE_CONSTRAINTS);
        }
        return new Appointment(appointment);
    }


}
