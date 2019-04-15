package seedu.giatros.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.giatros.commons.core.index.Index;
import seedu.giatros.logic.CommandHistory;
import seedu.giatros.logic.commands.exceptions.CommandException;
import seedu.giatros.model.GiatrosBook;
import seedu.giatros.model.Model;
import seedu.giatros.model.patient.NameContainsKeywordsPredicate;
import seedu.giatros.model.patient.Patient;
import seedu.giatros.testutil.EditPatientDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_ALLERGY_AMPICILLIN = "ampicillin";
    public static final String VALID_ALLERGY_IBUPROFEN = "ibuprofen";
    public static final String VALID_ALLERGY_AMY = "ibuprofen";
    public static final String VALID_ALLERGY_BOB = "paracetamol";
    public static final String VALID_APPOINTMENT_YMDH = "2019-01-01 10";
    public static final String VALID_APPOINTMENT_YMDHM = "2019-01-01 10:10";
    public static final String VALID_APPOINTMENT_BOB = "2019-04-19 10:00";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String ALLERGY_DESC_IBUPROFEN = " " + PREFIX_ALLERGY + VALID_ALLERGY_IBUPROFEN;
    public static final String ALLERGY_DESC_AMPICILLIN = " " + PREFIX_ALLERGY + VALID_ALLERGY_AMPICILLIN;
    public static final String APPOINTMENT_DESC_YMDH = " " + PREFIX_APPOINTMENT + VALID_APPOINTMENT_YMDH;
    public static final String APPOINTMENT_DESC_YMDHM = " " + PREFIX_APPOINTMENT + VALID_APPOINTMENT_YMDHM;


    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_ALLERGY_DESC = " " + PREFIX_ALLERGY + "*allergy"; // '*' not allowed in allergies
    public static final String INVALID_APPOINTMENT_DESC = " "
            + PREFIX_APPOINTMENT + "2019"; // it must be in one of the accepted formats

    public static final String VALID_USERNAME = "baba";
    public static final String VALID_PASSWORD = "1122qq";
    public static final String VALID_NAME = "baba";
    public static final String INVALID_USERNAME = "bab b";
    public static final String INVALID_PASSWORD = "1122 qq";
    public static final String INVALID_NAME = "bab @ b";

    public static final String VALID_DEST_PATH = System.getProperty("user.home") + "/Desktop";
    public static final String INVALID_DEST_PATH = " ";

    public static final String PREFIX_WITH_VALID_USERNAME = " " + PREFIX_ID + VALID_USERNAME;
    public static final String PREFIX_WITH_VALID_PASSWORD = " " + PREFIX_PASSWORD + VALID_PASSWORD;
    public static final String PREFIX_WITH_VALID_NAME = " " + PREFIX_NAME + VALID_NAME;

    public static final String PREFIX_WITH_INVALID_USERNAME = " " + PREFIX_ID + INVALID_USERNAME;
    public static final String PREFIX_WITH_INVALID_PASSWORD = " " + PREFIX_PASSWORD + INVALID_PASSWORD;
    public static final String PREFIX_WITH_INVALID_NAME = " " + PREFIX_NAME + INVALID_NAME;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPatientDescriptor DESC_AMY;
    public static final EditCommand.EditPatientDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPatientDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withAllergies(VALID_ALLERGY_IBUPROFEN).build();
        DESC_BOB = new EditPatientDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withAllergies(VALID_ALLERGY_AMPICILLIN, VALID_ALLERGY_IBUPROFEN)
                .withAppointments(VALID_APPOINTMENT_BOB).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            CommandResult expectedCommandResult, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandHistory, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, actualCommandHistory, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the Giatros book, filtered patient list and selected patient in {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        GiatrosBook expectedGiatrosBook = new GiatrosBook(actualModel.getGiatrosBook());
        List<Patient> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPatientList());
        Patient expectedSelectedPatient = actualModel.getSelectedPatient();

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedGiatrosBook, actualModel.getGiatrosBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredPatientList());
            assertEquals(expectedSelectedPatient, actualModel.getSelectedPatient());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the patient at the given {@code targetIndex} in the
     * {@code model}'s Giatros book.
     */
    public static void showPatientAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPatientList().size());

        Patient patient = model.getFilteredPatientList().get(targetIndex.getZeroBased());
        final String[] splitName = patient.getName().fullName.split("\\s+");
        model.updateFilteredPatientList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPatientList().size());
    }

    /**
     * Deletes the first patient in {@code model}'s filtered list from {@code model}'s Giatros book.
     */
    public static void deleteFirstPatient(Model model) {
        Patient firstPatient = model.getFilteredPatientList().get(0);
        model.deletePatient(firstPatient);
        model.commitGiatrosBook();
    }

}
