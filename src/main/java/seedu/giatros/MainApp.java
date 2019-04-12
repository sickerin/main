package seedu.giatros;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.giatros.commons.core.Config;
import seedu.giatros.commons.core.LogsCenter;
import seedu.giatros.commons.core.Version;
import seedu.giatros.commons.exceptions.DataConversionException;
import seedu.giatros.commons.util.ConfigUtil;
import seedu.giatros.commons.util.StringUtil;
import seedu.giatros.logic.Logic;
import seedu.giatros.logic.LogicManager;
import seedu.giatros.model.GiatrosBook;
import seedu.giatros.model.Model;
import seedu.giatros.model.ModelManager;
import seedu.giatros.model.ReadOnlyGiatrosBook;
import seedu.giatros.model.ReadOnlyUserPrefs;
import seedu.giatros.model.UserPrefs;
import seedu.giatros.model.util.SampleDataUtil;
import seedu.giatros.storage.GiatrosBookStorage;
import seedu.giatros.storage.JsonGiatrosBookStorage;
import seedu.giatros.storage.JsonUserPrefsStorage;
import seedu.giatros.storage.Storage;
import seedu.giatros.storage.StorageManager;
import seedu.giatros.storage.UserPrefsStorage;
import seedu.giatros.ui.Ui;
import seedu.giatros.ui.UiManager;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 4, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing GiatrosBook ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        GiatrosBookStorage giatrosBookStorage = new JsonGiatrosBookStorage(userPrefs.getGiatrosBookFilePath());
        storage = new StorageManager(giatrosBookStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s giatros book and {@code userPrefs}. <br>
     * The data from the sample giatros book will be used instead if {@code storage}'s giatros book is not found,
     * or an empty giatros book will be used instead if errors occur when reading {@code storage}'s giatros book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyGiatrosBook> giatrosBookOptional;
        ReadOnlyGiatrosBook initialData;
        try {
            giatrosBookOptional = storage.readGiatrosBook();
            if (!giatrosBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample GiatrosBook");
            }
            initialData = giatrosBookOptional.orElseGet(SampleDataUtil::getSampleGiatrosBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty GiatrosBook");
            initialData = new GiatrosBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty GiatrosBook");
            initialData = new GiatrosBook();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty GiatrosBook");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting GiatrosBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Giatros Book ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
