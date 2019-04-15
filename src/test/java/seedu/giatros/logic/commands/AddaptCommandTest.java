package seedu.giatros.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_APPOINTMENT_YMDH;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_APPOINTMENT_YMDHM;
import static seedu.giatros.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.giatros.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.giatros.logic.commands.CommandTestUtil.showPatientAtIndex;
import static seedu.giatros.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static seedu.giatros.testutil.TypicalIndexes.INDEX_SECOND_PATIENT;
import static seedu.giatros.testutil.TypicalPatients.getTypicalGiatrosBook;

import org.junit.Test;

import seedu.giatros.commons.core.Messages;
import seedu.giatros.commons.core.index.Index;
import seedu.giatros.logic.CommandHistory;
import seedu.giatros.model.GiatrosBook;
import seedu.giatros.model.Model;
import seedu.giatros.model.ModelManager;
import seedu.giatros.model.UserPrefs;
import seedu.giatros.model.appointment.Appointment;
import seedu.giatros.model.patient.Patient;
import seedu.giatros.testutil.PatientBuilder;

public class AddaptCommandTest {

    private static final String APPOINTMENT_STUB = "2019-01-01 10";

    private Model model = new ModelManager(getTypicalGiatrosBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_addAppointmentUnfilteredList_success() {
        Patient firstPatient = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        Patient editedPatient = new PatientBuilder(firstPatient).withAppointment(APPOINTMENT_STUB).build();

        AddaptCommand addaptCommand = new AddaptCommand(INDEX_FIRST_PATIENT, editedPatient.getAppointments());

        String expectedMessage = String.format(AddaptCommand.MESSAGE_ADD_APPOINTMENT_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new GiatrosBook(model.getGiatrosBook()), new UserPrefs());
        expectedModel.setPatient(firstPatient, editedPatient);
        expectedModel.commitGiatrosBook();

        assertCommandSuccess(addaptCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addDuplicateAppointmentUnfilteredList_failure() {
        Patient firstPatient = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        AddaptCommand addaptCommand = new AddaptCommand(INDEX_FIRST_PATIENT, firstPatient.getAppointments());

        assertCommandFailure(addaptCommand, model, commandHistory,
                String.format(AddaptCommand.MESSAGE_ADD_APPOINTMENT_FAILURE, firstPatient));
    }

    @Test
    public void execute_filteredList_success() {
        showPatientAtIndex(model, INDEX_FIRST_PATIENT);

        Patient firstPatient = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        Patient editedPatient = new PatientBuilder(model.getFilteredPatientList()
                .get(INDEX_FIRST_PATIENT.getZeroBased())).withAppointment(APPOINTMENT_STUB).build();

        AddaptCommand addaptCommand = new AddaptCommand(INDEX_FIRST_PATIENT, editedPatient.getAppointments());

        String expectedMessage = String.format(AddaptCommand.MESSAGE_ADD_APPOINTMENT_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new GiatrosBook(model.getGiatrosBook()), new UserPrefs());
        expectedModel.setPatient(firstPatient, editedPatient);
        expectedModel.commitGiatrosBook();

        assertCommandSuccess(addaptCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPatientIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPatientList().size() + 1);
        AddaptCommand addaptCommand = new AddaptCommand(outOfBoundIndex, new Appointment(VALID_APPOINTMENT_YMDHM));

        assertCommandFailure(addaptCommand, model, commandHistory, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of giatros book
     */
    @Test
    public void execute_invalidPatientIndexFilteredList_failure() {
        showPatientAtIndex(model, INDEX_FIRST_PATIENT);
        Index outOfBoundIndex = INDEX_SECOND_PATIENT;
        // ensures that outOfBoundIndex is still in bounds of giatros book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getGiatrosBook().getPatientList().size());

        AddaptCommand addaptCommand = new AddaptCommand(outOfBoundIndex, new Appointment(VALID_APPOINTMENT_YMDHM));

        assertCommandFailure(addaptCommand, model, commandHistory, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Patient patientToModify = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        Patient modifiedPatient = new PatientBuilder(patientToModify).withAppointment(APPOINTMENT_STUB).build();

        AddaptCommand addaptCommand = new AddaptCommand(INDEX_FIRST_PATIENT, new Appointment(APPOINTMENT_STUB));

        Model expectedModel = new ModelManager(model.getGiatrosBook(), new UserPrefs());
        expectedModel.setPatient(patientToModify, modifiedPatient);
        expectedModel.commitGiatrosBook();

        // addapt -> first patient appointment changed
        addaptCommand.execute(model, commandHistory);

        // undo -> reverts giatrosbook back to previous state and filtered patient list to show all patients
        expectedModel.undoGiatrosBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first patient modified again
        expectedModel.redoGiatrosBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPatientList().size() + 1);
        AddaptCommand addaptCommand = new AddaptCommand(outOfBoundIndex, new Appointment("2019-01-01 10"));

        // execution failed -> giatros book state not added into model
        assertCommandFailure(addaptCommand, model, commandHistory, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);

        // single giatros book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Modifies {@code Patient#appointment} from a filtered list.
     * 2. Undo the modification.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously modified patient in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the modification. This ensures {@code RedoCommand} modifies the patient object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePatientDeleted() throws Exception {
        AddaptCommand addaptCommand = new AddaptCommand(INDEX_FIRST_PATIENT, new Appointment(APPOINTMENT_STUB));
        Model expectedModel = new ModelManager(model.getGiatrosBook(), new UserPrefs());

        showPatientAtIndex(model, INDEX_SECOND_PATIENT);
        Patient patientToModify = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        Patient modifiedPatient = new PatientBuilder(patientToModify).withAppointment(APPOINTMENT_STUB).build();
        expectedModel.setPatient(patientToModify, modifiedPatient);
        expectedModel.commitGiatrosBook();

        // addapt -> modifies second patient in unfiltered patient list / first patient in filtered patient list
        addaptCommand.execute(model, commandHistory);

        // undo -> reverts giatrosbook back to previous state and filtered patient list to show all patients
        expectedModel.undoGiatrosBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> modifies same second patient in unfiltered patient list
        expectedModel.redoGiatrosBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final AddaptCommand standardCommand = new AddaptCommand(INDEX_FIRST_PATIENT,
                new Appointment(VALID_APPOINTMENT_YMDH));

        // same values -> returns true
        AddaptCommand commandWithSameValues = new AddaptCommand(INDEX_FIRST_PATIENT,
                new Appointment(VALID_APPOINTMENT_YMDH));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddaptCommand(INDEX_SECOND_PATIENT,
                new Appointment(VALID_APPOINTMENT_YMDH))));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new AddaptCommand(INDEX_FIRST_PATIENT,
                new Appointment(VALID_APPOINTMENT_YMDHM))));
    }

}
