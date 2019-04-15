package seedu.giatros.ui.account;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.giatros.model.account.Account;
import seedu.giatros.ui.GuiUnitTest;
import seedu.giatros.ui.testutil.AccountCreator;

public class AccountCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Account account = new AccountCreator("staff").build();
        AccountCard accountCard = new AccountCard(account, 2);
        uiPartRule.setUiPart(accountCard);
    }

    @Test
    public void equals() {
        Account account = new AccountCreator("staff").build();
        AccountCard accountCard = new AccountCard(account, 0);

        // same account, same index -> returns true
        AccountCard copy = new AccountCard(account, 0);
        assertTrue(accountCard.equals(copy));

        // same object -> returns true
        assertTrue(accountCard.equals(accountCard));

        // null -> returns false
        assertFalse(accountCard.equals(null));

        // different types -> returns false
        assertFalse(accountCard.equals(0));

        // different account, same index -> returns false
        Account differentPatient = new AccountCreator("staff").withName("differentName").build();
        assertFalse(accountCard.equals(new AccountCard(differentPatient, 0)));

        // same account, different index -> returns false
        assertFalse(accountCard.equals(new AccountCard(account, 1)));
    }

}
