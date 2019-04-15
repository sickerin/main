package seedu.giatros.logic.parser;

import static seedu.giatros.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.giatros.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.giatros.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.ALLERGY_DESC_AMPICILLIN;
import static seedu.giatros.logic.commands.CommandTestUtil.ALLERGY_DESC_IBUPROFEN;
import static seedu.giatros.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.giatros.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.giatros.logic.commands.CommandTestUtil.INVALID_ALLERGY_DESC;
import static seedu.giatros.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.giatros.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.giatros.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.giatros.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.giatros.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.giatros.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_ALLERGY_AMPICILLIN;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_ALLERGY_IBUPROFEN;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.giatros.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.giatros.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.giatros.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static seedu.giatros.testutil.TypicalIndexes.INDEX_SECOND_PATIENT;
import static seedu.giatros.testutil.TypicalIndexes.INDEX_THIRD_PATIENT;

import org.junit.BeforeClass;
import org.junit.Test;

import seedu.giatros.commons.core.EventsCenter;
import seedu.giatros.commons.core.index.Index;
import seedu.giatros.commons.events.ui.accounts.LoginEvent;
import seedu.giatros.logic.commands.EditCommand;
import seedu.giatros.logic.commands.EditCommand.EditPatientDescriptor;
import seedu.giatros.model.allergy.Allergy;
import seedu.giatros.model.patient.Address;
import seedu.giatros.model.patient.Email;
import seedu.giatros.model.patient.Name;
import seedu.giatros.model.patient.Phone;
import seedu.giatros.testutil.EditPatientDescriptorBuilder;
import seedu.giatros.ui.testutil.AccountCreator;

public class EditCommandParserTest {

    private static final String ALLERGY_EMPTY = " " + PREFIX_ALLERGY;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @BeforeClass
    public static void setupBeforeClass() {
        EventsCenter.getInstance().post(new LoginEvent(new AccountCreator("staff").build()));
    }

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1" + INVALID_ALLERGY_DESC, Allergy.MESSAGE_CONSTRAINTS); // invalid allergy

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_ALLERGY} alone will reset the allergies of the {@code Patient} being edited,
        // parsing it together with a valid allergy results in error
        assertParseFailure(parser, "1" + ALLERGY_DESC_IBUPROFEN + ALLERGY_DESC_AMPICILLIN + ALLERGY_EMPTY,
                Allergy.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + ALLERGY_DESC_IBUPROFEN + ALLERGY_EMPTY + ALLERGY_DESC_AMPICILLIN,
                Allergy.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + ALLERGY_EMPTY + ALLERGY_DESC_IBUPROFEN + ALLERGY_DESC_AMPICILLIN,
                Allergy.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PATIENT;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + ALLERGY_DESC_AMPICILLIN
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY + ALLERGY_DESC_IBUPROFEN;

        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withAllergies(VALID_ALLERGY_AMPICILLIN, VALID_ALLERGY_IBUPROFEN).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PATIENT;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PATIENT;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditPatientDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditPatientDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY;
        descriptor = new EditPatientDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // allergies
        userInput = targetIndex.getOneBased() + ALLERGY_DESC_IBUPROFEN;
        descriptor = new EditPatientDescriptorBuilder().withAllergies(VALID_ALLERGY_IBUPROFEN).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PATIENT;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + ALLERGY_DESC_IBUPROFEN + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + ALLERGY_DESC_IBUPROFEN
                + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + ALLERGY_DESC_AMPICILLIN;

        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withAllergies(VALID_ALLERGY_IBUPROFEN, VALID_ALLERGY_AMPICILLIN)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PATIENT;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + EMAIL_DESC_BOB + INVALID_PHONE_DESC + ADDRESS_DESC_BOB
                + PHONE_DESC_BOB;
        descriptor = new EditPatientDescriptorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetAllergies_success() {
        Index targetIndex = INDEX_THIRD_PATIENT;
        String userInput = targetIndex.getOneBased() + ALLERGY_EMPTY;

        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder().withAllergies().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
