package seedu.giatros.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.giatros.model.GiatrosBook;
import seedu.giatros.model.ReadOnlyGiatrosBook;
import seedu.giatros.model.account.Account;
import seedu.giatros.model.account.Password;
import seedu.giatros.model.account.Username;
import seedu.giatros.model.allergy.Allergy;
import seedu.giatros.model.appointment.Appointment;
import seedu.giatros.model.patient.Address;
import seedu.giatros.model.patient.Email;
import seedu.giatros.model.patient.Name;
import seedu.giatros.model.patient.Patient;
import seedu.giatros.model.patient.Phone;

/**
 * Contains utility methods for populating {@code GiatrosBook} with sample data.
 */
public class SampleDataUtil {

    public static Account[] getSampleAccount() {
        return new Account[] {
            new Account(new Username("MANAGER"), new Password("1122qq"), new seedu.giatros.model.account
                    .Name("MANAGER")),
            new Account(new Username("STAFF"), new Password("1122qq"), new seedu.giatros.model.account
                    .Name("STAFF"))
        };
    }

    public static Patient[] getSamplePatients() {
        return new Patient[] {
            new Patient(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), getAllergySet("amoxicillin"),
                getAppointmentSet("2019-04-01 14:30")),
            new Patient(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getAllergySet("ampicillin", "penicillin"), getAppointmentSet("2019-04-01 13:30", "2019-04-03 14")),
            new Patient(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), getAllergySet("aspirin"),
                getAppointmentSet("2019-03-10 13:30", "2019-12-14 14")),
            new Patient(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), getAllergySet("penicillin"),
                getAppointmentSet("2019-03-12 13:30")),
            new Patient(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), getAllergySet("ibuprofen"),
                getAppointmentSet("2019-01-23 14")),
            new Patient(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), getAllergySet("amoxicilin"),
                getAppointmentSet("2019-03-10 21:30"))
        };
    }

    public static ReadOnlyGiatrosBook getSampleGiatrosBook() {
        GiatrosBook sampleAb = new GiatrosBook();
        for (Patient samplePatient : getSamplePatients()) {
            sampleAb.addPatient(samplePatient);
        }
        for (Account sampleAccount : getSampleAccount()) {
            sampleAb.addAccount(sampleAccount);
        }
        return sampleAb;
    }

    /**
     * Returns an allergy set containing the list of strings given.
     */
    public static Set<Allergy> getAllergySet(String... strings) {
        return Arrays.stream(strings)
                .map(Allergy::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns an allergy set containing the list of strings given.
     */
    public static Set<Appointment> getAppointmentSet(String... strings) {
        return Arrays.stream(strings)
                .map(Appointment::new)
                .collect(Collectors.toSet());
    }
}
