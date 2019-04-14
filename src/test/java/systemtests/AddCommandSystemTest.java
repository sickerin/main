package systemtests;

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
import static seedu.giatros.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.giatros.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.giatros.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.giatros.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.giatros.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.giatros.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.giatros.testutil.TypicalPatients.ALICE;
import static seedu.giatros.testutil.TypicalPatients.AMY;
import static seedu.giatros.testutil.TypicalPatients.BOB;
import static seedu.giatros.testutil.TypicalPatients.CARL;
import static seedu.giatros.testutil.TypicalPatients.HOON;
import static seedu.giatros.testutil.TypicalPatients.IDA;
import static seedu.giatros.testutil.TypicalPatients.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.giatros.commons.core.Messages;
import seedu.giatros.commons.core.index.Index;
import seedu.giatros.logic.commands.AddCommand;
import seedu.giatros.logic.commands.RedoCommand;
import seedu.giatros.logic.commands.UndoCommand;
import seedu.giatros.model.Model;
import seedu.giatros.model.allergy.Allergy;
import seedu.giatros.model.patient.Address;
import seedu.giatros.model.patient.Email;
import seedu.giatros.model.patient.Name;
import seedu.giatros.model.patient.Patient;
import seedu.giatros.model.patient.Phone;
import seedu.giatros.testutil.PatientBuilder;
import seedu.giatros.testutil.PatientUtil;

public class AddCommandSystemTest extends GiatrosBookSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a patient without allergies to a non-empty Giatros book, command with leading spaces and trailing
         * spaces -> added
         */
        Patient toAdd = AMY;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
                + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   "
                + ALLERGY_DESC_IBUPROFEN + " " + APPOINTMENT_DESC_YMDH;
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addPatient(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a patient with all fields same as another patient in the Giatros book except name -> added */
        toAdd = new PatientBuilder(AMY).withName(VALID_NAME_BOB).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + ALLERGY_DESC_IBUPROFEN + APPOINTMENT_DESC_YMDH;
        assertCommandSuccess(command, toAdd);

        /* Case: add a patient with all fields same as another patient in the Giatros book except phone and email
         * -> added
         */
        toAdd = new PatientBuilder(AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        command = PatientUtil.getAddCommand(toAdd);
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty Giatros book -> added */
        deleteAllPatients();
        assertCommandSuccess(ALICE);

        /* Case: add a patient with allergies, command with parameters in random order -> added */
        toAdd = BOB;
        command = AddCommand.COMMAND_WORD + ALLERGY_DESC_IBUPROFEN + PHONE_DESC_BOB + APPOINTMENT_DESC_YMDHM
                + ADDRESS_DESC_BOB + NAME_DESC_BOB + ALLERGY_DESC_AMPICILLIN + EMAIL_DESC_BOB + APPOINTMENT_DESC_YMDH;
        assertCommandSuccess(command, toAdd);

        /* Case: add a patient, missing allergies -> added */
        assertCommandSuccess(HOON);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the patient list before adding -> added */
        showPatientsWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(IDA);

        /* ------------------------ Perform add operation while a patient card is selected ---------------------------
         */

        /* Case: selects first card in the patient list, add a patient -> added, card selection remains unchanged */
        selectPatient(Index.fromOneBased(1));
        assertCommandSuccess(CARL);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate patient -> rejected */
        command = PatientUtil.getAddCommand(HOON);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PATIENT);

        /* Case: add a duplicate patient except with different phone -> rejected */
        toAdd = new PatientBuilder(HOON).withPhone(VALID_PHONE_BOB).build();
        command = PatientUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PATIENT);

        /* Case: add a duplicate patient except with different email -> rejected */
        toAdd = new PatientBuilder(HOON).withEmail(VALID_EMAIL_BOB).build();
        command = PatientUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PATIENT);

        /* Case: add a duplicate patient except with different address -> rejected */
        toAdd = new PatientBuilder(HOON).withAddress(VALID_ADDRESS_BOB).build();
        command = PatientUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PATIENT);

        /* Case: add a duplicate patient except with different allergies -> rejected */
        command = PatientUtil.getAddCommand(HOON) + " " + PREFIX_ALLERGY.getPrefix() + "paracetamol";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PATIENT);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing address -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + PatientUtil.getPatientDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, Name.MESSAGE_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + INVALID_PHONE_DESC + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, Phone.MESSAGE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + INVALID_EMAIL_DESC + ADDRESS_DESC_AMY;
        assertCommandFailure(command, Email.MESSAGE_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + INVALID_ADDRESS_DESC;
        assertCommandFailure(command, Address.MESSAGE_CONSTRAINTS);

        /* Case: invalid allergy -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + INVALID_ALLERGY_DESC;
        assertCommandFailure(command, Allergy.MESSAGE_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code PatientListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code GiatrosBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see GiatrosBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Patient toAdd) {
        assertCommandSuccess(PatientUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Patient)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Patient)
     */
    private void assertCommandSuccess(String command, Patient toAdd) {
        Model expectedModel = getModel();
        expectedModel.addPatient(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Patient)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code PatientListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Patient)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code PatientListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code GiatrosBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see GiatrosBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
