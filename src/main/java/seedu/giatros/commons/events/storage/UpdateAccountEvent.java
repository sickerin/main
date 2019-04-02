package seedu.giatros.commons.events.storage;

import seedu.giatros.commons.core.session.UserSession;
import seedu.giatros.commons.events.BaseEvent;
import seedu.giatros.model.account.Account;
import seedu.giatros.model.account.Username;

/**
 * Indicates a user account has just been updated.
 */
public class UpdateAccountEvent extends BaseEvent {

    public final Username username;

    public UpdateAccountEvent(Account account) {
        UserSession.update(account);
        this.username = account.getUsername();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
