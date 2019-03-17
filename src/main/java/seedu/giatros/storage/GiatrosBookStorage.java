package seedu.giatros.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.giatros.commons.exceptions.DataConversionException;
import seedu.giatros.model.GiatrosBook;
import seedu.giatros.model.ReadOnlyGiatrosBook;

/**
 * Represents a storage for {@link GiatrosBook}.
 */
public interface GiatrosBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getGiatrosBookFilePath();

    /**
     * Returns GiatrosBook data as a {@link ReadOnlyGiatrosBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyGiatrosBook> readGiatrosBook() throws DataConversionException, IOException;

    /**
     * @see #getGiatrosBookFilePath()
     */
    Optional<ReadOnlyGiatrosBook> readGiatrosBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyGiatrosBook} to the storage.
     * @param giatrosBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveGiatrosBook(ReadOnlyGiatrosBook giatrosBook) throws IOException;

    /**
     * @see #saveGiatrosBook(ReadOnlyGiatrosBook)
     */
    void saveGiatrosBook(ReadOnlyGiatrosBook giatrosBook, Path filePath) throws IOException;

}
