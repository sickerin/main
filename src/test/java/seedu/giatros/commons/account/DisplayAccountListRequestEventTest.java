package seedu.giatros.commons.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import seedu.giatros.commons.events.ui.accounts.DisplayAccountListRequestEvent;

public class DisplayAccountListRequestEventTest {

    @Test
    public void simpleName_returnEqualAndNotNull() {
        DisplayAccountListRequestEvent displayAccount = new DisplayAccountListRequestEvent();
        assertNotNull(displayAccount.toString());
        assertEquals(displayAccount.toString(), "DisplayAccountListRequestEvent");
    }
}
