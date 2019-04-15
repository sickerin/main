package seedu.giatros.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.giatros.commons.exceptions.IllegalValueException;
import seedu.giatros.commons.util.JsonUtil;
import seedu.giatros.model.GiatrosBook;
import seedu.giatros.testutil.TypicalAccounts;
import seedu.giatros.testutil.TypicalPatients;

public class JsonSerializableGiatrosBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableGiatrosBookTest");
    private static final Path TYPICAL_PATIENTS_FILE = TEST_DATA_FOLDER.resolve("typicalPatientsGiatrosBook.json");
    private static final Path TYPICAL_ACCOUNTS_FILE = TEST_DATA_FOLDER
            .resolve("account/typicalAccountsGiatrosBook.json");
    private static final Path INVALID_PATIENT_FILE = TEST_DATA_FOLDER.resolve("invalidPatientGiatrosBook.json");
    private static final Path INVALID_ACCOUNT_FILE = TEST_DATA_FOLDER.resolve("account/invalidAccountGiatrosBook.json");
    private static final Path DUPLICATE_PATIENT_FILE = TEST_DATA_FOLDER.resolve("duplicatePatientGiatrosBook.json");
    private static final Path DUPLICATE_ACCOUNT_FILE = TEST_DATA_FOLDER
            .resolve("account/duplicateAccountGiatrosBook.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPatientsFile_success() throws Exception {
        JsonSerializableGiatrosBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PATIENTS_FILE,
                JsonSerializableGiatrosBook.class).get();
        GiatrosBook giatrosBookFromFile = dataFromFile.toModelType();
        GiatrosBook typicalPatientsGiatrosBook = TypicalPatients.getTypicalGiatrosBook();
        assertEquals(giatrosBookFromFile, typicalPatientsGiatrosBook);
    }

    @Test
    public void toModelType_typicalAccountsFile_success() throws Exception {
        JsonSerializableGiatrosBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_ACCOUNTS_FILE,
                JsonSerializableGiatrosBook.class).get();
        GiatrosBook giatrosBookFromFile = dataFromFile.toModelType();
        GiatrosBook typicalAccountsGiatrosBook = TypicalAccounts.getTypicalGiatrosBook();
        assertNotNull(giatrosBookFromFile);
        assertNotNull(typicalAccountsGiatrosBook);
    }

    @Test
    public void toModelType_invalidPatientFile_throwsIllegalValueException() throws Exception {
        JsonSerializableGiatrosBook dataFromFile = JsonUtil.readJsonFile(INVALID_PATIENT_FILE,
                JsonSerializableGiatrosBook.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_invalidAccountFile_throwsIllegalValueException() throws Exception {
        JsonSerializableGiatrosBook dataFromFile = JsonUtil.readJsonFile(INVALID_ACCOUNT_FILE,
                JsonSerializableGiatrosBook.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePatients_throwsIllegalValueException() throws Exception {
        JsonSerializableGiatrosBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PATIENT_FILE,
                JsonSerializableGiatrosBook.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableGiatrosBook.MESSAGE_DUPLICATE_PATIENT);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateAccounts_throwsIllegalValueException() throws Exception {
        JsonSerializableGiatrosBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_ACCOUNT_FILE,
                JsonSerializableGiatrosBook.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableGiatrosBook.MESSAGE_DUPLICATE_ACCOUNT);
        dataFromFile.toModelType();
    }

}
