package seedu.giatros.logic.commands;

import static seedu.giatros.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.giatros.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.giatros.testutil.TypicalPatients.getTypicalGiatrosBook;

import org.junit.Before;
import org.junit.Test;

import seedu.giatros.logic.CommandHistory;
import seedu.giatros.model.Model;
import seedu.giatros.model.ModelManager;
import seedu.giatros.model.UserPrefs;
import seedu.giatros.model.patient.Patient;
import seedu.giatros.testutil.PatientBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalGiatrosBook(), new UserPrefs());
    }

    @Test
    public void execute_newPatient_success() {
        Patient validPatient = new PatientBuilder().build();

        Model expectedModel = new ModelManager(model.getGiatrosBook(), new UserPrefs());
        expectedModel.addPatient(validPatient);
        expectedModel.commitGiatrosBook();

        assertCommandSuccess(new AddCommand(validPatient), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validPatient), expectedModel);
    }

    @Test
    public void execute_duplicatePatient_throwsCommandException() {
        Patient patientInList = model.getGiatrosBook().getPatientList().get(0);
        assertCommandFailure(new AddCommand(patientInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_PATIENT);
    }

}
