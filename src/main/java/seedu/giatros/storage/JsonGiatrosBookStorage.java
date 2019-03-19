package seedu.giatros.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.giatros.commons.core.LogsCenter;
import seedu.giatros.commons.exceptions.DataConversionException;
import seedu.giatros.commons.exceptions.IllegalValueException;
import seedu.giatros.commons.util.FileUtil;
import seedu.giatros.commons.util.JsonUtil;
import seedu.giatros.model.ReadOnlyGiatrosBook;

/**
 * A class to access GiatrosBook data stored as a json file on the hard disk.
 */
public class JsonGiatrosBookStorage implements GiatrosBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonGiatrosBookStorage.class);

    private Path filePath;

    public JsonGiatrosBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getGiatrosBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyGiatrosBook> readGiatrosBook() throws DataConversionException {
        return readGiatrosBook(filePath);
    }

    /**
     * Similar to {@link #readGiatrosBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyGiatrosBook> readGiatrosBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableGiatrosBook> jsonGiatrosBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableGiatrosBook.class);
        if (!jsonGiatrosBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonGiatrosBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveGiatrosBook(ReadOnlyGiatrosBook giatrosBook) throws IOException {
        saveGiatrosBook(giatrosBook, filePath);
    }

    /**
     * Similar to {@link #saveGiatrosBook(ReadOnlyGiatrosBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveGiatrosBook(ReadOnlyGiatrosBook giatrosBook, Path filePath) throws IOException {
        requireNonNull(giatrosBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableGiatrosBook(giatrosBook), filePath);
    }

}
