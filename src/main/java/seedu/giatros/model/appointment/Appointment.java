package seedu.giatros.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.giatros.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.Date;

/**
 * Represents an Appointment in the Giatros book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidAppointment(String)}
 */
public class Appointment {
    public static final String[] VALIDATION_REGEX_LIST = {"yyyy-MM-dd HH:mm:ss",
        "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH"};
    public static final String MESSAGE_CONSTRAINTS = "Appointment should not be blank "
            + "and should be in one of the formats: "
            + String.join(" , ", VALIDATION_REGEX_LIST)
            + ".\nEnsure that your date and time isn't one that does not exist. "
            + "I.e account for leap years, months that have only 30 days, etc.";
    public final String appointmentString;
    private Date appointment;

    public Appointment(String appointmentString) {
        requireNonNull(appointmentString);
        checkArgument(isValidAppointment(appointmentString), MESSAGE_CONSTRAINTS);
        this.appointmentString = appointmentString;
        this.appointment = convertStringtoDate(appointmentString);
        // this.day = day;
        // this.date = date;
    }

    /**
     * Converts a {@code appointmentString} into a {@Date} object
     * @param appointmentString
     * @return convertedDate
     */
    public static Date convertStringtoDate(String appointmentString) {
        Date convertedDate = null;
        String format = Arrays.asList(VALIDATION_REGEX_LIST).stream()
                .filter(VALIDATION_REGEX -> {
                    try {
                        LocalDateTime.parse(appointmentString, DateTimeFormatter.ofPattern(VALIDATION_REGEX));
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                })
                .findFirst()
                .get();
        try {
            convertedDate = new SimpleDateFormat(format).parse(appointmentString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }

    /**
     * Returns true if a given string is a valid appointment.
     */
    public static boolean isValidAppointment(String test) {
        requireNonNull(test);
        boolean match = Arrays.asList(VALIDATION_REGEX_LIST).stream()
                .anyMatch(VALIDATION_REGEX -> {
                    try {
                        DateTimeFormatter customFormat = new DateTimeFormatterBuilder().appendPattern(VALIDATION_REGEX)
                                .parseDefaulting(ChronoField.ERA, 1)
                                .toFormatter()
                                .withChronology(IsoChronology.INSTANCE)
                                .withResolverStyle(ResolverStyle.STRICT);
                        LocalDateTime.parse(test, customFormat);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                });
        return match;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Appointment // instanceof handles nulls
                && appointmentString.equals(((Appointment) other).appointmentString)); // state check
    }

    @Override
    public int hashCode() {
        return appointmentString.hashCode();
    }

    public String toString() {
        return '[' + appointmentString + ']';
    }
}


