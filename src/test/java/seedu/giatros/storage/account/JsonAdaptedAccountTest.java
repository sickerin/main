package seedu.giatros.storage.account;

import static org.junit.Assert.assertEquals;
import static seedu.giatros.storage.JsonAdaptedAccount.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.giatros.testutil.TypicalAccounts.BABA;

import org.junit.Test;

import seedu.giatros.commons.exceptions.IllegalValueException;
import seedu.giatros.model.account.Password;
import seedu.giatros.model.account.Username;
import seedu.giatros.model.patient.Name;
import seedu.giatros.storage.JsonAdaptedAccount;
import seedu.giatros.testutil.Assert;

public class JsonAdaptedAccountTest {
    private static final String INVALID_NAME = "M@ANAGER1";
    private static final String INVALID_USERNAME = "MANAG ER1";
    private static final String INVALID_PASSWORD = "123 123";

    private static final String VALID_NAME = BABA.getName().toString();
    private static final String VALID_USERNAME = BABA.getUsername().toString();
    private static final String VALID_PASSWORD = BABA.getPassword().toString();

    @Test
    public void toModelType_validAccountDetails_returnsAccount() throws Exception {
        JsonAdaptedAccount account = new JsonAdaptedAccount(BABA);
        assertEquals(BABA, account.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedAccount account =
                new JsonAdaptedAccount(INVALID_NAME, VALID_USERNAME, VALID_PASSWORD);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, account::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedAccount account = new JsonAdaptedAccount(null, VALID_USERNAME, VALID_PASSWORD);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, account::toModelType);
    }

    @Test
    public void toModelType_invalidUsername_throwsIllegalValueException() {
        JsonAdaptedAccount account =
                new JsonAdaptedAccount(VALID_NAME, INVALID_USERNAME, VALID_PASSWORD);
        String expectedMessage = Username.MESSAGE_USERNAME_CONSTRAINT;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, account::toModelType);
    }

    @Test
    public void toModelType_nullUsername_throwsIllegalValueException() {
        JsonAdaptedAccount account = new JsonAdaptedAccount(VALID_NAME, null, VALID_PASSWORD);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Username.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, account::toModelType);
    }

    @Test
    public void toModelType_invalidPassword_throwsIllegalValueException() {
        JsonAdaptedAccount account =
                new JsonAdaptedAccount(VALID_NAME, VALID_USERNAME, INVALID_PASSWORD);
        String expectedMessage = Password.MESSAGE_PASSWORD_CONSTRAINT;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, account::toModelType);
    }

    @Test
    public void toModelType_nullPassword_throwsIllegalValueException() {
        JsonAdaptedAccount account = new JsonAdaptedAccount(VALID_NAME, VALID_USERNAME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Password.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, account::toModelType);
    }
}
