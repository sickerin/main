package seedu.giatros.commons.events.ui.accounts;

import seedu.giatros.commons.core.session.UserSession;
import seedu.giatros.commons.events.BaseEvent;

/**
 * Indicates a user has just logged out of system.
 */
public class LogoutEvent extends BaseEvent {

    public LogoutEvent() {
        UserSession.destroy();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
