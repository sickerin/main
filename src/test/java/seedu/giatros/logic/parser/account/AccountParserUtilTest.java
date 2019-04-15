package seedu.giatros.logic.parser.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static seedu.giatros.logic.commands.CommandTestUtil.INVALID_NAME;
import static seedu.giatros.logic.commands.CommandTestUtil.INVALID_PASSWORD;
import static seedu.giatros.logic.commands.CommandTestUtil.INVALID_USERNAME;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_NAME;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_PASSWORD;
import static seedu.giatros.logic.commands.CommandTestUtil.VALID_USERNAME;
import static seedu.giatros.testutil.Assert.assertThrows;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.giatros.logic.parser.exceptions.ParseException;
import seedu.giatros.model.account.Name;
import seedu.giatros.model.account.Password;
import seedu.giatros.model.account.Username;

public class AccountParserUtilTest {
    private static final String WHITESPACE = " \t\r\n";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseUsername_invalid_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        AccountParserUtil.parseUsername(INVALID_USERNAME);
    }

    @Test
    public void parsePassword_invalid_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        AccountParserUtil.parsePassword(INVALID_PASSWORD);
    }

    @Test
    public void parseName_invalid_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        AccountParserUtil.parseUsername(INVALID_NAME);
    }

    @Test
    public void parseUsername_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> AccountParserUtil.parseUsername(null));
    }

    @Test
    public void parsePassword_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> AccountParserUtil.parsePassword(null));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> AccountParserUtil.parseName(null));
    }

    @Test
    public void parseUsername_validValueWithoutWhitespace_returnsName() throws Exception {
        Username expectedUsername = new Username(VALID_USERNAME);
        assertEquals(expectedUsername, AccountParserUtil.parseUsername(VALID_USERNAME));
        assertNotNull(AccountParserUtil.parseUsername(VALID_USERNAME));
    }

    @Test
    public void parseUsername_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String usernameWithWhitespace = WHITESPACE + VALID_USERNAME + WHITESPACE;
        Username expectedUsername = new Username(VALID_USERNAME);
        assertEquals(expectedUsername, AccountParserUtil.parseUsername(usernameWithWhitespace));
        assertNotNull(AccountParserUtil.parseUsername(usernameWithWhitespace));
    }

    @Test
    public void parsePassword_validValueWithoutWhitespace_returnsName() throws Exception {
        Password expectedPassword = new Password(VALID_PASSWORD);
        assertEquals(expectedPassword, AccountParserUtil.parsePassword(VALID_PASSWORD));
        assertNotNull(AccountParserUtil.parsePassword(VALID_PASSWORD));
    }

    @Test
    public void parsePassword_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String passwordWithWhitespace = WHITESPACE + VALID_PASSWORD + WHITESPACE;
        Password expectedPassword = new Password(VALID_PASSWORD);
        assertEquals(expectedPassword, AccountParserUtil.parsePassword(passwordWithWhitespace));
        assertNotNull(AccountParserUtil.parsePassword(passwordWithWhitespace));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, AccountParserUtil.parseName(VALID_NAME));
        assertNotNull(AccountParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValue_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, AccountParserUtil.parseName(nameWithWhitespace));
        assertNotNull(AccountParserUtil.parseName(nameWithWhitespace));
    }
}
