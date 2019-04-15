package seedu.giatros.commons.core.session;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import seedu.giatros.model.account.Account;
import seedu.giatros.model.account.Name;
import seedu.giatros.model.account.Password;
import seedu.giatros.model.account.Username;
import seedu.giatros.ui.testutil.AccountCreator;

/**
 * Since this is a local desktop application, we assume the multiplicity of 0...1 user will be logged in at any time.
 * Thus, it makes sense to allow only one user sessions as it is impossible to have multi users in this project's
 * context.
 */
public class UserSessionTest {

    @Before
    public void setUp() {
        UserSession.create(new AccountCreator("staff").build());
    }

    @AfterClass
    public static void tearDown() {
        // Make sure to unset the session, in case the last test case does not logs out
        UserSession.destroy();
    }

    @Test
    public void getAccount_userSessionEmpty_returnNull() {
        UserSession.destroy();
        assertNull(UserSession.getAccount());
    }

    @Test
    public void isAuthenticated_accountNotNull_returnTrue() {
        UserSession.destroy();
        assertFalse(UserSession.isAuthenticated());
        assertNull(UserSession.getAccount());

        UserSession.create(new Account(new Username("baba"), new Password("112233"), new Name("baba")));
        assertTrue(UserSession.isAuthenticated());
        assertNotNull(UserSession.getAccount());
    }

    @Test
    public void update_updateIsDoneCorrectly_returnEqual() {
        Account newAccount = new AccountCreator(new Account(new Username("baba"), new Password("112233"),
                new Name("baba"))).build();
        UserSession.update(newAccount);
        assertEquals(UserSession.getAccount().getUsername().toString(), "baba");
        assertTrue(UserSession.isAuthenticated());
        assertNotNull(UserSession.getAccount());
    }
}
