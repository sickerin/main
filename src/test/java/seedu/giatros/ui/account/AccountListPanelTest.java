package seedu.giatros.ui.account;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.giatros.testutil.TypicalAccounts.getTypicalAccounts;

import org.junit.Test;

import guitests.guihandles.account.AccountCardHandle;
import guitests.guihandles.account.AccountListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.giatros.model.account.Account;
import seedu.giatros.model.account.Name;
import seedu.giatros.model.account.Password;
import seedu.giatros.model.account.Username;
import seedu.giatros.ui.GuiUnitTest;

/**
 * Test accountlistpanel class
 */
public class AccountListPanelTest extends GuiUnitTest {
    private static final ObservableList<Account> TYPICAL_ACCOUNTS =
            FXCollections.observableList(getTypicalAccounts());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<Account> selectedAccount = new SimpleObjectProperty<>();
    private AccountListPanelHandle accountListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_ACCOUNTS);

        for (int i = 0; i < TYPICAL_ACCOUNTS.size(); i++) {
            accountListPanelHandle.navigateToCard(TYPICAL_ACCOUNTS.get(i));
            Account expectedAccount = TYPICAL_ACCOUNTS.get(i);
            AccountCardHandle actualCard = accountListPanelHandle.getAccountCardHandle(i);

            assertEquals(expectedAccount.getUsername().toString(), actualCard.getUsername());
        }
    }

    /**
     * Verifies that creating and deleting large number of accounts in {@code AccountListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Account> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of account cards exceeded time limit");
    }

    /**
     * Returns a list of accounts containing {@code accountCount} accounts that is used to populate the
     * {@code AccountListPanel}.
     */
    private ObservableList<Account> createBackingList(int accountCount) {
        ObservableList<Account> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < accountCount; i++) {
            Username username = new Username(i + "a");
            Password password = new Password("000000");
            Name name = new Name("aaa");
            Account account = new Account(username, password, name);
            backingList.add(account);
        }
        return backingList;
    }

    /**
     * Initializes {@code accountListPanelHandle} with a {@code AccountListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code AccountListPanel}.
     */
    private void initUi(ObservableList<Account> backingList) {
        AccountListPanel accountListPanel =
                new AccountListPanel(backingList);
        uiPartRule.setUiPart(accountListPanel);

        accountListPanelHandle = new AccountListPanelHandle(getChildNode(accountListPanel.getRoot(),
                accountListPanelHandle.ACCOUNT_LIST_VIEW_ID));
    }
}
