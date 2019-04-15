package seedu.giatros.logic.parser;

import static seedu.giatros.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.giatros.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.giatros.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.ALLERGY_DESC_AMPICILLIN;
import static seedu.giatros.logic.commands.CommandTestUtil.ALLERGY_DESC_IBUPROFEN;
import static seedu.giatros.logic.commands.CommandTestUtil.APPOINTMENT_DESC_YMDH;
import static seedu.giatros.logic.commands.CommandTestUtil.APPOINTMENT_DESC_YMDHM;
import static seedu.giatros.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.giatros.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.giatros.logic.commands.CommandTestUtil.INVALID_ALLERGY_DESC;
import static seedu.giatros.logic.commands.CommandTestUtil.INVALID_APPOINTMENT_DESC;
import static seedu.giatros.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.giatros.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.giatros.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.giatros.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.giatros.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.giatros.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.giatros.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_ALLERGY_AMPICILLIN;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_ALLERGY_IBUPROFEN;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_APPOINTMENT_YMDH;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_APPOINTMENT_YMDHM;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.giatros.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.giatros.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.giatros.testutil.TypicalPatients.AMY;
import static seedu.giatros.testutil.TypicalPatients.BOB;

import org.junit.BeforeClass;
import org.junit.Test;

import seedu.giatros.commons.core.EventsCenter;
import seedu.giatros.commons.events.ui.accounts.LoginEvent;
import seedu.giatros.logic.commands.AddCommand;
import seedu.giatros.model.allergy.Allergy;
import seedu.giatros.model.appointment.Appointment;
import seedu.giatros.model.patient.Address;
import seedu.giatros.model.patient.Email;
import seedu.giatros.model.patient.Name;
import seedu.giatros.model.patient.Patient;
import seedu.giatros.model.patient.Phone;
import seedu.giatros.testutil.PatientBuilder;
import seedu.giatros.ui.testutil.AccountCreator;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @BeforeClass
    public static void setupBeforeClass() {
        EventsCenter.getInstance().post(new LoginEvent(new AccountCreator("staff").build()));
    }

    @Test
    public void parse_allFieldsPresent_success() {
        Patient expectedPatient = new PatientBuilder(BOB)
                .withAllergies(VALID_ALLERGY_IBUPROFEN).withAppointments(VALID_APPOINTMENT_YMDH).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + ALLERGY_DESC_IBUPROFEN + APPOINTMENT_DESC_YMDH, new AddCommand(expectedPatient));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + ALLERGY_DESC_IBUPROFEN + APPOINTMENT_DESC_YMDH, new AddCommand(expectedPatient));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + ALLERGY_DESC_IBUPROFEN + APPOINTMENT_DESC_YMDH , new AddCommand(expectedPatient));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + ALLERGY_DESC_IBUPROFEN + APPOINTMENT_DESC_YMDH, new AddCommand(expectedPatient));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + ALLERGY_DESC_IBUPROFEN + APPOINTMENT_DESC_YMDH, new AddCommand(expectedPatient));

        // multiple allergies - all accepted
        Patient expectedPatientMultipleAllergies = new PatientBuilder(BOB)
                .withAllergies(VALID_ALLERGY_IBUPROFEN, VALID_ALLERGY_AMPICILLIN)
                .withAppointments(VALID_APPOINTMENT_YMDH).build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ALLERGY_DESC_AMPICILLIN + ALLERGY_DESC_IBUPROFEN
                + APPOINTMENT_DESC_YMDH, new AddCommand(expectedPatientMultipleAllergies));

        // multiple appointments - all accepted
        Patient expectedPatientMultipleAppointments = new PatientBuilder(BOB)
                .withAllergies(VALID_ALLERGY_IBUPROFEN)
                .withAppointments(VALID_APPOINTMENT_YMDH, VALID_APPOINTMENT_YMDHM).build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ALLERGY_DESC_IBUPROFEN + APPOINTMENT_DESC_YMDH
                + APPOINTMENT_DESC_YMDHM, new AddCommand(expectedPatientMultipleAppointments));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero allergies
        Patient expectedPatient = new PatientBuilder(AMY).withAllergies().withAppointments().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
                new AddCommand(expectedPatient));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ALLERGY_DESC_AMPICILLIN + ALLERGY_DESC_IBUPROFEN
                + APPOINTMENT_DESC_YMDH + APPOINTMENT_DESC_YMDHM, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ALLERGY_DESC_AMPICILLIN + ALLERGY_DESC_IBUPROFEN
                + APPOINTMENT_DESC_YMDH + APPOINTMENT_DESC_YMDHM, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + ALLERGY_DESC_AMPICILLIN + ALLERGY_DESC_IBUPROFEN
                + APPOINTMENT_DESC_YMDH + APPOINTMENT_DESC_YMDHM, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + ALLERGY_DESC_AMPICILLIN + ALLERGY_DESC_IBUPROFEN
                + APPOINTMENT_DESC_YMDH + APPOINTMENT_DESC_YMDHM, Address.MESSAGE_CONSTRAINTS);

        // invalid allergy
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_ALLERGY_DESC + ALLERGY_DESC_IBUPROFEN
                + APPOINTMENT_DESC_YMDH + APPOINTMENT_DESC_YMDHM, Allergy.MESSAGE_CONSTRAINTS);

        // invalid appointment
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ALLERGY_DESC_AMPICILLIN + ALLERGY_DESC_IBUPROFEN
                + INVALID_APPOINTMENT_DESC , Appointment.MESSAGE_CONSTRAINTS);


        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + ALLERGY_DESC_AMPICILLIN + ALLERGY_DESC_IBUPROFEN,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
