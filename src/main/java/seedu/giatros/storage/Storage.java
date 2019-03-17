package seedu.giatros.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.giatros.commons.exceptions.DataConversionException;
import seedu.giatros.model.ReadOnlyGiatrosBook;
import seedu.giatros.model.ReadOnlyUserPrefs;
import seedu.giatros.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends GiatrosBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getGiatrosBookFilePath();

    @Override
    Optional<ReadOnlyGiatrosBook> readGiatrosBook() throws DataConversionException, IOException;

    @Override
    void saveGiatrosBook(ReadOnlyGiatrosBook giatrosBook) throws IOException;

}
