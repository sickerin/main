package seedu.giatros.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.giatros.commons.util.AppUtil.checkArgument;

// ? make sure it's the correct convention for imports
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;



public class Appointment {
    // TODO make sure that the regex stuff below is correct.
    // public static final String DAY = "";
    // public static final String TIME = "{";
    // TODO have a flexible regex, 
    // ? is there an issue with the style below?
    public static final String[] VALIDATION_REGEX_LIST = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH"};;
    // TODO use day of week object and convert date to day
    // ? should it be final if appointment is editable?
    public static final String MESSAGE_CONSTRAINTS = "Appointment should not be blank and should be in one of the formats: " + String.join(" , ", VALIDATION_REGEX_LIST);
    // public String day;
    // public String date;
    public final String appointment;
    // TODO make a date field.
    // TODO make it figure out day from the time.


    public Appointment(String appointment){
        requireNonNull(appointment);
        checkArgument(isValidAppointment(appointment), MESSAGE_CONSTRAINTS);
        this.appointment= appointment;
        // this.day = day;
        // this.date = date;
    }

    // // TODO move parser to the ParserUtil class
    // public void parseAppointment(String appointment){
    //     String[] appointmentArray = appointment.split("\\s+");
    //     day = appointmentArray[0].trim();
    //     time = appointmentArray[1].trim();
    // }

    // TODO extract date time and day.

    public static boolean isValidAppointment(String test){
        // * reference https://www.codota.com/code/java/methods/java.util.stream.Stream/anyMatch
        // ? with regards to style should it be VALIDATION_REGEX??
        boolean match = Arrays.asList(VALIDATION_REGEX_LIST).stream()
            .anyMatch( VALIDATION_REGEX -> {
                try {
                    LocalDateTime.parse(test, DateTimeFormatter.ofPattern(VALIDATION_REGEX));
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
                && appointment.equals(((Appointment) other).appointment)); // state check
    }

    @Override
    public int hashCode() {
        return appointment.hashCode();
    }

    public String toString() {
        return appointment;
    }
}


