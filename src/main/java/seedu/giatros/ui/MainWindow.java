package seedu.giatros.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.giatros.commons.core.GuiSettings;
import seedu.giatros.commons.core.LogsCenter;
import seedu.giatros.commons.events.ui.ToggleSidePanelVisibilityEvent;
import seedu.giatros.logic.Logic;
import seedu.giatros.logic.commands.CommandResult;
import seedu.giatros.logic.commands.exceptions.CommandException;
import seedu.giatros.logic.parser.exceptions.ParseException;
import seedu.giatros.ui.account.AccountListPanel;
import seedu.giatros.ui.account.UsernameDisplay;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private BrowserPanel browserPanel;
    private PatientListPanel patientListPanel;
    private AccountListPanel usernamePanel;
    private AccountListPanel accountListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private UsernameDisplay usernameDisplay;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private Pane usernameDisplayPlaceholder;

    @FXML
    private StackPane dataListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();
        registerAsAnEventHandler(this);

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {

        accountListPanel = new AccountListPanel(logic.getGiatrosBook().getAccountList());
        patientListPanel = new PatientListPanel(logic.getFilteredPatientList(), logic.selectedPatientProperty(),
                logic::setSelectedPatient);

        browserPanel = new BrowserPanel(logic.selectedPatientProperty());
        browserPlaceholder.getChildren().add(browserPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getGiatrosBookFilePath(), logic.getGiatrosBook());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand, logic.getHistory());
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        usernamePanel = new AccountListPanel(logic.getFilteredAccountList());
        usernameDisplay = new UsernameDisplay();
        // Centralize the width
        usernameDisplay.getRoot().layoutXProperty().bind(usernameDisplayPlaceholder.widthProperty()
                .subtract(usernameDisplay.getRoot().widthProperty())
                .divide(2));
        // Centralize the height
        usernameDisplay.getRoot().layoutYProperty().bind(usernameDisplayPlaceholder.heightProperty()
                .subtract(usernameDisplay.getRoot().heightProperty())
                .divide(2));
        usernameDisplayPlaceholder.getChildren().add(usernameDisplay.getRoot());

        dataListPanelPlaceholder.getChildren().add(patientListPanel.getRoot());
        dataListPanelPlaceholder.setVisible(false);
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public PatientListPanel getPatientListPanel() {
        return patientListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.giatros.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
    @Subscribe
    private void handleToggleSidePanelVisibilityEvent(ToggleSidePanelVisibilityEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        dataListPanelPlaceholder.getChildren().clear();
        if (usernameDisplay.getRoot().toString().indexOf("MANAGER") != -1) {
            switchToAccountInfoSidePanel();
        } else {
            switchToPatientInfoSidePanel();
        }
        dataListPanelPlaceholder.setVisible(event.isVisible);
    }

    /**
     * Input the side panel with account management information
     */
    private void switchToAccountInfoSidePanel() {
        dataListPanelPlaceholder.getChildren().add(accountListPanel.getRoot());
    }

    /**
     * Input the side panel with patient management information
     */
    private void switchToPatientInfoSidePanel() {
        dataListPanelPlaceholder.getChildren().add(patientListPanel.getRoot());
    }
}
