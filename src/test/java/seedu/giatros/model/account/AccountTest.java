package seedu.giatros.model.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static seedu.giatros.testutil.TypicalAccounts.BABA;
import static seedu.giatros.testutil.TypicalAccounts.MANAGER;

import org.junit.Test;

import seedu.giatros.ui.testutil.AccountCreator;

public class AccountTest {

    @Test
    public void contructor() {
        Account newAccount = new Account(BABA.getUsername());
        assertEquals("baba", newAccount.getUsername().toString());
        assertNotNull(new Account(BABA.getUsername()));
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertEquals(MANAGER, MANAGER);

        // same values -> returns true
        Account adminAccount = new AccountCreator("manager").build();
        assertEquals(MANAGER, adminAccount);

        // null -> returns false
        assertNotEquals(null, MANAGER);

        // different type -> returns false
        assertNotEquals(5, MANAGER);

        // different account -> returns false
        assertNotEquals(MANAGER, BABA);
    }

    @Test
    public void hashcode() {
        final Account adminAccount = MANAGER;
        final Account demoAccount = BABA;

        // same values -> returns same hashcode
        assertEquals(adminAccount.hashCode(), adminAccount.hashCode());

        // different values -> returns different hashcode
        assertNotEquals(adminAccount.hashCode(), demoAccount.hashCode());
    }

    @Test
    public void toStringCheck() {
        // same username
        assertEquals(MANAGER.toString(), MANAGER.getUsername().toString());

        // different username
        assertNotEquals(MANAGER.toString(), BABA.getUsername().toString());
    }
}
