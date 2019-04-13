package seedu.giatros.ui.account;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.giatros.model.account.Account;
import seedu.giatros.ui.UiPart;

/**
 * An UI component that displays information of a {@code Account}.
 */
public class AccountCard extends UiPart<Region> {

    private static final String FXML = "AccountListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX. As a consequence, UI
     * elements' variable names cannot be set to such keywords or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Account account;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label username;
    @FXML
    private Label name;

    public AccountCard(Account account, int displayedIndex) {
        super(FXML);
        this.account = account;
        id.setText(displayedIndex + ". ");
        username.setText(account.getUsername().toString());
        name.setText(account.getName().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AccountCard)) {
            return false;
        }

        // state check
        AccountCard card = (AccountCard) other;
        return id.getText().equals(card.id.getText()) && account.equals(card.account);
    }
}
