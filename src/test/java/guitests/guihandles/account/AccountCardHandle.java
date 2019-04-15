package guitests.guihandles.account;

import guitests.guihandles.NodeHandle;
import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.giatros.model.account.Account;

/**
 * Provides a handle to a card card in the account list panel.
 */
public class AccountCardHandle extends NodeHandle<Node> {
    private static final String USERNAME_FIELD_ID = "#username";
    private static final String NAME_FIELD_ID = "#name";

    private final Label usernameLabel;
    private final Label nameLabel;

    public AccountCardHandle(Node cardNode) {
        super(cardNode);

        usernameLabel = getChildNode(USERNAME_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
    }

    public String getUsername() {
        return usernameLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code account}.
     */
    public boolean equals(Account account) {
        return getUsername().equals(account.getUsername().toString());
    }
}
