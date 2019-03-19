package seedu.giatros.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.giatros.commons.core.LogsCenter;
import seedu.giatros.commons.exceptions.DataConversionException;
import seedu.giatros.model.ReadOnlyGiatrosBook;
import seedu.giatros.model.ReadOnlyUserPrefs;
import seedu.giatros.model.UserPrefs;

/**
 * Manages storage of GiatrosBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private GiatrosBookStorage giatrosBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(GiatrosBookStorage giatrosBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.giatrosBookStorage = giatrosBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ GiatrosBook methods ==============================

    @Override
    public Path getGiatrosBookFilePath() {
        return giatrosBookStorage.getGiatrosBookFilePath();
    }

    @Override
    public Optional<ReadOnlyGiatrosBook> readGiatrosBook() throws DataConversionException, IOException {
        return readGiatrosBook(giatrosBookStorage.getGiatrosBookFilePath());
    }

    @Override
    public Optional<ReadOnlyGiatrosBook> readGiatrosBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return giatrosBookStorage.readGiatrosBook(filePath);
    }

    @Override
    public void saveGiatrosBook(ReadOnlyGiatrosBook giatrosBook) throws IOException {
        saveGiatrosBook(giatrosBook, giatrosBookStorage.getGiatrosBookFilePath());
    }

    @Override
    public void saveGiatrosBook(ReadOnlyGiatrosBook giatrosBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        giatrosBookStorage.saveGiatrosBook(giatrosBook, filePath);
    }

}
