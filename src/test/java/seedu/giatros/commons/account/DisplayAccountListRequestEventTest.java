package seedu.giatros.commons.account;

import seedu.giatros.commons.events.ui.accounts.DisplayAccountListRequestEvent;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DisplayAccountListRequestEventTest {

    @Test
    public void simpleName_returnEqualAndNotNull() {
        DisplayAccountListRequestEvent displayAccount = new DisplayAccountListRequestEvent();
        assertNotNull(displayAccount.toString());
        assertEquals(displayAccount.toString(), "DisplayAccountListRequestEvent");
    }
}
