package seedu.giatros.model.appointment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.giatros.testutil.Assert;

public class AppointmentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Appointment(null));
    }

    @Test
    public void constructor_invalidAppointment_throwsIllegalArgumentException() {
        String invalidAppointment = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Appointment(invalidAppointment));
    }

    @Test
    public void isValidAppointment() {
        // null appointment
        Assert.assertThrows(NullPointerException.class, () -> Appointment.isValidAppointment(null));

        // invalid appointment
        assertFalse(Appointment.isValidAppointment("2020-01-01")); // no time
        assertFalse(Appointment.isValidAppointment("2020-01-01 24:10")); // impossible time
        assertFalse(Appointment.isValidAppointment("2020-01-01 10:70")); // impossible minute
        assertFalse(Appointment.isValidAppointment("2020 May 1")); // wrong format
        assertFalse(Appointment.isValidAppointment("2020-14-01 14")); // impossible month
        assertFalse(Appointment.isValidAppointment("2020-11-41 14")); // impossible day
        assertFalse(Appointment.isValidAppointment("2020-04-31 14")); // 31 days in April
        assertFalse(Appointment.isValidAppointment("2019-02-29 14")); // wrong leap year
        assertFalse(Appointment.isValidAppointment("2019/01/01 14")); // using backslash

        // valid appointment
        assertTrue(Appointment.isValidAppointment("2020-01-01 00")); // hour only
        assertTrue(Appointment.isValidAppointment("2020-01-01 00:00")); // hour and minute
        assertTrue(Appointment.isValidAppointment("2020-01-01 00:00:00")); // hour minute second
        assertTrue(Appointment.isValidAppointment("2019-09-14 09:56:34")); // random combination
    }
}
