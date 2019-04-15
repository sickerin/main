package seedu.giatros.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.giatros.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.giatros.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.giatros.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.giatros.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.giatros.commons.core.EventsCenter;
import seedu.giatros.commons.events.ui.accounts.LoginEvent;
import seedu.giatros.logic.commands.AddCommand;
import seedu.giatros.logic.commands.AddallCommand;
import seedu.giatros.logic.commands.AddaptCommand;
import seedu.giatros.logic.commands.ClearCommand;
import seedu.giatros.logic.commands.DeleteCommand;
import seedu.giatros.logic.commands.EditCommand;
import seedu.giatros.logic.commands.EditCommand.EditPatientDescriptor;
import seedu.giatros.logic.commands.ExitCommand;
import seedu.giatros.logic.commands.FindCommand;
import seedu.giatros.logic.commands.HelpCommand;
import seedu.giatros.logic.commands.HistoryCommand;
import seedu.giatros.logic.commands.ListCommand;
import seedu.giatros.logic.commands.RedoCommand;
import seedu.giatros.logic.commands.RemallCommand;
import seedu.giatros.logic.commands.RemaptCommand;
import seedu.giatros.logic.commands.SelectCommand;
import seedu.giatros.logic.commands.UndoCommand;
import seedu.giatros.logic.parser.exceptions.ParseException;
import seedu.giatros.model.allergy.Allergy;
import seedu.giatros.model.appointment.Appointment;
import seedu.giatros.model.patient.NameContainsKeywordsPredicate;
import seedu.giatros.model.patient.Patient;
import seedu.giatros.testutil.EditPatientDescriptorBuilder;
import seedu.giatros.testutil.PatientBuilder;
import seedu.giatros.testutil.PatientUtil;
import seedu.giatros.ui.testutil.AccountCreator;

public class GiatrosBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final GiatrosBookParser parser = new GiatrosBookParser();

    @BeforeClass
    public static void setupBeforeClass() {
        EventsCenter.getInstance().post(new LoginEvent(new AccountCreator("staff").build()));
    }

    @Test
    public void parseCommand_add() throws Exception {
        Patient patient = new PatientBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PatientUtil.getAddCommand(patient));
        assertEquals(new AddCommand(patient), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PATIENT.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PATIENT), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Patient patient = new PatientBuilder().build();
        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder(patient).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PATIENT.getOneBased() + " " + PatientUtil.getEditPatientDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PATIENT, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PATIENT.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PATIENT), command);
    }

    @Test
    public void parseCommand_addall() throws Exception {
        final Allergy allergy = new Allergy("someAllergy");
        AddallCommand command = (AddallCommand) parser.parseCommand(AddallCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PATIENT.getOneBased() + " " + PREFIX_ALLERGY + allergy.allergyName);
        assertEquals(new AddallCommand(INDEX_FIRST_PATIENT, allergy), command);
    }

    @Test
    public void parseCommand_remall() throws Exception {
        final Allergy allergy = new Allergy("someAllergy");
        RemallCommand command = (RemallCommand) parser.parseCommand(RemallCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PATIENT.getOneBased() + " " + PREFIX_ALLERGY + allergy.allergyName);
        assertEquals(new RemallCommand(INDEX_FIRST_PATIENT, allergy), command);
    }

    @Test
    public void parseCommand_addapt() throws Exception {
        final Appointment appointment = new Appointment("2019-01-01 10");
        AddaptCommand command = (AddaptCommand) parser.parseCommand(AddaptCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PATIENT.getOneBased() + " " + PREFIX_APPOINTMENT + appointment.appointmentString);
        assertEquals(new AddaptCommand(INDEX_FIRST_PATIENT, appointment), command);
    }

    @Test
    public void parseCommand_remapt() throws Exception {
        final Appointment appointment = new Appointment("2019-01-01 10");
        RemaptCommand command = (RemaptCommand) parser.parseCommand(RemaptCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PATIENT.getOneBased() + " " + PREFIX_APPOINTMENT + appointment.appointmentString);
        assertEquals(new RemaptCommand(INDEX_FIRST_PATIENT, appointment), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
