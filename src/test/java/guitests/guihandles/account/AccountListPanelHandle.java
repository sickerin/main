package guitests.guihandles.account;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import guitests.guihandles.NodeHandle;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.giatros.model.account.Account;

/**
 * Provides a handle for {@code accountListPanel} containing the list of {@code accountCard}.
 */
public class AccountListPanelHandle extends NodeHandle<ListView<Account>> {
    public static final String ACCOUNT_LIST_VIEW_ID = "#accountListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Account> lastRememberedSelectedaccountCard;

    public AccountListPanelHandle(ListView<Account> accountListPanelNode) {
        super(accountListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code accountCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public AccountCardHandle getHandleToSelectedCard() {
        List<Account> selectedAccountList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedAccountList.size() != 1) {
            throw new AssertionError("Account list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(AccountCardHandle::new)
                .filter(handle -> handle.equals(selectedAccountList.get(0)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<Account> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code account}.
     */
    public void navigateToCard(Account account) {
        if (!getRootNode().getItems().contains(account)) {
            throw new IllegalArgumentException("Account does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(account);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Navigates the listview to {@code index}.
     */
    public void navigateToCard(int index) {
        if (index < 0 || index >= getRootNode().getItems().size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(index);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Selects the {@code accountCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the account card handle of a account associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public AccountCardHandle getAccountCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(AccountCardHandle::new)
                .filter(handle -> handle.equals(getaccount(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Account getaccount(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    /**
     * Remembers the selected {@code accountCard} in the list.
     */
    public void rememberSelectedAccountCard() {
        List<Account> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedaccountCard = Optional.empty();
        } else {
            lastRememberedSelectedaccountCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code accountCard} is different from the value remembered by the most recent
     * {@code rememberSelectedAccountCard()} call.
     */
    public boolean isSelectedAccountCardChanged() {
        List<Account> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedaccountCard.isPresent();
        } else {
            return !lastRememberedSelectedaccountCard.isPresent()
                    || !lastRememberedSelectedaccountCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
