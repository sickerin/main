package seedu.giatros.commons.account;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.Test;

import seedu.giatros.commons.core.session.UserSession;
import seedu.giatros.commons.events.storage.UpdateAccountEvent;
import seedu.giatros.model.account.Account;
import seedu.giatros.model.account.Name;
import seedu.giatros.model.account.Password;
import seedu.giatros.model.account.Username;
import seedu.giatros.testutil.Assert;
import seedu.giatros.ui.testutil.AccountCreator;

/**
 * Test if update of account works
 */
public class UpdateAccountTest {

    private UpdateAccountEvent updateAccount;

    @Test
    public void constructor_updateIsDoneCorrectly_returnEqualAndNotNull() {
        Assert.assertThrows(NullPointerException.class, () -> new UpdateAccountEvent(null));
        assertNull(UserSession.getAccount());
        UserSession.create(new Account(new Username("baba"), new Password("112233"), new Name("baba")));
        assertNotNull(UserSession.getAccount());
        assertEquals(UserSession.getAccount().getUsername().toString(), "baba");
        updateAccount = new UpdateAccountEvent(new AccountCreator("manager").build());
        assertNotNull(updateAccount.username);
        assertEquals(UserSession.getAccount().getUsername().toString(), "MANAGER");
    }

    @Test
    public void simpleName_returnEqualAndNotNull() {
        updateAccount = new UpdateAccountEvent(new AccountCreator("manager").build());
        assertNotNull(updateAccount.toString());
        assertEquals(updateAccount.toString(), "UpdateAccountEvent");
    }
}
