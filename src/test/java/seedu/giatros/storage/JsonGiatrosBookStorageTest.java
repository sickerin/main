package seedu.giatros.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.giatros.testutil.TypicalPatients.ALICE;
import static seedu.giatros.testutil.TypicalPatients.HOON;
import static seedu.giatros.testutil.TypicalPatients.IDA;
import static seedu.giatros.testutil.TypicalPatients.getTypicalGiatrosBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.giatros.commons.exceptions.DataConversionException;
import seedu.giatros.model.GiatrosBook;
import seedu.giatros.model.ReadOnlyGiatrosBook;

public class JsonGiatrosBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonGiatrosBookStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readGiatrosBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readGiatrosBook(null);
    }

    private java.util.Optional<ReadOnlyGiatrosBook> readGiatrosBook(String filePath) throws Exception {
        return new JsonGiatrosBookStorage(Paths.get(filePath)).readGiatrosBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readGiatrosBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readGiatrosBook("notJsonFormatGiatrosBook.json");

        // IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
        // That means you should not have more than one exception test in one method
    }

    @Test
    public void readGiatrosBook_invalidPatientGiatrosBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readGiatrosBook("invalidPatientGiatrosBook.json");
    }

    @Test
    public void readGiatrosBook_invalidAndValidPatientGiatrosBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readGiatrosBook("invalidAndValidPatientGiatrosBook.json");
    }

    @Test
    public void readAndSaveGiatrosBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempGiatrosBook.json");
        GiatrosBook original = getTypicalGiatrosBook();
        JsonGiatrosBookStorage jsonGiatrosBookStorage = new JsonGiatrosBookStorage(filePath);

        // Save in new file and read back
        jsonGiatrosBookStorage.saveGiatrosBook(original, filePath);
        ReadOnlyGiatrosBook readBack = jsonGiatrosBookStorage.readGiatrosBook(filePath).get();
        assertEquals(original, new GiatrosBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPatient(HOON);
        original.removePatient(ALICE);
        jsonGiatrosBookStorage.saveGiatrosBook(original, filePath);
        readBack = jsonGiatrosBookStorage.readGiatrosBook(filePath).get();
        assertEquals(original, new GiatrosBook(readBack));

        // Save and read without specifying file path
        original.addPatient(IDA);
        jsonGiatrosBookStorage.saveGiatrosBook(original); // file path not specified
        readBack = jsonGiatrosBookStorage.readGiatrosBook().get(); // file path not specified
        assertEquals(original, new GiatrosBook(readBack));

    }

    @Test
    public void saveGiatrosBook_nullGiatrosBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveGiatrosBook(null, "SomeFile.json");
    }

    /**
     * Saves {@code giatrosBook} at the specified {@code filePath}.
     */
    private void saveGiatrosBook(ReadOnlyGiatrosBook giatrosBook, String filePath) {
        try {
            new JsonGiatrosBookStorage(Paths.get(filePath))
                    .saveGiatrosBook(giatrosBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveGiatrosBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveGiatrosBook(new GiatrosBook(), null);
    }
}
