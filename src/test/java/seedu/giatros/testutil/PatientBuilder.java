package seedu.giatros.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.giatros.model.allergy.Allergy;
import seedu.giatros.model.appointment.Appointment;
import seedu.giatros.model.patient.Address;
import seedu.giatros.model.patient.Email;
import seedu.giatros.model.patient.Name;
import seedu.giatros.model.patient.Patient;
import seedu.giatros.model.patient.Phone;
import seedu.giatros.model.util.SampleDataUtil;

/**
 * A utility class to help with building Patient objects.
 */
public class PatientBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_ALLERGY = "none";
    public static final String DEFAULT_APPOINTMENT = "2019-01-01 00:00:00";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Allergy allergy;
    private Appointment appointment;
    private Set<Allergy> allergies;
    private Set<Appointment> appointments;

    public PatientBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        allergy = new Allergy(DEFAULT_ALLERGY);
        allergies = new HashSet<>();
        appointment = new Appointment(DEFAULT_APPOINTMENT);
        appointments = new HashSet<>();
    }

    /**
     * Initializes the PatientBuilder with the data of {@code patientToCopy}.
     */
    public PatientBuilder(Patient patientToCopy) {
        name = patientToCopy.getName();
        phone = patientToCopy.getPhone();
        email = patientToCopy.getEmail();
        address = patientToCopy.getAddress();
        allergies = new HashSet<>(patientToCopy.getAllergies());
        appointments = new HashSet<>(patientToCopy.getAppointments());
    }

    /**
     * Sets the {@code Name} of the {@code Patient} that we are building.
     */
    public PatientBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Patient} that we are building.
     */
    public PatientBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Patient} that we are building.
     */
    public PatientBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Patient} that we are building.
     */
    public PatientBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }


    /**
     * Parses the {@code allergies} into a {@code Set<Allergy>} and set it to the {@code Patient} that we are building.
     */
    public PatientBuilder withAllergies(String ... allergies) {
        this.allergies = SampleDataUtil.getAllergySet(allergies);
        return this;
    }

    /**
     * Adds a new {@code allergy} to the {@code Patient} that we are building.
     */
    public PatientBuilder withAllergy(String allergy) {
        this.allergies.add(new Allergy(allergy));
        return this;
    }

    /**
     * Parses the {@code appointments} into a {@code Set<Appointment>}
     * and set it to the {@code Patient} that we are building.
     */
    public PatientBuilder withAppointments(String ... appointments) {
        this.appointments = SampleDataUtil.getAppointmentSet(appointments);
        return this;
    }

    /**
     * Adds a new {@code appointment} to the {@code Patient} that we are building.
     */
    public PatientBuilder withAppointment(String appointment) {
        this.appointments.add(new Appointment(appointment));
        return this;
    }

    public Patient build() {
        return new Patient(name, phone, email, address, allergies, appointments);
    }

}
