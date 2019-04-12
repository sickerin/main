package seedu.giatros.commons.events.ui.accounts;

import seedu.giatros.commons.events.BaseEvent;

/**
 * An event requesting to display AccountListPanel.
 */
public class DisplayAccountListRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
