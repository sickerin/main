package seedu.giatros.commons.events.ui.accounts;

import seedu.giatros.commons.core.session.UserSession;
import seedu.giatros.commons.events.BaseEvent;
import seedu.giatros.model.account.Account;
import seedu.giatros.model.account.Username;

/**
 * Indicates a user has just logged into the system.
 */
public class LoginEvent extends BaseEvent {

    public final Username username;

    public LoginEvent(Account account) {
        UserSession.create(account);
        this.username = account.getUsername();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
