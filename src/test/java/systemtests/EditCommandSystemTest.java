package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
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
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_ALLERGY_AMPICILLIN;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_ALLERGY_IBUPROFEN;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_APPOINTMENT_YMDH;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_APPOINTMENT_YMDHM;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.giatros.model.Model.PREDICATE_SHOW_ALL_PATIENTS;
import static seedu.giatros.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static seedu.giatros.testutil.TypicalIndexes.INDEX_SECOND_PATIENT;
import static seedu.giatros.testutil.TypicalPatients.AMY;
import static seedu.giatros.testutil.TypicalPatients.BOB;
import static seedu.giatros.testutil.TypicalPatients.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.giatros.commons.core.Messages;
import seedu.giatros.commons.core.index.Index;
import seedu.giatros.logic.commands.EditCommand;
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

public class EditCommandSystemTest extends GiatrosBookSystemTest {

    @Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_PATIENT;
        String command = " " + EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + NAME_DESC_BOB + " "
                + PHONE_DESC_BOB + " " + EMAIL_DESC_BOB + " " + ADDRESS_DESC_BOB + " "
                + ALLERGY_DESC_IBUPROFEN + " " + ALLERGY_DESC_AMPICILLIN + " "
                + APPOINTMENT_DESC_YMDH + " " + APPOINTMENT_DESC_YMDHM + " ";
        Patient editedPatient = new PatientBuilder(BOB).withAllergies(VALID_ALLERGY_IBUPROFEN, VALID_ALLERGY_AMPICILLIN)
                .withAppointments(VALID_APPOINTMENT_YMDH, VALID_APPOINTMENT_YMDHM).build();
        assertCommandSuccess(command, index, editedPatient);

        /* Case: undo editing the last patient in the list -> last patient restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last patient in the list -> last patient edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.setPatient(getModel().getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased()), editedPatient);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a patient with new values same as existing values -> edited */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + ALLERGY_DESC_IBUPROFEN + ALLERGY_DESC_AMPICILLIN
                + APPOINTMENT_DESC_YMDH + APPOINTMENT_DESC_YMDHM;
        assertCommandSuccess(command, index, BOB);

        /* Case: edit a patient with new values same as another patient's values but with different name -> edited */
        assertTrue(getModel().getGiatrosBook().getPatientList().contains(BOB));
        index = INDEX_SECOND_PATIENT;
        assertNotEquals(getModel().getFilteredPatientList().get(index.getZeroBased()), BOB);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + ALLERGY_DESC_IBUPROFEN + ALLERGY_DESC_AMPICILLIN
                + APPOINTMENT_DESC_YMDH + APPOINTMENT_DESC_YMDHM;
        editedPatient = new PatientBuilder(BOB).withName(VALID_NAME_AMY).build();
        assertCommandSuccess(command, index, editedPatient);

        /* Case: edit a patient with new values same as another patient's values but with different phone and email
         * -> edited
         */
        index = INDEX_SECOND_PATIENT;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_BOB + ALLERGY_DESC_IBUPROFEN + ALLERGY_DESC_AMPICILLIN
                + APPOINTMENT_DESC_YMDH + APPOINTMENT_DESC_YMDHM;
        editedPatient = new PatientBuilder(BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).build();
        assertCommandSuccess(command, index, editedPatient);

        /* Case: clear allergies -> cleared */
        index = INDEX_FIRST_PATIENT;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_ALLERGY.getPrefix();
        Patient patientToEdit = getModel().getFilteredPatientList().get(index.getZeroBased());
        editedPatient = new PatientBuilder(patientToEdit).withAllergies().build();
        assertCommandSuccess(command, index, editedPatient);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered patient list, edit index within bounds of Giatros book and patient list -> edited */
        showPatientsWithName(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_PATIENT;
        assertTrue(index.getZeroBased() < getModel().getFilteredPatientList().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + NAME_DESC_BOB;
        patientToEdit = getModel().getFilteredPatientList().get(index.getZeroBased());
        editedPatient = new PatientBuilder(patientToEdit).withName(VALID_NAME_BOB).build();
        assertCommandSuccess(command, index, editedPatient);

        /* Case: filtered patient list, edit index within bounds of Giatros book but out of bounds of patient list
         * -> rejected
         */
        showPatientsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getGiatrosBook().getPatientList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a patient card is selected --------------------------
        */

        /* Case: selects first card in the patient list, edit a patient -> edited, card selection remains unchanged but
         * browser url changes
         */
        showAllPatients();
        index = INDEX_FIRST_PATIENT;
        selectPatient(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + ALLERGY_DESC_IBUPROFEN + APPOINTMENT_DESC_YMDH;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new patient's name
        assertCommandSuccess(command, index, AMY, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredPatientList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PATIENT.getOneBased(),
                EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PATIENT.getOneBased() + INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PATIENT.getOneBased() + INVALID_PHONE_DESC,
                Phone.MESSAGE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PATIENT.getOneBased() + INVALID_EMAIL_DESC,
                Email.MESSAGE_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PATIENT.getOneBased() + INVALID_ADDRESS_DESC,
                Address.MESSAGE_CONSTRAINTS);

        /* Case: invalid allergy -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PATIENT.getOneBased() + INVALID_ALLERGY_DESC,
                Allergy.MESSAGE_CONSTRAINTS);

        /* Case: edit a patient with new values same as another patient's values -> rejected */
        executeCommand(PatientUtil.getAddCommand(BOB));
        assertTrue(getModel().getGiatrosBook().getPatientList().contains(BOB));
        index = INDEX_FIRST_PATIENT;
        assertFalse(getModel().getFilteredPatientList().get(index.getZeroBased()).equals(BOB));
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + ALLERGY_DESC_IBUPROFEN + ALLERGY_DESC_AMPICILLIN;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PATIENT);

        /* Case: edit a patient with new values same as another patient's values but with different allergies
         * -> rejected
         */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + ALLERGY_DESC_AMPICILLIN;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PATIENT);

        /* Case: edit a patient with new values same as another patient's values but with different address -> rejected
         */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_AMY + ALLERGY_DESC_IBUPROFEN + ALLERGY_DESC_AMPICILLIN;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PATIENT);

        /* Case: edit a patient with new values same as another patient's values but with different phone -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + ALLERGY_DESC_IBUPROFEN + ALLERGY_DESC_AMPICILLIN;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PATIENT);

        /* Case: edit a patient with new values same as another patient's values but with different email -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY
                + ADDRESS_DESC_BOB + ALLERGY_DESC_IBUPROFEN + ALLERGY_DESC_AMPICILLIN;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PATIENT);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Patient, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, Patient, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Patient editedPatient) {
        assertCommandSuccess(command, toEdit, editedPatient, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the patient at index {@code toEdit} being
     * updated to values specified {@code editedPatient}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Patient editedPatient,
            Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.setPatient(expectedModel.getFilteredPatientList().get(toEdit.getZeroBased()), editedPatient);
        expectedModel.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);

        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_PATIENT_SUCCESS, editedPatient), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code GiatrosBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see GiatrosBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see GiatrosBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
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
