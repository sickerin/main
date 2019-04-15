package seedu.giatros.commons.account;

import seedu.giatros.commons.core.session.UserSession;
import seedu.giatros.commons.events.ui.accounts.LogoutEvent;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LogoutEventTest {

    private LogoutEvent logout = new LogoutEvent();

    @Test
    public void constructor_destroyUserSession_returnNull() {
        assertNull(UserSession.getAccount());
    }

    @Test
    public void simpleName_returnEqualAndNotNull() {
        assertNotNull(logout.toString());
        assertEquals(logout.toString(), "LogoutEvent");
    }
}
