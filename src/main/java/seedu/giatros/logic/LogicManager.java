package seedu.giatros.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.giatros.commons.core.GuiSettings;
import seedu.giatros.commons.core.LogsCenter;
import seedu.giatros.commons.core.Messages;
import seedu.giatros.commons.core.session.UserSession;
import seedu.giatros.logic.commands.AddCommand;
import seedu.giatros.logic.commands.AddallCommand;
import seedu.giatros.logic.commands.AddaptCommand;
import seedu.giatros.logic.commands.ClearCommand;
import seedu.giatros.logic.commands.Command;
import seedu.giatros.logic.commands.CommandResult;
import seedu.giatros.logic.commands.DeleteCommand;
import seedu.giatros.logic.commands.EditCommand;
import seedu.giatros.logic.commands.ExitCommand;
import seedu.giatros.logic.commands.ExportCommand;
import seedu.giatros.logic.commands.FindCommand;
import seedu.giatros.logic.commands.HelpCommand;
import seedu.giatros.logic.commands.HistoryCommand;
import seedu.giatros.logic.commands.ListCommand;
import seedu.giatros.logic.commands.RedoCommand;
import seedu.giatros.logic.commands.RemallCommand;
import seedu.giatros.logic.commands.RemaptCommand;
import seedu.giatros.logic.commands.SelectCommand;
import seedu.giatros.logic.commands.UndoCommand;
import seedu.giatros.logic.commands.account.LoginCommand;
import seedu.giatros.logic.commands.account.LogoutCommand;
import seedu.giatros.logic.commands.account.RegisterCommand;
import seedu.giatros.logic.commands.exceptions.CommandException;
import seedu.giatros.logic.parser.GiatrosBookParser;
import seedu.giatros.logic.parser.exceptions.ParseException;
import seedu.giatros.model.Model;
import seedu.giatros.model.ReadOnlyGiatrosBook;
import seedu.giatros.model.account.Account;
import seedu.giatros.model.patient.Patient;
import seedu.giatros.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    public static final String MANAGER_USERNAME = "MANAGER";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CommandHistory history;
    private final GiatrosBookParser giatrosBookParser;
    private boolean giatrosBookModified;

    private boolean isTest = false;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        history = new CommandHistory();
        giatrosBookParser = new GiatrosBookParser();

        // Set giatrosBookModified to true whenever the models' Giatros book is modified.
        model.getGiatrosBook().addListener(observable -> giatrosBookModified = true);
    }

    @Override
    public boolean isGuestCommand(Command command) {
        return command instanceof LoginCommand || command instanceof HelpCommand
                || command instanceof ExitCommand;
    }

    @Override
    public boolean isStaffCommand(Command command) { //the commands below are allowed for normal staff
        return command instanceof LoginCommand || command instanceof HelpCommand
                    || command instanceof AddCommand || command instanceof AddallCommand
                    || command instanceof DeleteCommand || command instanceof EditCommand
                    || command instanceof AddaptCommand || command instanceof ClearCommand
                    || command instanceof FindCommand || command instanceof HistoryCommand
                    || command instanceof ListCommand || command instanceof RedoCommand
                    || command instanceof RemallCommand || command instanceof RemaptCommand
                    || command instanceof SelectCommand || command instanceof LogoutCommand
                    || command instanceof UndoCommand || command instanceof ExitCommand
                    || command instanceof ExportCommand;
    }

    @Override
    public boolean isManagerCommand(Command command) {
        return command instanceof RegisterCommand || command instanceof HelpCommand
                || command instanceof LogoutCommand || command instanceof ExitCommand;
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        giatrosBookModified = false;

        CommandResult commandResult;
        try {
            Command command = giatrosBookParser.parseCommand(commandText);

            if (!isGuestCommand(command) && !UserSession.isAuthenticated()) {
                throw new CommandException(Messages.MESSAGE_COMMAND_RESTRICTED);
            }
            if (!isStaffCommand(command) && UserSession.isAuthenticated() && !UserSession.getAccount()
                    .getUsername().toString().equals(MANAGER_USERNAME)) {
                throw new CommandException(Messages.MESSAGE_COMMAND_RESTRICTED);
            }
            if (!isManagerCommand(command) && UserSession.isAuthenticated() && UserSession.getAccount()
                    .getUsername().toString().equals(MANAGER_USERNAME)) {
                throw new CommandException(Messages.MESSAGE_COMMAND_RESTRICTED);
            }

            commandResult = command.execute(model, history);
        } finally {
            history.add(commandText);
        }

        if (giatrosBookModified) {
            logger.info("Giatros book modified, saving to file.");
            try {
                storage.saveGiatrosBook(model.getGiatrosBook());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        return commandResult;
    }

    @Override
    public ReadOnlyGiatrosBook getGiatrosBook() {
        return model.getGiatrosBook();
    }

    @Override
    public ObservableList<Patient> getFilteredPatientList() {
        return model.getFilteredPatientList();
    }

    @Override
    public ObservableList<Account> getFilteredAccountList() {
        return model.getFilteredAccountList();
    }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public Path getGiatrosBookFilePath() {
        return model.getGiatrosBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ReadOnlyProperty<Patient> selectedPatientProperty() {
        return model.selectedPatientProperty();
    }

    @Override
    public void setSelectedPatient(Patient patient) {
        model.setSelectedPatient(patient);
    }
}
