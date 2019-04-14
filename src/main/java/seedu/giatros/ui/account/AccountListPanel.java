package seedu.giatros.ui.account;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.giatros.commons.core.LogsCenter;
import seedu.giatros.model.account.Account;
import seedu.giatros.ui.UiPart;

/**
 * Panel containing the list of {@code Account}s.
 */
public class AccountListPanel extends UiPart<Region> {

    private static final String FXML = "AccountListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AccountListPanel.class);

    @FXML
    private ListView<Account> accountListView;

    public AccountListPanel(ObservableList<Account> accountList) {
        super(FXML);
        accountListView.setItems(accountList);

        accountListView.setCellFactory(listView -> new AccountListViewCell());
        Account account = accountListView.getSelectionModel().getSelectedItem();
        logger.fine("Selection in account list panel changed to : '" + account + "'");
    }

    /**
     * Scrolls to the {@code AccountCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            accountListView.scrollTo(index);
            accountListView.getSelectionModel().clearAndSelect(index);
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Account} using a {@code AccountCard}.
     */
    private class AccountListViewCell extends ListCell<Account> {

        @Override
        protected void updateItem(Account account, boolean empty) {
            super.updateItem(account, empty);

            if (empty || account == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AccountCard(account, getIndex() + 1).getRoot());
            }
        }
    }
}
